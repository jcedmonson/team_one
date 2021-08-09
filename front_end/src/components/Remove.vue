<template>
    <v-dialog max-width="500px" outlined>
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
          <h1 class="display-1">{{this.selection}}</h1>
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
          alert: false,
          username: null,
          password: null,
          email: null
      } 
    },

    mounted () {},

    components: {},

    props: {
      selection: Object
    },

    methods :{},
  }
</script>

<style scoped>
  .window {
    padding: 45px;
    max-width: 500px;
    max-height: 550px;
  }
</style>