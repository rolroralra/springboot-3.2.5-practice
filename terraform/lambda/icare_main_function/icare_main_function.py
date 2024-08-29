import json

def lambda_handler(event, context):
  print(f"event: {event}")
  print(f"context: {context}")

  # TODO implement
  name = event['name']

  return f"Hello, {name}!"
