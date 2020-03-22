<template>
    <div id = "NavBarWrapper">
      <div class="headerNav">
        <b-navbar toggleable="lg" type="dark" variant="dark">
            <b-navbar-brand href="#">Parking</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav>

            </b-navbar-nav>

            <!-- Right aligned nav items -->
            <b-navbar-nav class="ml-auto">
            </b-navbar-nav>
            </b-collapse>
        </b-navbar>
      </div>
    </div>
</template>

<script>
// import { SidebarMenu } from 'vue-sidebar-menu'
import 'vue-sidebar-menu/dist/vue-sidebar-menu.css'
import '@fortawesome/fontawesome-free/css/all.css'
export default {
  name: 'NavigationBarReg',
  data () {
    return {
      collapse: true,
      username: null,
      menu: [
        {
          href: '/search',
          title: 'Parking',
          icon: 'fas fa-parking'
          /*
          disabled: true
          badge: {
            text: 'new',
            // class:''
          }
             */
        },
        {
          href: '/profile/' + window.localStorage.getItem('username'),
          title: 'Profile',
          icon: 'fas fa-id-card'
        },
        {
          href: '/bookings/' + window.localStorage.getItem('username'),
          title: 'Bookings',
          icon: 'fas fa-car'
        }
      ]
    }
  }
}
</script>

<style>
html,
body {
  margin: 0;
  padding: 0;
  /* height: 100%; */
}

* {
  margin: 0px;
  padding: 0px;
}

head {
  margin: 0px;
  padding: 0px;
}

.headerNav {
  width: 100%;
  height: 60px;
  /* background-color: #2a2a2e; */
  position: absolute;
  /* margin-top: -60px; */
  display: block;
  padding: 0px;
}

.wrapper {
  padding-top: 80px;
}

.navbar {
  z-index: 9999;
}
</style>
