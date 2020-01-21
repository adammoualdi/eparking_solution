<template>
    <div id = "NavBarWrapper">
      <div class="headerNav">
        <b-navbar toggleable="lg" type="dark" variant="info">
            <b-navbar-brand href="">Parking</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav>
                <b-nav-item href="/locations">Requests
                    <!-- <router-link to="/search">Parking Locations</router-link> -->
                </b-nav-item>
                <!-- <b-nav-item v-bind:href="profile">Profile</b-nav-item> -->
                <!-- <b-nav-item v-bind:href="bookings">Bookings</b-nav-item> -->
                <!-- <b-nav-item href="#" disabled>Disabled</b-nav-item> -->
            </b-navbar-nav>

            <!-- Right aligned nav items -->
            <b-navbar-nav class="ml-auto">
                <!-- <b-nav-item-dropdown text="Lang" right>
                <b-dropdown-item href="#">EN</b-dropdown-item>
                <b-dropdown-item href="#">ES</b-dropdown-item>
                <b-dropdown-item href="#">RU</b-dropdown-item>
                <b-dropdown-item href="#">FA</b-dropdown-item>
                </b-nav-item-dropdown> -->
              <b-nav-item href="#">Logout</b-nav-item>

                <!-- <b-nav-item-dropdown right>
                Using 'button-content' slot
                <template v-slot:button-content>
                    <em>User</em>
                </template>
                <b-dropdown-item href="#">Profile</b-dropdown-item>
                <b-dropdown-item href="#">Sign Out</b-dropdown-item>
                </b-nav-item-dropdown> -->
            </b-navbar-nav>
            </b-collapse>
        </b-navbar>
      </div>
    </div>
</template>

<script>
// import { SidebarMenu } from 'vue-sidebar-menu'
// import 'vue-sidebar-menu/dist/vue-sidebar-menu.css'
import '@fortawesome/fontawesome-free/css/all.css'
export default {
  name: 'NavigationBar',
  data () {
    return {
      collapse: true,
      profile: '',
      bookings: '',
      username: null
    }
  },
  mounted () {
    this.profile = '/profile/' + window.localStorage.getItem('username')
    this.bookings = '/bookings/' + window.localStorage.getItem('username')
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
  /* padding-top: 80px; */
}

.navbar {
  z-index: 9999;
}
</style>
