<template>
    <v-dialog max-width="500px" outlined v-model="showDialog">
     <template v-slot:activator="{ on }">
       <!-- <v-btn  v-on="on" class="success">Add New Project</v-btn> -->
       <v-btn color=#00BCD4 text small v-on="on">
           <v-icon>add</v-icon>
           <v-text class="cyan--text Subtitle 2">event</v-text>
       </v-btn>
     </template>
    <v-card>
      <v-container fill-height fluid>
      <v-row justify="center">
        <v-container class="window">
        <v-container>
          <h1 class="cyan--text font-weight-light">Add an event.</h1>
        </v-container>
        <v-container>
          <v-text-field
              v-model="title"
              label="Title"
              dark
              color=#00BCD4
          ></v-text-field>  
          <v-text-field
              v-model="description"
              label="Description"
              dark
              color=#00BCD4
          ></v-text-field>
          <v-text-field
              v-model="code"
              label="Code"
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
              <v-btn outlined color=#00BCD4 @click="create">
              <v-icon>arrow_forward_ios</v-icon>
              </v-btn>
            </v-container>
            </v-col>
          </v-row>
        </v-container>
        </v-container>
      </v-row>
      <v-snackbar
          :value="alert"
          color=#00BCD4
          dense
          transition="fade-transition"
          outlined
          top
          ></v-snackbar>
    </v-container>
    </v-card>
   </v-dialog>
</template>

<script>
  export default {
    name: 'event',
    data: () => {
      return {
          alert: false,
          title: null,
          description: null,
          code: null,
          showDialog: false,

      } 
    },
    components: {},

    methods :{
        
    close(){
        //{'id': 0, 'title': 'Cash-Money', 'description': 'Lectures and exhibits covering all Beat Making topics', 'code': '42069'}
      this.$emit('reload', 'events')
      this.showDialog = false;
    },

    toggleAlert(alert){
        this.alert = alert;
      },
      // simple register method
      create(){
        let token = this.$store.getters.getToken;
        let path = "/api/events"
        let event = JSON.stringify(this.constructBody());
        fetch(path, {
          mode: "cors",
          method: 'POST',
          headers: new Headers(
              { "Content-Type": "application/json", 'Authorization': `Bearer ${token}`}),
          body: event}
          ).then(response => {
          if (response.status == 201) {
              this.toggleAlert("Event Created");
              this.close()
            // follow on re-direction to login
          } else {
            this.toggleAlert("Creation Failed")
          }
        })
      },

      constructBody(){
          let newData = {};
          newData['id'] = 0;
          newData['title'] = this.title;
          newData['description'] = this.description;
          newData['code'] = this.code;
          return newData;
      },

      clearForm(){
          this.title = "";
          this.description = "";
          this.code = "";
      },
    },
  }
</script>

<style scoped>
  .window {
    padding: 45px;
  }
</style>