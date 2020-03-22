<template>
  <div id = "BookingWrapper">
    <div class="Nav">
      <!-- <NavigationBar class="nav"> </NavigationBar> -->
    </div>
        <b-container class ="wrapper">
        </b-container>
  </div>
</template>

<script>
import DatePicker from '@/components/DatePicker'
import NavigationBar from '@/components/PARKINGUSER/NavigationBar'
import swal from 'sweetalert'
import PostsService from '@/services/PostsService'
export default {
  name: 'ParkingConfirmation',
  components: {
    DatePicker,
    NavigationBar
  },
  data () {
    return {
    }
  },
  mounted () {
    console.log(this.$route.params.url)
    this.confirmBooking()
  },
  methods: {
    async confirmBooking () {
      const response = await PostsService.confirmParking(this.$route.params.url)
      console.log(response.data)
      const that = this
      if (response.data.errorContent !== undefined) {
        swal('Error!', 'There was an error with this confirmation!', 'error')
          .then(function () {
            console.log('GO TO')
            that.$router.push({ name: 'Login' })
          })
      } else {
        swal('Confirmed!', 'You have successfully confirmed your parking!', 'success')
          .then(function () {
            console.log('GO TO')
            that.$router.push({ name: 'Landing' })
          })
      }
    }
  }
}
</script>

<style>
html,
body {
  margin: 0;
  padding: 0;
  height: 100%;
}

* {
  margin: 0px;
  padding: 0px;
}
</style>
