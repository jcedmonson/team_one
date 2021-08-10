<template>
    <v-dialog max-width="500px" outlined v-model="showDialog">
     <template v-slot:activator="{ on }">
       <!-- <v-btn  v-on="on" class="success">Add New Project</v-btn> -->
       <v-btn text small v-on="on" class="white--text Subtitle 2">
           <v-icon>account_box</v-icon>
           profile
       </v-btn>
    </template>
    <v-card>
      <v-container class="window">
          <v-row class="text-center">
              <v-col>
                <v-btn outlined @click="logout" class="red--text Subtitle 2">
                <v-icon>logout</v-icon>
                Logout
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