<template>
  <div id = "BookingWrapper">
    <div class="Nav">
      <NavigationBar class="nav"> </NavigationBar>
    </div>
    <div class="BookingInfoWrapper">
      <div class="BookingContents">
        <div v-if="isMounted">
          <div class="locationInfo">
            <b-card no-body>
              <div class="Image">
                <img src="/tmp/tomcat-docbase.9188529932403793077.8090/owneruser52.62972973892097--1.1084526206885137">
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
                    <!-- <div class="DateTime">
                      {{ dateFunc(location.arriveTime) + ' ' + timeFunc(location.arriveTime) + ' &rArr; '}}
                    </div>
                    <div class="DateTime">
                      {{ dateFunc(location.leavingTime) + ' ' + timeFunc(location.leavingTime) }}
                    </div> -->
                  </div>
                </b-card-text>
                <b-card-text>
                  <!-- <font-awesome-icon icon="money-bill-alt" size="2x"> </font-awesome-icon> -->
                  <!-- <h4> Cost: </h4> -->
                </b-card-text>
              </div>
            </b-card>
          </div>
          <div class="table-wrap" v-if="bookings.length > 0">
            <table>
                <tr>
                  <th width="20%">Name</th>
                  <th width="30%">Email</th>
                  <th width="42.6%">Arrival time</th>
                  <th width="10%">Leaving time</th>
                  <!-- <td width="50%" align="center">Action</td> -->
                </tr>
                <tr v-for="(booking, index) in bookings" :key="index" class="border_bottom" >
                  <td> {{ booking.user.firstname + ' ' + booking.user.lastname }}</td>
                  <td> {{ booking.user.email }} </td>
                  <td>{{ dateFunc(booking.booking.startDate) + ', ' + timeFunc(booking.booking.startDate) }} <br>
                      <!-- {{ booking.postcode }} <br> -->
                      <!-- {{ 'Spaces: ' + location.spaces }} <br> -->
                      <!-- {{ 'Parking owner: ' + bookin.userId.firstname + ' ' + booking.userId.firstname }} <br> -->
                      <!-- {{ 'Email: ' + location.userId.email }} -->
                  </td>
                  <td> {{ dateFunc(booking.booking.endDate) + ', ' + timeFunc(booking.booking.endDate) }} </td>
                </tr>
            </table>
          </div>
        <div v-else>
            There are no requests
        </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PostsService from '@/services/PostsService'
import NavigationBar from '@/components/OWNER/NavigationBarOwner'
import DateConverter from '@/services/DateConvert'
// import swal from 'sweetalert'
export default {
  name: 'LocationOverview',
  components: {
    NavigationBar
  },
  data () {
    return {
      userQuery: {},
      location: null,
      selected: null,
      bookings: null,
      isMounted: false
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
    console.log(this.location)
    this.getBookings()
  },
  methods: {
    async getBookings () {
      const response = await PostsService.getLocationBookings({
        locationId: this.location.id
      })
      console.log(response)
      this.bookings = response.data.ownerOverviewInfo
      this.isMounted = true
    },
    dateFunc (dateTime) {
      return DateConverter.dateConverter(dateTime)
    },
    timeFunc (dateTime) {
      return DateConverter.timeConverter(dateTime)
    }
  //   async saveBooking () {
  //     console.log(this.location)
  //     console.log(this.userQuery)
  //     var uname = window.localStorage.getItem('username')
  //     console.log(this.location)
  //     const response = await PostsService.bookParkingSlot({
  //       id: 1,
  //       locationId: {
  //         locationId: this.location.locationId,
  //         country: this.location.country,
  //         city: this.location.city,
  //         address1: this.location.address1,
  //         address2: this.location.address2,
  //         postcode: this.location.postcode
  //       },
  //       username: uname,
  //       startDate: this.userQuery.aTime,
  //       endDate: this.userQuery.lTime,
  //       active: true
  //     })
  //     console.log(response.data)
  //     const that = this
  //     swal('Booked!', 'Your booking has been successfully made', 'success')
  //       .then(function () {
  //         console.log('GO TO')
  //         that.$router.push({ name: 'Landing' })
  //       })
  //     // this.$router.push({ name: 'Landing' })
  //   }
  }
}
</script>

<style type="text/css">

.BookingInfoWrapper {
    /* display: block; */
    height: 100%;
    /* position: absolute; */
    /* width: calc(100% - 50px); */
    width: calc(100%-10px);
    /* margin-left: 50px; */
    padding-top: 60px;
    margin-left:10px;
    margin-right: 10px;
}

.table-wrap {
  margin-top: 10px;
}

table tr {
  height: 60px;
  /* padding-left: 5px; */
}

table th {
  padding-left: 5px;
}

table td {
  padding-left: 5px;
}

table tr:nth-child(odd) {
    background: #f2f2f2;
}
table tr:nth-child(1) {
    background: #4d7ef7;
    color: #fff;
}

.locationInfo {
    margin-top: 10px;
    /* margin-left: 10px; */
    margin-right: 10px;
    height: auto;
    width: calc(100vw - 20px);
    /* display: inline-block; */
    background-color: white;
}

</style>
