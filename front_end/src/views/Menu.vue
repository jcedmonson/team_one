<template>
    <v-card>
    <!-- Toolbar -->
    <v-toolbar color="#00BCD4">  
    <v-row>
      <v-col>
        <v-container>
          <v-toolbar-title class="font-weight-light"> Event Registration </v-toolbar-title>
        </v-container>
      </v-col>
      <v-col>
        <v-container class="text-right">
          <!-- <v-btn color="white" outlined dense small elevation="2">{{user[0]}}</v-btn> -->
          <profile/>
        </v-container>
      </v-col>
    </v-row>
    </v-toolbar>
    <!-- Tabs  -->
    <v-tabs background-color="#00BCD4" color="white" grow>
    <v-tabs-slider color="white"></v-tabs-slider>
    <v-tab @click="refreshCustomers"> Customers </v-tab>
    <v-tab @click="refreshEvents"> Events </v-tab>
    <v-tab @click="refreshRegistrations"> Registrations </v-tab>
    </v-tabs>
    <!-- Table -->
    <v-data-table
    :headers="headers"
    :items="items"
    :items-per-page="10"
    flat
    :loading="loadTable"
    >
    <template v-slot:item.actions="{ item }">
      <modify 
              v-on:reload="reload($event)"
              :selection="{item}" 
              :selectedTab="{selectedTab}"
              :customerApi="{customerData}"
              :eventApi="{eventData}"
              :registrationApi="{registrationData}"
              />
      <remove v-on:reload="reload($event)"
              :selection="{item}"
              :selectedTab="{selectedTab}"
              :customerApi="{customerData}"
              :eventApi="{eventData}"
              :registrationApi="{registrationData}"/>
    </template>
    </v-data-table>
    <v-component class="text-right" v-if="ready">
      <customer v-on:reload="reload($event)"/>
      <event v-on:reload="reload($event)"/>
      <registration v-on:reload="reload($event)"
                    :customerApi="{customerData}"
                    :eventApi="{eventData}"/>
    </v-component>
  <v-snackbar
        color=#00BCD4
        dense
        transition="fade-transition"
        outlined
        top
        >Failure.</v-snackbar>
  </v-card>
</template>


<script>
  import modify from '../components/Modify.vue'
  import remove from '../components/Remove.vue'
  import customer from '../components/Customer.vue'
  import event from '../components/Event.vue'
  import registration from '../components/Registration.vue'
  import profile from '../components/Profile.vue'
  export default {
    name: 'simplename',
    props: {},

    components : {
      modify,
      remove,
      customer,
      event,
      registration,
      profile
    },

    watch:{
      modify
    },

    async mounted () {
      await this.verify()

    },

    data: () => {
      return {
        customerPath: "api/customers",
        eventPath: "api/events",
        registrationPath: "api/registrations",
        user: "",
        customerData : "",
        eventData : "",
        registrationData : "",
        alert : "",
        headers : [],
        items: [],
        loadTable: false,
        selectedTab: "customers",
        dialog: false,
        ready: false,
      }
    },

    methods: {

    reload (selection) {
      this.ready = !this.ready;
      console.log(selection);
      switch(selection){
        case 'customers':
          this.refreshCustomers()
          break;
        case 'events':
          this.refreshEvents()
          break;
        case 'registrations':
          this.refreshRegistrations()
          break;
      }
      
    },

    cleanString(value){
      let result = "";
      let temp = [];
      if (value.indexOf("_") != -1){
        let vSplit = value.split("_")
        vSplit.forEach(e => {
          temp.push(e[0].toUpperCase() + e.substring(1))
          result = temp.join(" ");
        })
      } else {
        result = value[0].toUpperCase() + value.substring(1)
      }
      return result
    },

    async getHeaders(dataset){
      let filter = ['id', 'customer_id', 'event_id'];
      let titles = [];
      let headers = [];
      let data = await JSON.parse(JSON.stringify(dataset));
      // get all header values 
      data.forEach(element => {
        let keys = Object.keys(element);
        for (let v of keys){
          if (filter.indexOf(v) == -1 && titles.indexOf(v) == -1){
            titles.push(v)
          }
        }
      });
      titles.forEach(t => {
        headers.push(
          {
            text: this.cleanString(t), 
            value: t, 
            align:'center',
          }
        )   
      })
      headers.push({text: "Actions", value:"actions", sortable: false})
      this.headers = headers;
      },

    // verify user has token
      async verify(){
        let token = this.$store.getters.getToken;
        if (token == null){
          this.$router.push({name: "Login"});
        } else {
          this.loadTable = ! this.loadTable;
          this.user = this.$store.getters.getUser;
          await this.customers();
          await this.events();
          await this.registrations();
          this.mapRegistrations();
          this.items = this.customerData;
          await this.getHeaders(this.customerData)
          this.selectedTab = "customers";
          this.loadTable = ! this.loadTable;
          this.ready = true;
        }
      },

      async getData(endpoint){
        let token = this.$store.getters.getToken;
        let authHeaders = new Headers({'Authorization': `Bearer ${token}`});
        let resp = await fetch(endpoint, {mode: "cors", method: "GET", headers: authHeaders});
        if (resp.status == 200){
            let respJson = await resp.json();
            return respJson
        } else {
          console.log("error")
        }
      },
  
      async customers(){
        let customers = await this.getData(this.customerPath)
        this.customerData = customers;
      },

      async refreshCustomers(){
        this.ready = false
        this.loadTable = !this.loadTable;
        this.items = [];
        await this.customers();
        await this.getHeaders(this.customerData)
        this.items = this.customerData;
        this.mapRegistrations()
        this.loadTable = !this.loadTable;
        this.selectedTab = "customers"
        this.ready = true

      },
      
      async events(){
        let events = await this.getData(this.eventPath)
        this.eventData = events;
        
      },

      async refreshEvents(){
        this.ready = false
        this.loadTable = !this.loadTable;
        this.items = [];
        await this.events();
        this.mapRegistrations()
        await this.getHeaders(this.eventData)
        this.items = this.eventData;
        this.loadTable = !this.loadTable;
        this.selectedTab = "events"
        this.ready = true
      },

      async registrations(){
        let registrations = await this.getData(this.registrationPath)
        this.registrationData = registrations;        
    },
      
      async refreshRegistrations(){
        this.ready = false
        this.loadTable = !this.loadTable;
        this.items = [];
        await this.registrations();
        this.mapRegistrations();
        await this.getHeaders(this.registrationData)
        this.items = this.registrationData;
        this.loadTable = !this.loadTable;
        this.selectedTab = "registrations"
        this.ready = true
      },


      mapRegistrations(){
        let output = [];
       
        this.registrationData.forEach(r => {
          this.eventData.forEach(e => {
            if (r.event_id == e.id){
              r['event'] = e.title;
            }
          })
          this.customerData.forEach(e => {
            if (r.customer_id == e.id){
              r['customer'] = e.name;
            }
          })

          let keys = Object.keys(r);
          keys.sort();

          let sorted = {};
          keys.forEach(k => {
            sorted[k] = r[k]
          })

          output.push(sorted)
    
        })
        this.registrationData = output;
    },
  }
}
</script>