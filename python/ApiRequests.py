from requests import get, post, put, delete

class ApiRequests:
    def __init__(self, path):
        self.token = False
        self.path = path

    
    def setToken(self, token):
        self.token = token

    def generatePath(self, path):
        path = f'{self.path}{path}'    
        return path

    def post(self, path, body):
        if self.token:
            resp = post(
                self.generatePath(path=path), 
                json=body,
                headers = {
                    'Content-Type': 'application/json',
                    'Authorization': f"Bearer {self.token}"
                    }
                )
        else:
            resp = post(
                self.generatePath(path=path), 
                json=body,
                headers = {'Content-Type': 'application/json'}
                )

        return resp

    def get(self, path):
        if self.token:
            resp = get(
                self.generatePath(path=path),
                headers = {'Authorization': f"Bearer {self.token}"}
                )
        else:
            resp = get(
                self.generatePath(path=path)
                )
        return resp

    def put(self, path, body):
        if self.token:
            resp = put(
                self.generatePath(path=path), 
                json=body,
                headers = {
                    'Content-Type': 'application/json',
                    'Authorization': f"Bearer {self.token}"
                    }
                )
        else:
            resp = put(
                self.generatePath(path=path), 
                json=body,
                headers = {'Content-Type': 'application/json'}
                )

        return resp

    def delete(self, path, body):
        if self.token:
            resp = delete(
                self.generatePath(path=path), 
                json=body,
                headers = {
                    'Content-Type': 'application/json',
                    'Authorization': f"Bearer {self.token}"
                    }
                )
        else:
            resp = put(
                self.generatePath(path=path), 
                json=body,
                headers = {'Content-Type': 'application/json'}
                )

        return resp