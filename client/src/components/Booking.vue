<template>
  <div id = "BookingWrapper">
    <div class="Nav">
      <NavBar class="nav"> </NavBar>
    </div>
    <div class="BookingInfoWrapper">
      <div class="BookingContents">
        <b-container class ="wrapper">
          <div>
            <b-card no-body>
              <b-card-text>{{ location.country }}</b-card-text>
              <b-card-text>{{ location.city }} </b-card-text>
              <b-card-text>{{ location.address1 + ' ' + location.address2 }} </b-card-text>
              <b-card-text>{{ location.postcode }} </b-card-text>
              <b-card-text>{{ userQuery.aTime + ' -> ' + userQuery.lTime }} </b-card-text>
              <button type="button" name="button" v-on:click="saveBooking">BOOK</button>
            </b-card>
          </div>
        </b-container>
      </div>
    </div>
  </div>
</template>

<script>
import PostsService from '@/services/PostsService'
import NavBar from '@/components/NavBar'
export default {
  name: 'Booking',
  components: {
    NavBar
  },
  data () {
    return {
      userQuery: {},
      location: null,
      selected: null
    }
  },
  created () {
    console.log(this.$route.params.times)
    if (this.$route.params.location === undefined) {
      console.log('ERROR')
      this.$router.push({ name: 'Landing' })
    }
    this.location = this.$route.params.location
    this.userQuery = this.$route.params.times
  },
  mounted () {
  },
  methods: {
    async saveBooking () {
      console.log(this.location)
      console.log(this.userQuery)
      var uname = window.localStorage.getItem('username')
      const response = await PostsService.bookParkingSlot({
        id: 1,
        locationId: {
          locationId: this.location.locationId,
          country: this.location.country,
          city: this.location.city,
          address1: this.location.address1,
          address2: this.location.address2,
          postcode: this.location.postcode
        },
        username: uname,
        startDate: this.userQuery.aTime,
        endDate: this.userQuery.lTime,
        active: true
      })
      console.log(response.data)
      this.$router.push({ name: 'Landing' })
    }
  }
}
</script>

<style type="text/css">

.BookingInfoWrapper {
    /* display: block; */
    height: 100%;
    /* position: absolute; */
    width: calc(100% - 50px);
    margin-left: 50px;
    padding-top: 60px;
}

.BookingWrapper {
    margin: 0px;
    padding: 0px;
}

</style>
