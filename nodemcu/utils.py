CRLF = "\r\n"
DEFAULT_HTTP_VERSION = "HTTP/1.1"


class NodePins:
    D1 = 5
    D2 = 4
    D3 = 0
    D4 = 2
    D5 = 14
    D6 = 12
    D7 = 13
    D8 = 15


class RequestParser(object):
    def __parse_request_line(self, request_line):
        request_parts = request_line.split(" ")
        self.method = request_parts[0]
        self.url = request_parts[1]
        self.protocol = request_parts[2] if len(request_parts) > 2 else DEFAULT_HTTP_VERSION

    def __init__(self, req_text):
        req_lines = req_text.split(CRLF)
        self.__parse_request_line(req_lines[0])
        ind = 1
        self.headers = dict()
        while ind < len(req_lines) and len(req_lines[ind]) > 0:
            colon_ind = req_lines[ind].find(":")
            header_key = req_lines[ind][:colon_ind]
            header_value = req_lines[ind][colon_ind + 1 :]
            self.headers[header_key] = header_value
            ind += 1
        ind += 1
        self.data = req_lines[ind:] if ind < len(req_lines) else None
        self.body = CRLF.join(self.data)

    def __str__(self):
        headers = CRLF.join(f"{key}: {self.headers[key]}" for key in self.headers)
        return f"{self.method} {self.url} {self.protocol}{CRLF}{headers}{CRLF}{CRLF}{self.body}"
