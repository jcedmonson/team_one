<template>
    <v-dialog max-width="500px" outlined v-model="showDialog">
     <template v-slot:activator="{ on }">
       <!-- <v-btn  v-on="on" class="success">Add New Project</v-btn> -->
       <v-btn outlined color=#00BCD4 v-on="on">
           <v-icon>app_registration</v-icon>
       </v-btn>
     </template>
    <v-card>
      <v-container fill-height fluid>
      <v-row justify="center">
        <v-container class="window">
        <v-container>
          <h1 class="cyan--text font-weight-light">Register.</h1>
        </v-container>
        <v-container>
          <v-text-field
              v-model="username"
              label="Username"
              outlined
              dark
              color=#00BCD4
          ></v-text-field>  
          <v-text-field
              v-model="email"
              label="Email"
              outlined
              dark
              color=#00BCD4
          ></v-text-field>
          <v-text-field
              v-model="password"
              label="Password"
              type="password" 
              outlined
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
              <v-btn outlined color=#00BCD4 @click="register">
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
    name: 'prompt',
    data: () => {
      return {
          alert: false,
          username: null,
          password: null,
          email: null,
          showDialog: false,
      } 
    },
    components: {},

    methods :{

    close(){
      this.showDialog = false;
    },

    toggleAlert(alert){
        this.alert = alert;
      },
      // simple register method
      register(){
        let path = "/account/register"
        let registration = JSON.stringify(
            {
                "name": this.username,
                "email": this.email,
                "password": this.password
            }
        )
        fetch(path, {
          mode: "cors",
          method: 'POST',
          headers: new Headers({ "Content-Type": "application/json"}),
          body: registration}
          ).then(response => {
          if (response.status == 200) {
              this.toggleAlert("Account Created");
              this.clearForm()
              this.close();
            // follow on re-direction to login
          } else {
            this.toggleAlert("Creation Failed")
          }
        })
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
    max-width: 500px;
    max-height: 550px;
  }
</style>
