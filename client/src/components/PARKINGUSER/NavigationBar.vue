<template>
    <div id = "NavBarWrapper">
      <div class="headerNav">
        <b-navbar toggleable="lg" type="dark" variant="dark">
            <b-navbar-brand href="">Parking</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav>
                <b-nav-item href="/search">Parking Locations</b-nav-item>
                <b-nav-item v-bind:href="profile">Profile</b-nav-item>
                <b-nav-item v-bind:href="bookings">Bookings</b-nav-item>
            </b-navbar-nav>

            <!-- Right aligned nav items -->
            <b-navbar-nav class="ml-auto">
              <!-- <b-nav-item href="/purchase"> {{ points }} Points</b-nav-item> -->
            </b-navbar-nav>
            </b-collapse>
            <b-nav-item v-b-modal.modal>£{{ deposit }}</b-nav-item>
            <b-nav-item href="/">Logout</b-nav-item>
        </b-navbar>
      </div>
      <b-modal
      id="modal"
      ref="modal"
      title="Deposit money"
      modal-footer=hide>
        <p>Purchase points to use when you book your next parking slot.</p>
        <p>100 points = £1</p>
        <form ref="form" @submit.stop.prevent="handleSubmit">
          <b-input-group>
            <b-form-input
                    class="locationFormInput"
                    type="number"
                    placeholder="Deposit"
                    v-model="depositValue">
            </b-form-input>
            <b-button size="sm" variant="success" @click="purchase()">
              Purchase
            </b-button>
            <!-- <b-button variant="primary" v-on:click="purchase">Purchase</b-button> -->
          </b-input-group>
        </form>
        <template v-slot:modal-footer="{ hide }">
          {{ hide }}
        </template>
      </b-modal>
    </div>
</template>

<script>
import '@fortawesome/fontawesome-free/css/all.css'
import PostsService from '@/services/PostsService'
export default {
  name: 'NavigationBar',
  data () {
    return {
      collapse: true,
      profile: '',
      bookings: '',
      username: null,
      deposit: 0,
      depositValue: 10
    }
  },
  mounted () {
    this.profile = '/profile/' + window.localStorage.getItem('username')
    this.bookings = '/bookings/' + window.localStorage.getItem('username')
    this.deposit = window.localStorage.getItem('deposit')
    console.log(this.points)
  },
  methods: {
    async purchase () {
      // Put var in object to send to server
      var deposit = this.depositValue
      var obj = { deposit }
      const response = await PostsService.purchasePoints(obj)
      console.log(response)
      this.deposit = response.data.deposit
      window.localStorage.setItem('deposit', response.data.deposit)
      this.$nextTick(() => {
        this.$bvModal.hide('modal')
      })
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
  /* padding-top: 80px; */
}

.navbar {
  z-index: 9999;
}

#modal {
  margin-top: 100px;
}
</style>
