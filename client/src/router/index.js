import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import Register from '@/components/Register'
import Posts from '@/components/Posts'
// import Test from '@/components/Test'
import NewPost from '@/components/NewPost'
import EditPost from '@/components/EditPost'
import Login from '@/components/Login'
import Authorisation from '@/components/Authorisation'
import Landing from '@/components/Landing'
import Booking from '@/components/Booking'
import Test from '@/components/Test'
import MapSearch from '@/components/MapSearch'
import Profile from '@/components/Profile'

Vue.use(Router)

// export default new Router({
const router = new Router({
  mode: 'history',
  hash: false,
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/landing',
      name: 'Landing',
      component: Landing
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile
    },
    {
      path: '/search',
      name: 'MapSearch',
      component: MapSearch
    },
    {
      path: '/test',
      name: 'Test',
      component: Test
    },
    {
      path: '/booking/:id',
      name: 'Booking',
      component: Booking
    },
    {
      path: '/posts',
      name: 'Posts',
      component: Posts,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/posts/new',
      name: 'NewPost',
      component: NewPost,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/posts/:id',
      name: 'EditPost',
      component: EditPost,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/auth',
      name: 'Authorisation',
      component: Authorisation
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
    console.log('JWT ' + localStorage.getItem('jwt'))
    if (localStorage.getItem('jwt') === null || localStorage.getItem('jwt') === 'undefined') {
      // next({
      //   path: '/login',
      //   params: { nextUrl: to.fullPath }
      // })
      next({ name: 'Authorisation' })
    // } else {
    //   let user = JSON.parse(localStorage.getItem('user'))
    //   if (to.matched.some(record => record.meta.is_admin)) {
    //     if (user.is_admin === 1) {
    //       next()
    //     } else {
    //       next({ name: 'Login' })
    //     }
    //   } else {
    //     next()
    //   }
    } else {
      next()
    }
  // } else if (to.matched.some(record => record.meta.guest)) {
  //   if (localStorage.getItem('jwt') === null) {
  //     next()
  //   } else {
  //     next({ name: 'Login' })
  //   }
  } else {
    console.log('NEXT PIPELINE')
    next()
  }
})

export default router
