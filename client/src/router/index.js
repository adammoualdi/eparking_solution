import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import Register from '@/components/Register'
import Login from '@/components/Login'
import Authorisation from '@/components/Authorisation'
import Landing from '@/components/Landing'
import Booking from '@/components/Booking'
import Test from '@/components/Test'
import MapSearch from '@/components/MapSearch'
import Profile from '@/components/Profile'
import UserBookings from '@/components/UserBookings'

Vue.use(Router)

// export default new Router({
const router = new Router({
  mode: 'history',
  hash: false,
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/register',
      name: 'Register',
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/landing',
      name: 'Landing',
      component: Landing,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/profile/:username',
      name: 'Profile',
      component: Profile,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/search',
      name: 'MapSearch',
      component: MapSearch,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/test',
      name: 'Test',
      component: Test,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/booking',
      name: 'Booking',
      component: Booking,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/bookings/:username',
      name: 'UserBookings',
      component: UserBookings,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/auth',
      name: 'Authorisation',
      component: Authorisation,
      meta: {
        requiresAuth: false
      }
    }

    // {
    //   path: '*',
    //   redirect: '/'
    // }
  ]
})

// Check if auth is needed before each request
router.beforeEach((to, from, next) => {
  console.log('BEFORE ROUTE')
  if (to.matched.some(record => record.meta.requiresAuth)) {
    console.log('CHECKING JWT ')
    console.log('JWT ' + localStorage.getItem('jwt'))
    if (localStorage.getItem('jwt') === null || localStorage.getItem('jwt') === undefined) {
      next({ name: 'Authorisation' })
    } else {
      next()
    }
  } else {
    console.log('NOT CHECKING JWT')
    console.log('NEXT PIPELINE')
    next()
  }
})

export default router
