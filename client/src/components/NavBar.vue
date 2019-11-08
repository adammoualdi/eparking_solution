<template>
    <div id = "NavBarWrapper">
      <div class="headerNav">
      </div>
      <div id="mySidenav" class="sidenav"><div>
        <v-sidebar
            :menu="menu"
            :collapsed="collapse"
            width="254px"></v-sidebar>
        <div id="overlay" @click="collapse = !collapse"></div>
        </div>
      </div>
    </div>
</template>

<script>
import { SidebarMenu } from 'vue-sidebar-menu'
import 'vue-sidebar-menu/dist/vue-sidebar-menu.css'
import '@fortawesome/fontawesome-free/css/all.css'
export default {
  name: 'NavBar',
  components: {
    'v-sidebar': SidebarMenu
  },
  data () {
    return {
      collapse: true,
      menu: [
        {
          href: '/landing',
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
          href: '/profile',
          title: 'Profile',
          icon: 'fas fa-id-card'
        },
        {
          title: 'Testing',
          icon: 'fa fa-coffee'
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

/* .MainNavBar { */
    /* display: block; */
/* } */

/* .v-sidebar-menu.vsm_collapsed { */
  /* height: 100%; */
  /* padding-top: 60px; */
  /* display: inline-block; */
  /* display: block; */
  /* position: fixed; */
/* } */

.v-sidebar-menu {
  height: 100%;
  padding-top: 60px;
  /* display: inline-block; */
  position: fixed;
  box-sizing: border-box;
}
/*
#NavBarWrapper {

} */

.headerNav {
  width: 100%;
  height: 60px;
  background-color: #2a2a2e;
  position: absolute;
  /* margin-top: -60px; */
  display: block;
  padding: 0px;
}

#mySidenav {
  padding: 0px;
  margin: 0px;
}
</style>
