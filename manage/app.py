# from flask import Flask, request
# from flask_cors import *
# import json
# from rear import user
#
# app = Flask(__name__)
# CORS(app, support_credentials=True)
#
#
# @app.route('/', methods=['post'])
# def hello_world():
#     return 'Hello World!'
#
#
# @app.route("/login", methods=['post'])
# def login():
#     u = user.user()
#     data = json.loads(request.get_data())
#     userName = data['UserName']
#     password = data['Password']
#     result = u.login(userName, password)
#     return result
#
#
# @app.route("/register", methods=['post'])
# def register():
#     u = user.user()
#     data = json.loads(request.get_data())
#     userName = data['UserName']
#     password = data['Password']
#     real_name = data['REAL_NAME']
#     sex = data['SEX']
#     email = data['EMAIL']
#     phone = data['PHONE']
#     result = u.register(userName, password, real_name, sex, email, phone)
#     return result
# #
# #
# # @app.route("/query", methods=['post'])
# # def query():
# #     u = user.user()
# #     data = json.loads(request.get_data())
# #     id = data['id']
# #     result = u.query(id)
# #     return result
# #
# #
# # @app.route("/queryAll")
# # def queryAll():
# #     u = user.user()
# #     result = u.queryAll()
# #     return result
#
#
# if __name__ == '__main__':
#     localhost = '127.0.0.1'
#     app.run(host=localhost, port=5000, debug=True)
