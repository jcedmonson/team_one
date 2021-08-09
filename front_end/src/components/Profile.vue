<template>
    <v-dialog max-width="500px" outlined v-model="showDialog">
     <template v-slot:activator="{ on }">
       <!-- <v-btn  v-on="on" class="success">Add New Project</v-btn> -->
       <v-btn color=white text small v-on="on" name="Registration">
           <v-icon>account_box</v-icon>
           <v-text class="white--text Subtitle 2">Profile</v-text>
       </v-btn>
    </template>
    <v-card>
      <v-container class="window">
          <v-row class="text-center">
              <v-col>
                <v-btn color=red outlined @click="logout">
                <v-icon>logout</v-icon>
                <v-text class="white--text Subtitle 2">Logout</v-text>
                </v-btn>
              </v-col>
          </v-row>
      </v-container>
  </v-card>
  </v-dialog>
</template>

<script>
  export default {
    name: 'profile',
    data: () => {
      return {
          showDialog: false,
       

      } 
    },
    components: {},

    props:{
      
    },


    methods :{
    logout(){
        this.$router.push({name: "Login"})
    },

    close(){
      this.$emit('reload', 'registrations')
      this.showDialog = false;
    }
  }
}
</script>

<style scoped>
  .window {
    padding: 45px;
  }
</style>