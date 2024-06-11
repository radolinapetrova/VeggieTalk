from flask import Flask, request, jsonify
from flask_cors import CORS


class UserApp:
    def __init__(self, service):
        self.service = service

    def create_app(self):
        users_app = Flask(__name__)
        CORS(users_app)

        @users_app.route('/login', methods=['POST'])
        def login():
            data = request.json
            username = data.get('username')
            password = data.get('password')
            result = self.service.login_user(username, password)
            return jsonify(result)

        @users_app.route('/create_user', methods=['POST'])
        def create_user():
            data = request.json
            username = data.get('username')
            password = data.get('password')
            email = data.get('email')

            user_cred = {
                "username": username,
                "password": password,
                "email": email
            }

            result = self.service.create_user(user_cred)
            return jsonify(result)  # Ensure the result is JSON serializable

        @users_app.route('/delete_user', methods=['POST'])
        def delete_user():
            data = request.json
            username = data.get('username')
            auth_token = data.get('auth_token')
            result = self.service.delete_user(username, auth_token)
            return jsonify(result)

        @users_app.route('/update_user', methods=['PUT'])
        def update_user():
            data = request.json
            username = data['username']
            password = data['password']
            email = data['email']
            auth_token = data['auth_token']
            user_id = data['id']

            update_data = {
                "username": username,
                "password": password,
                "email": email
            }
            result = self.service.update_user(user_id, update_data, auth_token)
            return jsonify(result)

        return users_app


if __name__ == "__main__":
    from user_service import UserService

    user_app = UserApp(UserService())
    app = user_app.create_app()
    app.run()
