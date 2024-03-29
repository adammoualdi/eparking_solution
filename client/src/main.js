// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Vuelidate from 'vuelidate'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VCalendar from 'v-calendar'
// import VueSidebarMenu from 'vue-sidebar-menu'
import 'vue-sidebar-menu/dist/vue-sidebar-menu.css'
import { Datetime } from 'vue-datetime'
import 'vue-datetime/dist/vue-datetime.css'
import { library } from '@fortawesome/fontawesome-svg-core'
// import { faUserSecret } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faUser, faSearchLocation, faPlus, faCar, faExclamationCircle, faCheckCircle, faCalendarAlt, faMoneyBillAlt } from '@fortawesome/free-solid-svg-icons'
import VueMq from 'vue-mq'
import { Settings } from 'luxon'
import VueMoment from 'vue-moment'
import moment from 'moment-timezone'
import PieChart from 'vue-pie-chart/src/PieChart.vue'

moment.tz.guess()
Vue.use(VueMoment, { moment })

library.add(faUser, faSearchLocation, faPlus, faCar, faExclamationCircle, faCheckCircle, faCalendarAlt, faMoneyBillAlt)

// import './assets/css/style.css'
// library.add(faUserSecret)
Vue.component('font-awesome-icon', FontAwesomeIcon)
Vue.component('pie-chart', PieChart)

Vue.config.productionTip = false
Vue.use(Vuelidate)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(VCalendar)
Vue.use(Datetime)
Vue.use(VueMq, {
  breakpoints: {
    mobile: 450,
    tablet: 900,
    laptop: 1250,
    desktop: Infinity
  }
})
Settings.defaultLocale = 'en'
// Vue.use(VueSidebarMenu)
// Vue.component('datepicker', Datepicker)

// const bus = new Vue()

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: {
    App
  },
  template: '<App/>'
})
