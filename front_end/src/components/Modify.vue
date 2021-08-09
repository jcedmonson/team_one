<template>
    <v-dialog max-width="500px" outlined v-model="showDialog">
     <template v-slot:activator="{ on }">
       <!-- <v-btn  v-on="on" class="success">Add New Project</v-btn> -->
        <v-icon
          small
          class="mr-2"
          color=#00BCD4 
          v-on="on"
        >
          mdi-pencil
        </v-icon>
     </template>
    <v-card>
      <v-container fill-height fluid>
      <v-row justify="center">
        <!-- 

          Customers

         -->
        <v-container class="window" v-if="customersTab">
          <v-container>
            <h2 class="cyan--text font-weight-light">Modify Customer.</h2>
          </v-container>
          <v-text-field
              v-model="username"
              label="Username"
              clearable
              dark
              color=#00BCD4
          ></v-text-field>
          <v-text-field
              v-model="email"
              label="Email"
              clearable
              dark
              color=#00BCD4
          ></v-text-field>
          <v-text-field
              v-model="password"
              label="Password"
              clearable
              dark
              color=#00BCD4
          ></v-text-field>
          <v-row>
          <v-col>
            <v-container>
              <v-btn outlined color=#00BCD4 @click="clearForm">
                <v-icon>delete</v-icon>
              </v-btn>
            </v-container>
          </v-col>
          <v-col>
            <v-container class="text-right">
              <v-btn outlined color=#00BCD4 @click="putCustomer">
                <v-icon>arrow_forward_ios</v-icon>
              </v-btn>
            </v-container>
          </v-col>
          </v-row>
        </v-container>
        <!-- 

          Events

         -->
        <v-container class="window" v-if="eventsTab">
          <v-container>
            <h2 class="cyan--text font-weight-light">Modify Event.</h2>
          </v-container>
          <v-text-field
              v-model="title"
              label="Title"
              clearable
              dark
              color=#00BCD4
          ></v-text-field>
          <v-text-field
              v-model="description"
              label="Description"
              clearable
              dark
              color=#00BCD4
          ></v-text-field>
          <v-text-field
              v-model="code"
              label="Code"
              clearable
              dark
              color=#00BCD4
          ></v-text-field>
          <v-row>
          <v-col>
            <v-container>
              <v-btn outlined color=#00BCD4 @click="clearForm">
                <v-icon>delete</v-icon>
              </v-btn>
            </v-container>
          </v-col>
          <v-col>
            <v-container class="text-right">
              <v-btn outlined color=#00BCD4 @click="putEvent">
                <v-icon>arrow_forward_ios</v-icon>
              </v-btn>
            </v-container>
          </v-col>
          </v-row>
        </v-container> 
        <!-- 

          Registrations

         -->
        <v-container class="window" v-if="registrationsTab">
          <v-container>
            <h2 class="cyan--text font-weight-light">Modify Registrations.</h2>
          </v-container>
          <!-- <h1>{{this.selection}}</h1> -->
          <v-select
            v-model="customer"
            :items="customers"
            label="Customer"
            color=#00BCD4
            item-color="cyan"
          ></v-select>
          <v-select
            v-model="event"
            :items="events"
            label="Event"
            color=#00BCD4
            item-color="cyan"
          ></v-select>
          <v-text-field
            v-model="notes"
            label="Notes"
            clearable
            dark
            color=#00BCD4
          ></v-text-field>
          <v-menu
            ref="menu"
            v-model="menu"
            :close-on-content-click="false"
            :return-value.sync="date"
            transition="scale-transition"
            offset-y
            min-width="auto"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="date"
                label="Date"
                prepend-icon="mdi-calendar"
                readonly
                v-bind="attrs"
                v-on="on"
                color=#00BCD4
              ></v-text-field>
            </template>
            <v-date-picker
              v-model="date"
              no-title
              scrollable
            >
              <v-spacer></v-spacer>
              <v-btn
                text
                color=#00BCD4 
                @click="menu = false"
              >
                Cancel
              </v-btn>
              <v-btn
                text
                color=#00BCD4
                @click="$refs.menu.save(date)"
              >
                OK
              </v-btn>
            </v-date-picker>
          </v-menu>
          <v-row>
          <v-col>
            <v-container>
              <v-btn outlined color=#00BCD4 @click="clearForm">
                <v-icon>delete</v-icon>
              </v-btn>
            </v-container>
          </v-col>
          <v-col>
            <v-container class="text-right">
              <v-btn outlined color=#00BCD4 @click="putRegistration">
                <v-icon>arrow_forward_ios</v-icon>
              </v-btn>
            </v-container>
          </v-col>
          </v-row>
        </v-container> 
      </v-row>
    </v-container>
    </v-card>
   </v-dialog>
</template>

