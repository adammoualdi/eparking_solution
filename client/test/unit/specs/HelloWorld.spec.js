// import Vue from 'vue'
// import HelloWorld from '@/components/Login'
// // import Login from '@/components/Login'
// // import VueRouter from 'vue-router'

// describe('Login.vue', () => {
//   it('should render correct contents', () => {
//     const Constructor = Vue.extend(Login)
//     const vm = new Constructor().$mount()
//     expect(1+1)
//       .to.equal(2)
//   })
// })

import { mount, createLocalVue } from '@vue/test-utils'
import App from '@/App.vue'
import VueRouter from 'vue-router'
import NestedRoute from '@/components/Login.vue'
import routes from '@/router/index.js'

const localVue = createLocalVue()
localVue.use(VueRouter)

describe('App', () => {
  it('renders a child component via routing', async () => {
    const router = new VueRouter({ routes })
    const wrapper = mount(App, {
      localVue,
      router
    })

    router.push('/login')
    await wrapper.vm.$nextTick()

    expect(wrapper.find(NestedRoute).exists()).toBe(true)
  })
})
