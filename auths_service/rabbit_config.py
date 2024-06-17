# import pika
#
#
# def publish_message_to_rabbitmq(
#         message,
#         exchange_name="account_exchange",
#         queue_name="account_queue",
#         routing_key="account",
#         rabbitmq_host="localhost",
#         username="guest",
#         password="guest"
# ):
#     # Set up credentials
#     credentials = pika.PlainCredentials(username, password)
#
#     # Establish connection to RabbitMQ server
#     connection = pika.BlockingConnection(pika.ConnectionParameters(host=rabbitmq_host, port=5672, credentials=credentials))
#     channel = connection.channel()
#
#     # Declare exchange and queue, then bind them
#     channel.exchange_declare(exchange=exchange_name, durable=True, exchange_type="topic")
#     channel.queue_declare(queue=queue_name)
#     channel.queue_bind(exchange=exchange_name, queue=queue_name, routing_key=routing_key)
#
#     # Publish the message
#     channel.basic_publish(exchange=exchange_name, routing_key=routing_key, body=message)
#     print(f" [x] Sent '{message}' to exchange '{exchange_name}' with routing key '{routing_key}'")
#
#     # Close the channel and connection
#     channel.close()
#     connection.close()