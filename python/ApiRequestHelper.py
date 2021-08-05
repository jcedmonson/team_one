from ApiRequests import ApiRequests

class ApiRequestHelper:
    def __init__(self):
        self.authPath = "http://localhost:8081"
        self.dataPath = "http://localhost:8080"
        

        self.tokenBody = {
            "name": "bruce",
            "password": "pass"
        }

        self.authRequests = ApiRequests(self.authPath)
        self.dataRequests = ApiRequests(self.dataPath)

        self.token = self.configToken()

        self.dataRequests.setToken(self.token)


    def configToken(self):
        print(" *** Configuring JWT *** ")
        resp = self.authRequests.post(
                            path="/account/token",
                            body=self.tokenBody)
        return resp.json()['token']
        
