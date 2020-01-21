<template>
  <div id = "BookingWrapper">
    <div class="Nav">
      <NavigationBar class="nav"> </NavigationBar>
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
import NavigationBar from '@/components/PARKINGUSER/NavigationBar'
import swal from 'sweetalert'
export default {
  name: 'LocationOverview',
  components: {
    NavigationBar
  },
  data () {
    return {
      userQuery: {},
      location: null,
      selected: null
    }
  },
  created () {
    // console.log(this.$route.params.times)
    if (this.$route.params.location === undefined) {
      console.log('ERROR')
      this.$router.push({ name: 'OwnerLanding' })
    }
    this.location = this.$route.params.location
  },
  mounted () {
  },
  methods: {
    async saveBooking () {
      console.log(this.location)
      console.log(this.userQuery)
      var uname = window.localStorage.getItem('username')
      console.log(this.location)
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
      const that = this
      swal('Booked!', 'Your booking has been successfully made', 'success')
        .then(function () {
          console.log('GO TO')
          that.$router.push({ name: 'Landing' })
        })
      // this.$router.push({ name: 'Landing' })
    }
  }
}
</script>

<style type="text/css">

.BookingInfoWrapper {
    /* display: block; */
    height: 100%;
    /* position: absolute; */
    /* width: calc(100% - 50px); */
    width: 100%;
    /* margin-left: 50px; */
    padding-top: 60px;
}

.BookingWrapper {
    margin: 0px;
    padding: 0px;
}

.swal-modal {
    /* margin-left: 50px; */
    /* width: calc(100% - 60px); */
    /* margin:10px; */
    width: 95%;
}

</style>
