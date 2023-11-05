import json

from machine import Pin, PWM

from micropyserver import MicroPyServer
from req_utils import send_response
from utils import RequestParser, NodePins

py_server = MicroPyServer(host="", port=80)
pins = tuple(filter(lambda x: not x.startswith("__"), dir(NodePins)))


def get_parts(request, path):
    url: str = RequestParser(request).url
    if url.endswith("/"):
        url = url[len(path) : -1]
    else:
        url = url[len(path) :]

    parts = url.split("/")
    if not parts[0].split():
        parts = parts[1:]

    return parts


# noinspection PyUnusedLocal
def index(request):
    send_response(py_server, "Face Detection NodeMCU Index")


def turn_on_pin(request):
    parts = get_parts(request, "/pin")
    if len(parts) != 2:
        return send_response(py_server, "Invalid request url.")

    if parts[0] not in pins:
        return send_response(py_server, "Invalid pin entered.")

    pin = getattr(NodePins, parts[0])
    try:
        state = int(parts[1])
    except ValueError:
        return send_response(py_server, "Invalid state entered.")

    node_pin = PWM(Pin(pin, Pin.OUT))
    node_pin.duty(state)

    send_response(py_server, json.dumps({parts[0]: state}), content_type="application/json")


# noinspection PyUnusedLocal
def get_pin_state(request):
    values = {pin: PWM(Pin(getattr(NodePins, pin), Pin.OUT)).duty() for pin in pins}
    send_response(py_server, json.dumps(values), content_type="application/json")


py_server.add_route("/", index)
py_server.add_route("/pin", turn_on_pin)
py_server.add_route("/state", get_pin_state)

py_server.start()
