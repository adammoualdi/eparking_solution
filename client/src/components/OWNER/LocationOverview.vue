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
              <div class="BookingInfo">
                <b-card-text>
                  <h2> {{ location.address1 + ', ' + location.address2 }} </h2>
                </b-card-text>
                <b-card-text>
                  <h4>{{ location.country + ', ' + location.city + ', ' + location.postcode}} </h4>
                </b-card-text>
                <b-card-text>
                  Cost Per Hour: {{ locationInfo.costPerHour}}
                  Spaces: {{ locationInfo.spaces }}
                </b-card-text>
                <div class="ChartBox">
                  <div class="Chart">
                    <PieChart
                      :percent=percent
                      :stroke-width=1
                      :label=percentText
                      label-small="Bookings today"
                      color=#40a070
                      :opacity=1
                    />
                  </div>
                  <div class="Chart">
                    <PieChart
                      :percent=percentWeek
                      :stroke-width=1
                      :label=percentWeekText
                      label-small="Weekly bookings"
                      color=#40a070
                      :opacity=1
                    />
                  </div>
                </div>
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
          <div class="table-wrap" v-if="bookings[0].booking != null">
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
                  <td>{{ dateFunc(booking.booking.endDate) + ', ' + timeFunc(booking.booking.endDate) }} </td>
                </tr>
            </table>
          </div>
        <div v-else>
          There are no bookings
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
import PieChart from 'vue-pie-chart/src/PieChart.vue'
// import swal from 'sweetalert'
export default {
  name: 'LocationOverview',
  components: {
    NavigationBar,
    PieChart
    // 'pie-chart': PieChart
  },
  data () {
    return {
      userQuery: {},
      location: null,
      selected: null,
      bookings: null,
      locationInfo: null,
      percent: 0,
      percentText: '',
      percentWeek: 0,
      percentWeekText: '',
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
      this.locationInfo = response.data.ownerOverviewInfo[0].location
      this.bookings = response.data.ownerOverviewInfo
      console.log(this.bookings)
      this.percent = response.data.percentageBooked * 100
      this.percentWeek = response.data.percentageBookedWeek * 100
      console.log('PERCENTAGE = ' + this.percent)
      this.percentText = Math.round(this.percent) + '%'
      this.percentWeekText = Math.round(this.percentWeek) + '%'
      // this.percentWeek = Math.round(this.percentageBookedWeek * 100)
      console.log('PERCENTAGE WEEK TEXT = ' + this.percentWeekText)
      console.log('PERCENTAGE WEEK = ' + this.percentWeek)
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

.Chart {
  width: 400px;
  float: left;
  display: block;
  /* margin: 10px; */
}

.ChartBox {
  align-content: center;
}

</style>
