import network

# noinspection PyUnresolvedReferences
ap = network.WLAN(network.AP_IF)
ap.active()
ap.config(essid="NodeMCU WiFi", password="lgyt1234")

while not ap.active():
    pass

print("Connection successful!")
print(ap.ifconfig())
