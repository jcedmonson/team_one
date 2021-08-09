import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import Vuex from 'vuex';
import prompt from './views/RegisterPrompt.vue'

Vue.config.productionTip = false

Vue.use(Vuex);

Vue.component("prompt", prompt)
Vue.component("prompt", prompt)

// Setup vuex store to preserve state.
const store = new Vuex.Store({
  
  state:{
    token: null,
    user: null
  },

  mutations:{
    changeUser(state, user){
      state.user = user;
    },
    changeToken(state, token){
      state.token = token;
    }
  },
  actions: {
    async setToken ({ commit }, token){
      return new Promise((resolve) => {
        commit('changeToken', token)
        resolve()
      })
    },
    async setUser ({ commit }, user){
      return new Promise((resolve) => {
        commit('changeUser', user)
        resolve()
      })
    }
  },
  getters: {
        getToken(state){
          return state.token
        },
        getUser(state){
          return state.user
        }
      }
  })

new Vue({
  router,
  vuetify,
  store,
  render: h => h(App)
}).$mount('#app')
