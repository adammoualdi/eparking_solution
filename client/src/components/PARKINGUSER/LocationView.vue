<template>
  <div id = "LocationViewWrapper">
    <!-- <div class="LocationViewInfoWrapper"> -->
      <!-- <div class="LocationViewContents"> -->
        <!-- <b-container class ="wrapper">
          <div>
            <b-card no-body>
                TEST
            </b-card>
          </div>
        </b-container> -->
    <div v-if="firstInit">
        <div class="LocationView">
            <b-card bg-variant="dark" text-variant="white" class="card">
                <b-card-text>
                    <h3>{{ location.address1 + ', ' + location.address2 }} </h3>
                    <h5>{{ location.country + ', ' + location.city + ', ' + location.postcode }}</h5>
                    <!-- {{ location.arriveTime + ' &rArr; ' + location.leavingTime }} -->
                    {{ arriveTimeFunc + ' &rArr; ' + leaveTimeFunc }}
                </b-card-text>
                <!-- <b-button href="#" variant="primary">Go somewhere</b-button> -->
                <b-button type="submit" variant="primary" v-on:click="goToBooking">Book</b-button>&nbsp;
            </b-card>
        </div>
    </div>
      <!-- </div> -->
    <!-- </div> -->
  </div>
</template>

<script>
// import PostsService from '@/services/PostsService'
export default {
  name: 'LocationView',
  data () {
    return {
      firstInit: false
    }
  },
  props: {
    location: null,
    booking: null
  },
  watch: {
    location: function () {
      // bool value used to set validation events off after first round of init.
      this.firstInit = true
      console.log('LOCATION CHANGED FOR MOBILE LOCATION VIEW--------------------------')
      window.scrollTo(0, document.body.scrollHeight || document.documentElement.scrollHeight)
    }
  },
  computed: {
    titleText () {
      return location.address1 + location.address2
    },
    arriveTimeFunc: function () {
      console.log(this.location.arriveTime)
      var date = this.location.arriveTime.substring(0, 10)
      var time = this.location.arriveTime.substring(11, 16)
      console.log(date + ' ' + time)
      var ret = date + ' ' + time
      return ret
    },
    leaveTimeFunc: function () {
      var date = this.location.leavingTime.substring(0, 10)
      var time = this.location.leavingTime.substring(11, 16)
      console.log(date + ' ' + time)
      var ret = date + ' ' + time
      return ret
    }
  },
  created () {
  },
  methods: {
    goToBooking () {
      this.$router.push({ name: 'Booking', params: {location: this.location, times: this.booking} })
    }
  }
}
</script>

<style type="text/css">

.LocationViewInfoWrapper {
    /* display: block; */
    /* height: 100%; */
    /* position: absolute; */
    /* display: block; */
    background: red;
    height: 100%;
    /* width: calc(100% - 50px); */
    width: 100%;
    /* margin-left: 50px; */
    /* height: 20vh; */
    /* padding-top: 60px; */
}

#LocationViewWrapper {
    display: block;
    width: 100%;
    /* height: 100%; */
    background: #2a2a2e;
}

.LocationView {
    /* height: calc(40vh - 60px); */
    height: 100%;
}

.LocationView .card {
    /* height: calc(40vh - 60px); */
    height: 100%;
}

</style>
