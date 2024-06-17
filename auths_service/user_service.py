import requests
import time
import pika
import json
import jwt


class UserService:

    def __init__(self):
        self.keycloak_root = "http://localhost:8282"
        self.realm = 'veggie-talk-realm'
        self.admin_user = 'admin'
        self.admin_password = 'admin'
        self.access_token = None
        self.token_expiry = 0
        self.client_id = 'veggie-talk-client'
        self.exchange_name = "account_exchange"
        self.queue_name = "account_queue"
        self.routing_key = "account"
        self.rabbitmq_host = "localhost"
        self.username = "guest"
        self.password = "guest"

        credentials = pika.PlainCredentials(self.username, self.password)
        self.connection = pika.BlockingConnection(
            pika.ConnectionParameters(host=self.rabbitmq_host, port=5672, credentials=credentials))
        self.channel = self.connection.channel()

        # Declare exchange and queue, then bind them
        self.channel.exchange_declare(exchange=self.exchange_name, exchange_type="direct", durable=True)
        self.channel.queue_declare(queue=self.queue_name, durable=True)
        self.channel.queue_bind(exchange=self.exchange_name, queue=self.queue_name, routing_key=self.routing_key)

    def get_access_token(self):
        if self.access_token and time.time() < self.token_expiry:
            return self.access_token

        resp = requests.post(
            f"{self.keycloak_root}/realms/master/protocol/openid-connect/token",
            data={
                "client_id": "admin-cli",
                "username": self.admin_user,
                "password": self.admin_password,
                "grant_type": "password"
            }
        )
        resp.raise_for_status()
        res = resp.json()
        self.access_token = res["access_token"]
        self.token_expiry = time.time() + res["expires_in"] - 60
        return self.access_token

    def get_auth_headers(self):
        token = self.get_access_token()
        return {"Authorization": f"Bearer {token}"}

    def create_user(self, user_data):
        username = user_data['username']
        password = user_data['password']
        email = user_data['email']

        resp = requests.get(
            f"{self.keycloak_root}/admin/realms/{self.realm}/users",
            headers=self.get_auth_headers(),
            params={"username": username}
        )
        resp.raise_for_status()
        users = resp.json()

        if not any(user['username'] == username for user in users):
            user_settings = {
                "email": email,
                "username": username,
                "enabled": True,
                "credentials": [{
                    "type": "password",
                    "value": password,
                    "temporary": False,
                }]
            }

            resp = requests.post(
                f"{self.keycloak_root}/admin/realms/{self.realm}/users",
                json=user_settings,
                headers=self.get_auth_headers(),
            )
            resp.raise_for_status()
            location = resp.headers["Location"]
            user_id = location.split('/')[-1]  # Extract user ID from the location header
            print(f"User created at {location}")
            # Automatically assign the "user" role
            self.assign_client_role_to_user(user_id, "user")
            self.assign_client_role_to_user(user_id, "manage-user")
            return "User successfully created"
        else:
            print(f"User '{username}' already exists")

    def delete_user(self, username, auth_token):
        try:
            decoded_token = jwt.decode(auth_token, options={"verify_signature": False})
            user_id = decoded_token['sub']

            delete_resp = requests.delete(
                f"{self.keycloak_root}/admin/realms/{self.realm}/users/{user_id}",
                headers={"Authorization": f"Bearer {auth_token}"}
            )
            delete_resp.raise_for_status()
            print(f"User '{user_id}' deleted successfully.")
        except jwt.ExpiredSignatureError:
            print("The token has expired.")
        except jwt.InvalidTokenError:
            print("Invalid token.")
        except requests.exceptions.HTTPError as err:
            print(f"Error deleting user: {err}")
            print(f"Status Code: {err.response.status_code}")
            print(f"Response Text: {err.response.text}")

        message_data = {"username": f"{username}"}
        message = json.dumps(message_data)
        self.publish_message_to_rabbitmq(message=message)

    def login_user(self, username, password):
        print(f"Trying to login user '{username}'...")

        try:
            resp = requests.post(
                f"{self.keycloak_root}/realms/{self.realm}/protocol/openid-connect/token",
                data={
                    "client_id": self.client_id,
                    "username": username,
                    "password": password,
                    "grant_type": "password"
                }
            )
            resp.raise_for_status()
            res = resp.json()
            print(f"User '{username}' successfully logged in")
            return res
        except requests.exceptions.HTTPError as err:
            print(f"Error logging in user '{username}': {err}")
            print(f"Status Code: {err.response.status_code}")
            print(f"Response Text: {err.response.text}")
            return None



    def update_user(self, user_id, update_data, auth_token):
        resp = requests.put(
            f"{self.keycloak_root}/admin/realms/{self.realm}/users/{user_id}",
            headers={"Authorization": f"Bearer {auth_token}"},
            json=update_data
        )
        resp.raise_for_status()
        print(f"User '{user_id}' updated")
        return "User successfully updated"

    def assign_client_role_to_user(self, user_id, role_name):
        resp = requests.get(
            f"{self.keycloak_root}/admin/realms/{self.realm}/clients",
            headers=self.get_auth_headers(),
            params={"clientId": self.client_id}
        )
        resp.raise_for_status()
        clients = resp.json()
        client = next((c for c in clients if c['clientId'] == self.client_id), None)
        if not client:
            print(f"Client '{self.client_id}' not found")
            return

        client_id = client['id']

        resp = requests.get(
            f"{self.keycloak_root}/admin/realms/{self.realm}/clients/{client_id}/roles",
            headers=self.get_auth_headers(),
        )
        resp.raise_for_status()
        roles = resp.json()
        role = next((r for r in roles if r['name'] == role_name), None)
        if not role:
            print(f"Role '{role_name}' not found")
            return

        role_id = role['id']

        # Assign role to user
        resp = requests.post(
            f"{self.keycloak_root}/admin/realms/{self.realm}/users/{user_id}/role-mappings/clients/{client_id}",
            headers=self.get_auth_headers(),
            json=[{"id": role_id, "name": role_name}]
        )
        resp.raise_for_status()
        print(f"Client role '{role_name}' assigned to user '{user_id}'")

    # def assign_menage_users_role(self):
    #     resp = requests.get(
    #         f"{self.keycloak_root}/admin/realms/{self.realm}/clients",
    #         headers=self.get_auth_headers(),
    #         params={"clientId": self.client_id}
    #     )
    #     resp.raise_for_status()
    #     clients = resp.json()
    #     client = next((c for c in clients if c['clientId'] == self.client_id), None)
    #     if not client:
    #         print(f"Client '{self.client_id}' not found")
    #         return
    #
    #     client_id = client['id']
    #
    #     resp = requests.get(
    #         f"{self.keycloak_root}/admin/realms/{self.realm}/clients/{client_id}/roles",
    #         headers=self.get_auth_headers(),
    #     )
    #     resp.raise_for_status()
    #     roles = resp.json()
    #     role = next((r for r in roles if r['name'] == role_name), None)
    #     if not role:
    #         print(f"Role '{role_name}' not found")
    #         return
    #
    #     role_id = role['id']
    #
    #     # Assign role to user
    #     resp = requests.post(
    #         f"{self.keycloak_root}/admin/realms/{self.realm}/users/{user_id}/role-mappings/clients/{client_id}",
    #         headers=self.get_auth_headers(),
    #         json=[{"id": role_id, "name": role_name}]
    #     )
    #     resp.raise_for_status()
    #     print(f"Client role '{role_name}' assigned to user '{user_id}'")


    def publish_message_to_rabbitmq(self, message):
        self.channel.basic_publish(
            exchange=self.exchange_name,
            routing_key=self.routing_key,
            body=message.encode('utf-8')
        )
        print(f" [x] Sent '{message}' to exchange '{self.exchange_name}' with routing key '{self.routing_key}'")


    def close_connection(self):
        self.channel.close()
        self.connection.close()

    def clean_queue(self):
        connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
        channel = connection.channel()
        queue_name = 'account_queue'
        channel.queue_purge(queue=queue_name)
        print(f"Messages purged from queue '{queue_name}'")
        connection.close()
