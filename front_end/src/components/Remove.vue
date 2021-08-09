<template>
    <v-dialog max-width="500px" v-model="showDialog">
     <template v-slot:activator="{ on }">
       <!-- <v-btn  v-on="on" class="success">Add New Project</v-btn> -->
        <v-icon
          small
          class="mr-2"
          color=#00BCD4 
          v-on="on"
        >
          delete
        </v-icon>
     </template>
    <v-card>
      <v-container fill-height fluid>
      <v-row justify="center">
        <v-container class="window">
        <v-container>
          <h2 class="cyan--text font-weight-light">Are You Sure?</h2>
        <v-container>
            <h2 class="white--text font-weight-light">{{this.warning}}</h2>
        </v-container>
        <v-row>
          <v-col>
            <v-container>
              <v-btn outlined color=#00BCD4 @click="softClose">
                <v-icon>arrow_back_ios</v-icon>
              </v-btn>
            </v-container>
          </v-col>
          <v-col>
            <v-container class="text-right">
              <v-btn outlined color=#00BCD4 @click="determineFollowOn">
                <v-icon>delete</v-icon>
              </v-btn>
            </v-container>
          </v-col>
          </v-row>
        </v-container>
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
          customerPath: "api/customers/",
          eventPath: "api/events/",
          registrationPath: "api/registrations/",
          alert: false,
          customers: null,
          events: null,
          registrations: null,
          showDialog: false,
          warning: ""
      } 
    },

    mounted () {},

    components: {},

    created () {
      this.prepare()
    },

    props: {
      selection: Object,
      selectedTab: Object,
      customerApi: Object,
      eventApi: Object,
      registrationApi: Object
    },

    methods :{
      
      close() {
        this.$emit('reload', this.selectedTab.selectedTab)
        this.showDialog = false;
      },

      softClose() {
        this.showDialog = false;
      },

      prepare() {
        let data = this.selection.item;
        switch (this.selectedTab.selectedTab){
          case 'customers':
            this.warning = "You are about to delete a customer."
            break
          case 'events':
            this.warning = "You are about to delete an event." 
            break
          case 'registrations':
            this.warning = "You are about to delete a registration."
            break
        }
      },

      async removeCustomer(customer_id){
        let registrations = JSON.parse(JSON.stringify(this.registrationApi.registrationData))
        console.log("Customers");
        let badRegistrations = []
        let initial = this.eventPath+customer_id
        // check if customer is tied to a specific registration
        for (let i = 0; i < registrations.length; i ++){
          if (JSON.stringify(registrations.at(i)['customer_id']) == JSON.stringify(customer_id)){
            badRegistrations.push(registrations.at(i).id);
        }}
        for (let i = 0; i < badRegistrations.length; i ++){
          let path = this.registrationPath + badRegistrations.at(i);
          await this.deleteData(path);
        }
        await this.deleteData(initial);
      },

      async removeEvent(event_id){
        let registrations = JSON.parse(JSON.stringify(this.registrationApi.registrationData))
        console.log(registrations)
        console.log("Events");
        let badRegistrations = [];
        let initial = this.eventPath+event_id
        // check if event is tied to a specific registration
        for (let i = 0; i < registrations.length; i ++){
          if (JSON.stringify(registrations.at(i)['event_id']) == JSON.stringify(event_id)){
            badRegistrations.push(registrations.at(i).id);
        }}
      

        for (let i = 0; i < badRegistrations.length; i ++){
          let path = this.registrationPath + badRegistrations.at(i);
          await this.deleteData(path);
        }
        await this.deleteData(initial);
      },

      async removeRegistration(registration_id){
        console.log("Registrations");
        let initial = this.registrationPath+registration_id;
        await this.deleteData(initial);
      },

      async deleteData(endpoint){
        console.log(`DELETING ${endpoint}`)
        let token = this.$store.getters.getToken;
        let authHeaders = new Headers(
            {'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`})
        let resp = await fetch(
          endpoint, {
            mode: "cors", 
            method: "DELETE", 
            headers: authHeaders,
            });
        return resp.status
    },
      async determineFollowOn(){
          switch(this.selectedTab.selectedTab){
            case 'customers':
              this.customersTab = true;
              await this.removeCustomer(this.selection.item.id)
              this.close();
              break;
            case 'events':
              this.eventsTab = true;
              await this.removeEvent(this.selection.item.id)
              this.close();
              break;
            case 'registrations':
              this.registrationsTab = true;
              await this.removeRegistration(this.selection.item.id)
              this.close();
              break;
      }
      
    }
  }
}
</script>

<style scoped>
  .window {
    padding: 45px;
    max-width: 500px;
    max-height: 550px;
  }
</style>