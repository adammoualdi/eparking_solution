<template>
  <div id = "BookingWrapper">
    <div class="Nav">
      <NavigationBar class="nav"> </NavigationBar>
    </div>
    <div class="BookingInfoWrapper">
      <div class="BookingContents">
        <b-container class = "wrapper">
          <div>
            <b-card class = "card">
              <p>Purchase points to use when you book your next parking slot.</p>
              <p>100 points = £1</p>
              <b-input-group>
                <b-form-input
                        class="locationFormInput"
                        type="number"
                        placeholder="Points"
                        v-model="points">
                </b-form-input>
                <b-button variant="primary" v-on:click="purchase">Purchase</b-button>
              </b-input-group>
            </b-card>
          </div>
        </b-container>
      </div>
    </div>
  </div>
</template>

<script>
// import DateConverter from '@/services/DateConvert'
import PostsService from '@/services/PostsService'
import NavigationBar from '@/components/PARKINGUSER/NavigationBar'
// import swal from 'sweetalert'
export default {
  name: 'PurchasePoints',
  components: {
    NavigationBar
  },
  data () {
    return {
      points: null
    }
  },
  created () {
    // console.log(this.$route.params.times)
    // if (this.$route.params.location === undefined) {
    //   console.log('ERROR')
    //   this.$router.push({ name: 'Landing' })
    // }
    // this.location = this.$route.params.location
    // this.userQuery = this.$route.params.times
    console.log('TEST')
  },
  mounted () {
  },
  methods: {
    async purchase () {
      // Put var in object to send to server
      var points = this.points
      var obj = { points }
      const response = await PostsService.purchasePoints(obj)
      console.log(response)
      window.localStorage.setItem('points', response.data.points)
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

.Image {
  width: 50%;
  height: 30vh;
  background-color: red;
}
@media (max-width: 450px) {
  .Image {
    width: 100%;
  }

  .BookingInfo {
    margin-left: 30px;
    margin-top: 20px;
  }

  .container {
    padding: 0px;
  }
}

.swal-modal {
    /* margin-left: 50px; */
    /* width: calc(100% - 60px); */
    /* margin:10px; */
    /* width: 100%; */
}

.DateTimeInfo {
  width: 100%;
  /* float: left; */
  display: flex;
}

.TimeInfo {
  font-size: 40px;
  /* float: left; */
  /* positio */
}

.DateTime {
  /* float: left; */
}

.card {

}

</style>
