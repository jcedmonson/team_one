from ApiRequestHelper import ApiRequestHelper

class MockClient(ApiRequestHelper):
    def __init__(self):
        super().__init__()
        print("\n***************************")
        print("*** M o c k C l i e n t ***")
        print("***************************\n")


    # Data GET/objects
    def getObject(self, path):
        resp = self.dataRequests.get(path)
        if resp.status_code == 200 and resp.json() != []:
            print(f"* GET {self.dataPath}{path} {resp.status_code}:working as intended.")
        else:
            raise Exception(f"* GET {self.dataPath}{path} {resp.status_code}:FAILED.")

    # Data POST/objects
    def postObject(self, path, body):
        resp = self.dataRequests.post(path, body)
        if resp.status_code == 201:
            print(f"* POST {self.dataPath}{path} {resp.status_code}:working as intended.")
        else:
            raise Exception(f"* POST {self.dataPath}{path} {resp.status_code}:FAILED.")
           
    # Data PUT/objects{objectID}
    def putObject(self, path, body):
        resp = self.dataRequests.put(path, body)
        if resp.status_code == 204:
            print(f"* PUT {self.dataPath}{path} {resp.status_code}:working as intended.")
        else:
            raise Exception(f"* PUT {self.dataPath}{path} {resp.status_code}:FAILED.")

    # Data DELETE/objects{objectID}
    def deleteObject(self, path, body):
        resp = self.dataRequests.delete(path, body)
        if resp.status_code == 204:
            print(f"* DELETE {self.dataPath}{path} {resp.status_code}:working as intended.")
        else:
            raise Exception(f"* DELETE {self.dataPath}{path} {resp.status_code}:FAILED.")

    # Authentication POST/object
    def register(self, path, body):
        resp = self.authRequests.post(path, body)
        if resp.status_code == 200:
            print(f"* POST {self.authPath}{path} {resp.status_code}:working as intended.")
        else:
            raise Exception(f"* POST {self.dataPath}{path} {resp.status_code}:FAILED.")

    def unAuthPost(self, path, body, status_code):
        unloaded_auth_service = self.getUnloadedAuth()
        resp = unloaded_auth_service.post(path, body)
        if resp.status_code == status_code:
            print(f"* POST {self.authPath}{path} {resp.status_code}:working as intended.")
        else:
            raise Exception(f"* POST {self.dataPath}{path} {resp.status_code}:FAILED.")

    def unAuthGet(self, path, body, status_code):
        unloaded_auth_service = self.getUnloadedAuth()
        resp = unloaded_auth_service.get(path)
        if resp.status_code == status_code:
            print(f"* GET {self.authPath}{path} {resp.status_code}:working as intended.")
        else:
            raise Exception(f"* GET {self.dataPath}{path} {resp.status_code}:FAILED.")


    
if __name__ == "__main__":
    try:
        mc = MockClient()

        print("\n - - - AUTH Verification - - -\n")

        print("- - - Verifying Account Service Activity - - - ")
        mc.unAuthGet("/account/", {}, 200)

        print("\n - - - Testing User Login Good Credentials - - - ")
        body = {"name": "bruce", "password": "pass"}
        print(f"      * Passing correct login body {body}")
        mc.unAuthPost("/account/token", body, 200)

        print("\n - - - Testing User Login Bad Credentials - - - ")
        body = {"name": "bruce", "password": "supercoolpassword"}
        print(f"      * Passing incorrect login body {body}")
        mc.unAuthPost("/account/token", body, 401)
        
        
        print("\n - - - Testing Login, No Account - - - ")
        body = {"name": "SomeRandomUser", "password": "SHEEESSSHH"}
        print(f"      * Passing incorrect login body {body}")
        mc.unAuthPost("/account/token", body, 401)

        print("\n - - - CRUD Verification - - -\n")
         ### Registrations
        # All Registrations
        mc.getObject("/api/registrations")
        #Single Registration
        mc.getObject("/api/registrations/1")
        # Add Registration
        sample_registration = {'id': 0, 'event_id': 2, 'customer_id': 2, 'registration_date': '2021-04-20', 'notes': 'SHEESH'}
        mc.postObject("/api/registrations", sample_registration)
        # Update Registration
        updated_registration = {'id': 1, 'event_id': 2, 'customer_id': 2, 'registration_date': '2019-01-15', 'notes': 'SSSSSHHHHHHEEEESSSSHH'}
        mc.putObject("/api/registrations/1", updated_registration)
        # Remove Registration
        mc.deleteObject("/api/registrations/1", {})
        
        ### Customers
        # All Customers
        mc.getObject("/api/customers")
        #Single Customer
        mc.getObject("/api/customers/1")
        # Add Customer
        sample_customer = {'name': 'bryan', 'email': 'bryan@gmail.com', 'password': 'pass'}
        mc.postObject("/api/customers", sample_customer)
        # Update Customer
        updated_customer = {'id': 1, 'name': 'Bruce', 'email': 'bruce@gmail.com', 'password': 'pass'}
        mc.putObject("/api/customers/1", updated_customer)
        # Remove Customer
        mc.deleteObject("/api/customers/1", {})

        ### Events
        # All Events
        mc.getObject("/api/events")
        #Single Event
        mc.getObject("/api/events/1")
        # Add Event
        sample_event = {'id': 0, 'title': 'Cash-Money', 'description': 'Lectures and exhibits covering all Beat Making topics', 'code': '42069'}
        mc.postObject( "/api/events", sample_event)
        # Update Event
        updated_event = {'id': 1, 'title': 'All-JS Conference', 'description': 'Lectures and exhibits covering all JS topics', 'code': 'CNF001'}
        mc.putObject("/api/events/1", updated_event)
        # Remove Event
        mc.deleteObject("/api/events/3", {})

        ### User Registration
        print("\n - - - Registration Verification - - -\n")
        new_user = {'name': 'elkin', 'email': 'elkin@gmail.com', 'password': 'pass'}
        print(f"      * Passing new user for creation {new_user}")
        mc.register("/account/register", new_user)

    except Exception as e:
        print(e)