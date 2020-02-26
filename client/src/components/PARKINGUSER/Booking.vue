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
              <div class="Image">
                IMAGE
              </div>
              <div class="BookingInfo">
                <b-card-text>
                  <h2> {{ location.address1 + ', ' + location.address2 }} </h2>
                </b-card-text>
                <b-card-text>
                  <h4>{{ location.country + ', ' + location.city + ', ' + location.postcode}} </h4>
                  </b-card-text>
                <!-- <b-card-text>{{ location.postcode }} </b-card-text> -->
                <!-- <b-card-text>{{ userQuery.aTime + ' -> ' + userQuery.lTime }} </b-card-text> -->
                <b-card-text>
                  <div class="DateTimeInfo">
                    <!-- <font-awesome-icon icon="calendar-alt" size="3x"> </font-awesome-icon> -->
                    <!-- <div class="TimeInfo">
                       -->
                    <div class="DateTime">
                      {{ dateFunc(location.arriveTime) + ' ' + timeFunc(location.arriveTime) + ' &rArr; '}}
                    </div>
                    <div class="DateTime">
                      {{ dateFunc(location.leavingTime) + ' ' + timeFunc(location.leavingTime) }}
                    </div>
                  </div>
                </b-card-text>
                <b-card-text>
                  <!-- <font-awesome-icon icon="money-bill-alt" size="2x"> </font-awesome-icon> -->
                  <!-- <h4> Cost: </h4> -->
                </b-card-text>
              </div>
              <button type="button" name="button" v-on:click="saveBooking">BOOK</button>
            </b-card>
          </div>
        </b-container>
      </div>
    </div>
  </div>
</template>

<script>
import DateConverter from '@/services/DateConvert'
import PostsService from '@/services/PostsService'
import NavigationBar from '@/components/PARKINGUSER/NavigationBar'
import swal from 'sweetalert'
export default {
  name: 'Booking',
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
      this.$router.push({ name: 'Landing' })
    }
    this.location = this.$route.params.location
    // this.userQuery = this.$route.params.times
  },
  mounted () {
  },
  methods: {
    async saveBooking () {
      console.log(this.location)
      // console.log(this.userQuery)
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
        // startDate: this.userQuery.aTime,
        // endDate: this.userQuery.lTime,
        startDate: this.location.arriveTime,
        endDate: this.location.leavingTime,
        active: true
      })
      console.log(response.data)
      const that = this
      // NEED TO CHECK IF ERROR
      swal('Booked!', 'Your booking has been successfully made', 'success')
        .then(function () {
          console.log('GO TO')
          that.$router.push({ name: 'Landing' })
        })
      // this.$router.push({ name: 'Landing' })
    },
    dateFunc (dateTime) {
      return DateConverter.dateConverter(dateTime)
    },
    timeFunc (dateTime) {
      return DateConverter.timeConverter(dateTime)
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
    width: 100%;
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

</style>
