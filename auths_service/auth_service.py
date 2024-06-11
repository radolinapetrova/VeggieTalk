import requests

keycloak_root = "http://localhost:8282"
keycloak_admin = "admin"
keycloak_admin_password = "admin"

# Get an admin access token
resp = requests.post(
    f"{keycloak_root}/realms/master/protocol/openid-connect/token",
    data={
        "client_id": "admin-cli",
        "username": keycloak_admin,
        "password": keycloak_admin_password,
        "grant_type": "password"
    }
)
resp.raise_for_status()
data = resp.json()
access_token = data["access_token"]
print(f"Access Token: {access_token[:20]}...{access_token[-20:]}")
print(f"Expires in {data['expires_in']} seconds")

# Predefine authorization headers for later use.
auth_headers = {
    "Authorization": f"Bearer {access_token}",
}

# Check existing realms
resp = requests.get(
    f"{keycloak_root}/admin/realms",
    headers=auth_headers,
)
resp.raise_for_status()
realms = [r["realm"] for r in resp.json()]
print("Existing Realms:", realms)

realm = "veggie-talk-realm"

# Create a new realm if it doesn't exist
if realm not in realms:
    resp = requests.post(
        f"{keycloak_root}/admin/realms",
        headers=auth_headers,
        json={
            "realm": realm,
            "enabled": True
        }
    )
    resp.raise_for_status()
    print(f"Realm '{realm}' created")
else:
    print(f"Realm '{realm}' already exists")

# Check realms again to confirm creation
resp = requests.get(
    f"{keycloak_root}/admin/realms",
    headers=auth_headers,
)
resp.raise_for_status()
realms = [r["realm"] for r in resp.json()]
print("Realms after creation:", realms)

# Client settings
client_settings = {
    "protocol": "openid-connect",
    "clientId": "veggie-talk-client",
    "enabled": True,
    "publicClient": True,
    "standardFlowEnabled": True,
    "serviceAccountsEnabled": False,
    "directAccessGrantsEnabled": True,
    "attributes": {
        "oauth2.device.authorization.grant.enabled": True,
    }
}

# Check if the client already exists
resp = requests.get(
    f"{keycloak_root}/admin/realms/{realm}/clients",
    headers=auth_headers,
    params={"clientId": client_settings["clientId"]}
)
resp.raise_for_status()
clients = resp.json()
created_client = next((client for client in clients if client['clientId'] == client_settings["clientId"]), None)

if not created_client:
    resp = requests.post(
        f"{keycloak_root}/admin/realms/{realm}/clients",
        json=client_settings,
        headers=auth_headers,
    )
    resp.raise_for_status()
    location = resp.headers["Location"]
    print(f"Client created at {location}")
else:
    print(f"Client '{client_settings['clientId']}' already exists")

# Confirm the created client
resp = requests.get(
    f"{keycloak_root}/admin/realms/{realm}/clients",
    headers=auth_headers,
)
resp.raise_for_status()
clients = resp.json()
created_client = next((client for client in clients if client['clientId'] == client_settings["clientId"]), None)
if created_client:
    print(f"Client ID: {created_client['id']}")
    print(f"Client Details: {created_client}")
else:
    print("Client 'veggie-talk-client' not found")

username = "piratka"
password = "123456"

# # Check if the user already exists
# resp = requests.get(
#     f"{keycloak_root}/admin/realms/{realm}/users",
#     headers=auth_headers,
#     params={"username": username}
# )
# resp.raise_for_status()
# users = resp.json()
#
# if not any(user['username'] == username for user in users):
#     user_settings = {
#         "email": "radka@gmail.com",
#         "username": username,
#         "enabled": True,
#         "credentials": [{
#             "type": "password",
#             "value": password,
#             "temporary": False,
#         }]
#     }
#
#     resp = requests.post(
#         f"{keycloak_root}/admin/realms/{realm}/users",
#         json=user_settings,
#         headers=auth_headers,
#     )
#     resp.raise_for_status()
#     location = resp.headers["Location"]
#     print(f"User created at {location}")
# else:
#     print(f"User '{username}' already exists")
