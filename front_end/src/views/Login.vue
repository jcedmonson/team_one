<template>
  <v-container fill-height fluid>
      <v-row justify="center">
        <v-container class="window">
          <v-container>
          <h1 class="font-weight-light">Login.</h1>
          </v-container>
        <v-container>
          <v-text-field
              v-model="username"
              label="Username"
              outlined
              clearable
              dark
              color=#00BCD4
          ></v-text-field>
          <v-text-field
              v-model="password"
              label="Password"
              type="password"
              outlined
              clearable
              dark
              color=#00BCD4
          ></v-text-field>
          <v-row>
            <v-col>
            <v-container>
              <prompt/>
            </v-container>
            </v-col>
            <v-col>
            <v-container class="text-right">
              <v-btn outlined color=#00BCD4 @click="login">
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
          >Login Failed.</v-snackbar>
  </v-container>
</template>

<script>
  export default {
    name: 'Login',

    components: {},

    data: () => {
      return {
        alert: false,
        username: null,
        password: null,
      }  
    },

    mounted () {
      this.clearUser()
    },

    methods :{
      // login, if valid set user and token.
      async login() {
        let path = "/account/token";
        let resp = await fetch(path, {
                  mode: "cors",
                  method: 'POST',
                  headers: new Headers({ "Content-Type": "application/json"}),
                  body: JSON.stringify({
                    "name": this.username,
                    "password": this.password
                    })})
        if (resp.status == 200) {
          let respJson = await resp.json();
          await this.setTokenUser(respJson.token, this.username)
          this.$router.push({name: "Menu"})
        } else {
          this.toggleAlert()
        }
      },
      
      // sleep helper function
      sleep(time){
        return new Promise((resolve) => setTimeout(resolve, time));
      },

      // toggle login alert
      toggleAlert(){
        this.alert = !this.alert;
        this.sleep(2000).then(() => {this.alert = false})
      },

      async setTokenUser(token, user) {
        await this.$store.dispatch('setToken', token)
        await this.$store.dispatch('setUser', user)
      },

      clearUser(){
        this.$store.dispatch('setToken', null)
        this.$store.dispatch('setUser', null)
      }
    },

    components: {},
  }
</script>

<style scoped>
  .window {
    border-radius: 25px;
    border-width: 1px;
    border-style: solid;
    border-color: #00BCD4;
    padding: 20px;
    max-width: 500px;
    max-height: 400px;
  }
</style>
