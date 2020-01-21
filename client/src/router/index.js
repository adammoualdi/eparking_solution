import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import Register from '@/components/Register'
import Login from '@/components/Login'
import Authorisation from '@/components/Authorisation'
import Landing from '@/components/PARKINGUSER/Landing'
import Booking from '@/components/PARKINGUSER/Booking'
import Test from '@/components/Test'
import MapSearch from '@/components/PARKINGUSER/MapSearch'
import Profile from '@/components/PARKINGUSER/Profile'
import UserBookings from '@/components/PARKINGUSER/UserBookings'
import OwnerLanding from '@/components/OWNER/OwnerLanding'
import AdminApproval from '@/components/ADMIN/AdminApproval'
import LocationOverview from '@/components/OWNER/LocationOverview'

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
      path: '/search',
      name: 'Landing',
      component: Landing,
      meta: {
        requiresAuth: true,
        roles: {
          role: 'User'
        }
      }
    },
    {
      path: '/profile/:username',
      name: 'Profile',
      component: Profile,
      meta: {
        requiresAuth: true,
        roles: {
          role: 'User'
        }
      }
    },
    {
      path: '/search',
      name: 'MapSearch',
      component: MapSearch,
      props (route) {
        return route.query || {}
      },
      meta: {
        requiresAuth: true,
        roles: {
          role: 'User'
        }
      }
    },
    {
      path: '/test',
      name: 'Test',
      component: Test,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/booking',
      name: 'Booking',
      component: Booking,
      meta: {
        requiresAuth: true,
        roles: {
          role: 'User'
        }
      }
    },
    {
      path: '/bookings/:username',
      name: 'UserBookings',
      component: UserBookings,
      meta: {
        requiresAuth: true,
        roles: {
          role: 'User'
        }
      }
    },
    {
      path: '/locations',
      name: 'OwnerLanding',
      component: OwnerLanding,
      meta: {
        requiresAuth: true,
        roles: {
          role: 'Owner'
        }
      }
    },
    {
      path: '/auth',
      name: 'Authorisation',
      component: Authorisation,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/approval',
      name: 'AdminApproval',
      component: AdminApproval,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/'
    },
    {
      path: '/overview/:locationId',
      name: 'LocationOverview',
      component: LocationOverview,
      meta: {
        requiresAuth: true
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
      // if (to.matched.some(record => record.meta.roles[0] !== localStorage.getItem('role'))) {
      //   next({ name: 'Authorisation' })
      // }
      next()
    }
  } else {
    console.log('NOT CHECKING JWT')
    console.log('NEXT PIPELINE')
    next()
  }
})

export default router
