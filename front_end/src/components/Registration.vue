<template>
    <v-dialog max-width="500px" outlined v-model="showDialog">
     <template v-slot:activator="{ on }">
       <!-- <v-btn  v-on="on" class="success">Add New Project</v-btn> -->
       <v-btn color=#00BCD4 text small v-on="on" name="Registration">
           <v-icon>add</v-icon>
           <v-text class="cyan--text Subtitle 2">Registration</v-text>
       </v-btn>
    </template>
    <v-card>
      <v-container class="window">
        <v-container>
          <h2 class="cyan--text font-weight-light">Add a registration.</h2>
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
  </v-card>
  </v-dialog>
</template>

<script>
  export default {
    name: 'registration',
    data: () => {
      return {
          alert: false,
          customer: null,
          customers: [],
          event: null,
          events: [],
          notes: null,
          showDialog: false,
          date: ""

      } 
    },
    components: {},

    props:{
      customerApi: Object,
      eventApi: Object,
    },

    created(){
      this.prepare();
      console.log(this.customerApi.customerData)
    },

    methods :{

    close(){
      this.$emit('reload', 'registrations')
      this.showDialog = false;
    },

    prepare(){
      this.customers = this.extractValues(this.customerApi.customerData, "name")
      this.events = this.extractValues(this.eventApi.eventData, "title")
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
    },

    toggleAlert(alert){
        this.alert = alert;
      },

    async putRegistration(){
      let newData = {};
      // {'id': 1, 'event_id': 2, 'customer_id': 2, 'registration_date': '2019-01-15', 'notes': 'SSSSSHHHHHHEEEESSSSHH'}
      let endpoint = "/api/registrations/"
      let event = this.event
      let customer = this.customer
      newData['id'] = 0
      let eventid = this.findID(this.eventApi.eventData, event, "title");
      let customerid = this.findID(this.customerApi.customerData, customer, "name")
      console.log(`Event ID ${eventid}`)
      console.log(`Customer ID ${customerid}`)
        
      newData['event_id'] = eventid;
      newData['customer_id'] = customerid;
      newData['registration_date'] = this.date;
      newData['notes'] = this.notes;
      let resp = await this.postData(endpoint, JSON.stringify(newData))
      if (resp == 201){
          this.close()
        } else {
          console.log("Registration Stuff Is Wack.")
        }
    },

    async postData(endpoint, newData){
      console.log(newData)
      let token = this.$store.getters.getToken;
      let authHeaders = new Headers(
          {'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`})
      let resp = await fetch(
        endpoint, {
          mode: "cors", 
          method: "POST", 
          headers: authHeaders,
          body: newData});
      return resp.status
    },
    // simple register method
    register(){
      let path = "/api/event"
      let registration = JSON.stringify(
          {
              "name": this.username,
              "email": this.email,
              "password": this.password
          }
      )
    },

    clearForm(){
      this.username = "";
      this.email = "";
      this.password = "";
      },
    },
  }
</script>

<style scoped>
  .window {
    padding: 45px;
  }
</style>