<script>
  export default {
    name: 'modify',

    data: () => {
      return {
          menu : null,
          clearform : null,
          customerPath: "api/customers",
          eventPath: "api/events",
          registrationPath: "api/registrations",
          alert: false,
          customersTab: false,
          eventsTab: false,
          registrationsTab: false,
          username: null,
          email: null,
          password: null,
          title: null,
          description: null,
          code: null,
          customer: null,
          customers: [],
          event: null,
          events: [],
          notes: null,
          date: null,
          showDialog: false,
      } 
    },

    created () {
       this.determineRender()
    },

    components: {},

    props: {
      selection: Object,
      selectedTab: Object,
      customerApi: Object,
      eventApi: Object,
      registrationApi: Object
    },

    methods :{

    close(){
      this.$emit('reload', this.selectedTab.selectedTab)
      this.showDialog = false;
    },
      
    async determineRender(){
      switch(this.selectedTab.selectedTab){
        case 'customers':
          this.customersTab = true;
          this.prepareCustomer()
          break;
        case 'events':
          this.eventsTab = true;
          this.prepareEvent()
          break;
        case 'registrations':
          this.registrationsTab = true;
          this.prepareRegistration()
          break;
      }
    },

    prepareCustomer(){
      let customerData = this.selection.item;
      this.username = customerData.name;
      this.email = customerData.email;
      this.password = customerData.password;
    },

    prepareEvent(){
      let customerData = this.selection.item;
      this.title = customerData.title;
      this.description = customerData.description;
      this.code = customerData.code;
    },

    prepareRegistration(){
      let customerData = this.selection.item;
      this.customer = customerData.customer;
      this.customers = this.extractValues(this.customerApi.customerData, "name")
      this.event = customerData.event;
      this.events = this.extractValues(this.eventApi.eventData, "title")
      this.notes = customerData.notes;
      this.date = customerData.registration_date;
    },

    async putCustomer(){
        //{'id': 1, 'name': 'Bruce', 'email': 'bruce@gmail.com', 'password': 'pass'}
      let newData = {};
      let endpoint = "/api/customers/"+this.selection.item.id
      newData['id'] = this.selection.item.id
      newData['name'] = this.username
      newData['email'] = this.email
      newData['password'] = this.password
      let resp = await this.putData(endpoint, JSON.stringify(newData))
      if (resp == 204){
        console.log("GG EZ Customer modified!")
        this.close()
      } else {
        console.log("Customer Stuff Is Wack.")
      }
    },

    async putEvent(){
      // { "id": 1, "title": "All-Java Conference", "description": "Lectures and exhibits covering all Java topics", "code": "CNF001" }
      let endpoint = "/api/events/"+this.selection.item.id
      let newData = {};
      newData['id'] = this.selection.item.id
      newData['title'] = this.title
      newData['description'] = this.description
      newData['code'] = this.code
      let resp = await this.putData(endpoint, JSON.stringify(newData))
      if (resp == 204){
        this.close()
      } else {
        console.log("Event Stuff Is Wack.")
      }
    },

    async putRegistration(){
      let newData = {};
      // {'id': 1, 'event_id': 2, 'customer_id': 2, 'registration_date': '2019-01-15', 'notes': 'SSSSSHHHHHHEEEESSSSHH'}
      let endpoint = "/api/registrations/"+this.selection.item.id
      let event = this.event
      let customer = this.customer
      newData['id'] = this.selection.item.id
      let eventid = this.findID(this.eventApi.eventData, event, "title");
      let customerid = this.findID(this.customerApi.customerData, customer, "name")
      console.log(`Event ID ${eventid}`)
      console.log(`Customer ID ${customerid}`)
        
      newData['event_id'] = eventid;
      newData['customer_id'] = customerid;
      newData['registration_date'] = this.date;
      newData['notes'] = this.notes;
      let resp = await this.putData(endpoint, JSON.stringify(newData))
      if (resp == 204){
          this.close()
        } else {
          console.log("Registration Stuff Is Wack.")
        }
    },
         

    async putData(endpoint, newData){
      console.log(newData)
      let token = this.$store.getters.getToken;
      let authHeaders = new Headers(
          {'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`})
      let resp = await fetch(
        endpoint, {
          mode: "cors", 
          method: "PUT", 
          headers: authHeaders,
          body: newData});
      return resp.status
    },

      
    // helper function to extract customer names
    extractValues(api, key){
      let results = []
      let parsed = JSON.parse(JSON.stringify(api))
      parsed.forEach(e => {
        results.push(e[key])
      });
      return results
    },

    // getting id for a specific object...
    findID(api, value, key){
      let result = "";
      let parsed = JSON.parse(JSON.stringify(api))
      parsed.forEach(v => {
        if (JSON.stringify(v[key]) == JSON.stringify(value)){
          result = v['id'];
        }
      })
      return result;
    }
  }
}
</script>

<style scoped>
  .window {
    padding: 45px;
  }
</style>