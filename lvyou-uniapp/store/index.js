// store/index.js
import { defineStore } from 'pinia'

export const useMainStore = defineStore('main', {
  // state
  state: () => ({
    tagNum: 0
  }),
  
  // getters
  getters: {
    param: (state) => state.tagNum
  },
  
  // actions
  actions: {
    setParam(payload) {
      this.tagNum = payload
    },
    
    setParamAction(count) {
      this.setParam(count)
    }
  }
})