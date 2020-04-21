<template>
<div id = "SecurityOverviewWrapper">
    <div class="Nav">
        <NavigationBarSecurity class="nav"> </NavigationBarSecurity>
    </div>
    <div class="SecurityOverviewInfoWrapper">
        <!-- <b-tabs card> -->
            <!-- <b-tab title="Old" active> -->
        <div v-if="isMounted">
            <!-- <div class="overviewCitySearch">
                <b-form-select v-model="citySelected" :options="cityOptions" multiple :select-size="4" @change="onChange($event)"></b-form-select>
            </div> -->
            <div class="overviewSearch">
                <b-form-select v-model="locationSelected" :options="locationOptions" multiple :select-size="4" @change="onChange($event)"></b-form-select>
            </div>
            <div class ="overviewTitleDiv">
                <b-card class="overviewTitle">
                    <span v-for="(location, index) in locationSelected" :key="index">
                        {{ location.address1 }}
                    </span>
                </b-card>
            </div>
            <div class ="overviewDiv">
                <div class="wrapperCard">
                    <b-card-group decks>
                        <b-card class="overviewBox" id="box1">
                            Bookings Today
                            <h1> {{ showLocations.length }} </h1>
                        </b-card>
                        <b-card class="overviewBox" id="box2">
                            Currently Parked
                            <h1> {{ currentlyParked.length }} </h1>
                        </b-card>
                        <b-card class="overviewBox" id="box3">
                            Late
                            <h1> {{ lateBookings.length }} </h1>
                        </b-card>
                    </b-card-group>
                </div>
                <!-- <b-card class="overviewBox" id="box4">
                    Overview: Location name
                </b-card> -->
                <b-form-group>
                  <b-form-radio-group id="radio-group-2" v-model="selectedRadio" name="radio-sub-component" v-on:input="filterLocs">
                    <b-form-radio name="AllRadio" value="All">All</b-form-radio>
                    <b-form-radio name="BookingsTodayRadio" value="Bookings">Bookings Today</b-form-radio>
                    <b-form-radio name="ParkedRadio" value="Parked">Currently Parked</b-form-radio>
                    <b-form-radio name="LateRadio" value="Late">Issue</b-form-radio>
                  </b-form-radio-group>
                </b-form-group>
            </div>
                <div class="table-wrap" v-if="showLocations.length > 0">
                    <table>
                        <tr>
                            <th width="15%">Name</th>
                            <th width="15%">Email</th>
                            <th width="30%">Car</th>
                            <th width="20%">Arrival time</th>
                            <th width="25%">Leaving time</th>
                            <th width="15%">Report</th>
                            <!-- <td width="50%" align="center">Action</td> -->
                        </tr>
                        <tr v-for="(booking, index) in showLocations" :key="index" class="border_bottom" v-bind:style="[booking.issue ? { background: 'red'} : {}]">
                            <td> {{ booking.userDTO.firstname + ' ' + booking.userDTO.lastname }}</td>
                            <td> {{ booking.userDTO.email }} </td>
                            <td> {{ booking.car.regNo }} </td>
                            <td>{{ dateFunc(booking.startDate) + ', ' + timeFunc(booking.startDate) }} <br>
                                <!-- {{ booking.postcode }} <br> -->
                                <!-- {{ 'Spaces: ' + location.spaces }} <br> -->
                                <!-- {{ 'Parking owner: ' + bookin.userId.firstname + ' ' + booking.userId.firstname }} <br> -->
                                <!-- {{ 'Email: ' + location.userId.email }} -->
                            </td>
                            <td> {{ dateFunc(booking.endDate) + ', ' + timeFunc(booking.endDate) }} </td>
                            <td><a href="javascript:void(0)" @click="confirmIssue(booking)">Confirm issue</a> </td>
                        </tr>
                    </table>
                </div>
                <div v-else>
                    There are no requests
                </div>
        </div>
        <div v-else class="Loading">
            <SemipolarSpinner
                :animation-duration="2000"
                :size="200"
                color="#000000"
            />
        </div>
            <!-- </b-tab> -->
        <!-- </b-tabs> -->
    </div>
</div>
</template>

<script>
import NavigationBarSecurity from '@/components/SECURITY/NavigationBarSecurity'
import PostsService from '@/services/PostsService'
import {SemipolarSpinner} from 'epic-spinners'
import DateConverter from '@/services/DateConvert'
import swal from 'sweetalert'
export default {
  name: 'SecurityOverview',
  components: {
    NavigationBarSecurity,
    SemipolarSpinner
  },
  data () {
    return {
      locations: [],
      showLocations: [],
      isMounted: false,
      locationSelected: [],
      locationOptions: [
        { value: null, text: 'Please select an option' }
      ],
      bookingsToday: [],
      currentlyParked: [],
      lateBookings: [],
      interval: 0,
      selectedRadio: null
    }
  },
  mounted () {
    this.getBookings()
    this.getLocations()
    console.log('set interval')
    window.setInterval(() => {
      this.checkBookings()
    }, 60000)
  },
  beforeDestroy () {
    console.log('Destroyed')
    clearInterval()
  },
  methods: {
    async getBookings () {
      console.log('locations: ' + this.locations)
    },
    async getLocations () {
      const response = await PostsService.getSecurityBookings()
      this.locations = response.data.bookings
      console.log(this.locations)
      this.locationOptions = []
      for (var i = 0; i < this.locations.length; i++) {
        var address = {value: this.locations[i].locationId, text: this.locations[i].locationId.address1}
        // var city = {value: response.data.locations[i], text: response.data.locations[i].city}
        console.log(address)
        this.locationOptions.push(address)
      }

      //   this.locationOptions = this.locationOptions.filter((v,i,a)=>a.findIndex(t=>(t.locationId === v.locationId))===i)
      console.log(this.locationOptions)
      //   var temp = this.locationOptions.filter((a, b) => this.locationOptions.indexOf(a.value) === b.value)
      //   this.locationOptions = temp
      // var uniq = {}
      // var arrFiltered = this.locationOptions.filter(obj => uniq[obj.id] && (uniq[obj.id] = true))
      // console.log(arrFiltered)
      // this.locationOptions = arrFiltered
      console.log(this.locationOptions)
      this.checkBookings()
      this.isMounted = true
    },
    checkBookings () {
      console.log('TESTTTTTT')
      this.checkActive()
    },
    async checkActive () {
    //   this.showLocations = []
      var showLocationsTmp = []
      var currentlyParkedTmp = []
      var lateBookingsTmp = []
      //   this.currentlyParked = []
      this.lateBookings = []
      const response = await PostsService.getSecurityBookings()
      console.log(response.data)
      console.log('SELECTA')
      console.log(this.locationSelected)
      for (var i = 0; i < response.data.bookings.length; i++) {
        for (var ii = 0; ii < this.locationSelected.length; ii++) {
          // Only update bookings for specific selected locations
          if (response.data.bookings[i].locationId.locationId === this.locationSelected[ii].locationId) {
            showLocationsTmp.push(response.data.bookings[i])
            if (response.data.bookings[i].parkingConfirmation === true) {
              currentlyParkedTmp.push(response.data.bookings[i])
            }
            if (response.data.bookings[i].issue === true) {
              lateBookingsTmp.push(response.data.bookings[i])
            }
          }
        }
      }
      this.currentlyParked = currentlyParkedTmp
      this.lateBookings = lateBookingsTmp
      this.showLocations = showLocationsTmp
      console.log(response)
    },
    onChange: function (event) {
      console.log(this.locationSelected)
      console.log(this.locations)
      this.locationOptions = []
      // Get all booking information for selected locations
      console.log(this.locations.length)
      for (var i = 0; i < this.locations.length; i++) {
        var location = {value: this.locations[i].locationId, text: this.locations[i].locationId.address1}
        this.locationOptions.push(location)
        // console.log("selected locs")
        for (var ii = 0; ii < this.locationSelected.length; ii++) {
          if (this.locations[i].locationId.locationId === this.locationSelected[ii].locationId) {
            this.showLocations.push(this.locations[i])
          }
        }
      }
      var uniq = {}
      var arrFiltered = this.locationOptions.filter(obj => !uniq[obj.id] && (uniq[obj.id] = true))
      console.log(arrFiltered)
      this.locationOptions = arrFiltered
      this.checkBookings()
    },
    dateFunc (dateTime) {
      return DateConverter.dateConverter(dateTime)
    },
    timeFunc (dateTime) {
      return DateConverter.timeConverter(dateTime)
    },
    async confirmIssue (booking) {
      console.log(booking)
      const resp = await PostsService.confirmIssue(booking)
      console.log(resp)
      swal('Success!', 'Issue resolved', 'success')
    },
    filterLocs () {
      console.log(this.selectedRadio)
      console.log(this.lateBookings)
      if (this.selectedRadio === 'Bookings') {
        this.showLocations = this.bookingsToday
      } else if (this.selectedRadio === 'Parked') {
        this.showLocations = this.currentlyParked
      } else if (this.selectedRadio === 'Late') {
        this.showLocations = this.lateBookings
      } else if (this.selectedRadio === 'All') {
        this.showLocations = this.locations
      }
    }
  }
}
</script>

<style>
/* .profileWrapper {
} */

.SecurityOverviewInfoWrapper {
    /* display: block; */
    height: 100%;
    /* position: absolute; */
    /* width: calc(100% - 50px); */
    /* margin-left: 50px; */
    width: 100%;
    padding-top: 60px;
    /* text-align: center; */
    display: inline-block;
}

.SecurityOverviewWrapper {
    margin: 0px;
    padding-top: 10px;
}

#booking_header {
    height:100px;
    width:100%;
    /* background-color: red; */
    border-radius: 10px 10px 0 0;
    border-color: black;
    padding-top:10px;

    /* margin-top:10px; */
}

.BookingCardWrapper {
    background-color: #D6D6D6;
    /* border: 2px solid red; */
    border-radius: 5px;
    /* padding-top: 10px; */
    margin-top:200px;
    /* border-color: black; */
}

/* .profileContainer { */
    /* width: calc(100% - 50px); */
    /* margin-left: 50px; */
    /* box-sizing: border-bo    x; */
/* } */

table {
    border-radius: 50px;
}

.table-wrap {
    width: 100%;
    /* width: 100%; */
    padding: 10px;
    margin: 0 auto;
    text-align: center;
    display: inline-block;
    /* border-radius: 100px; */
}
table th, table tr {
    text-align: left;
    height: 50px;
    /* padding: 0px; */

}
table thead {
    background: #f2f2f2;
}
table tr td {
    padding-top: 0px;
}

tr.border_bottom td {
  border-bottom:1pt solid black;
  /* padding-top: 20px; */
}

/* td {
    background: rgb(255, 255, 255);
} */
table tr:nth-child(odd) {
    background: #f2f2f2;
}
table tr:nth-child(1) {
    background: #4d7ef7;
    color: #fff;
}

#__BVID__19 {
    padding: 0px;
}

.WarningIcon {
    color: red;
}

.OkIcon {
    color: green;
}

.Highlight {
     background-color: red;
}

.Loading {
    z-index: 9999999;
    position: absolute;
    top: 50%;
    left: 50%;
    -webkit-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
}

.overviewTitle {
    width: 100%;
    height: 60px;
}

.overviewTitleDiv {
    padding: 10px;
}

.overviewDiv {
    /* padding: 10px; */
    margin-left: 10px;
    margin-right: 10px;
    background-color: white;
    height: auto;
    display:inline-block;
    width: calc(100vw - 20px);
}

#box1 {
    background-color: green;
}

#box2 {
    background-color: orange;
    margin-left: 2vw;
}

#box3 {
    background-color: red;
    margin-left: calc(2vw);
}

.overviewBox {
    /* width: auto; */
    height: 200px;
    float: left;
    /* padding: 10px; */
    /* background-color: green; */
}

.wrapperCard {
    background-color: white;
    padding: 10px;
    height: 400px;
    width: 100%;
}

.overviewSearch {
    margin-top: 10px;
    margin-left: 10px;
    margin-right: 10px;
    height: auto;
    width: calc(100vw - 20px);
    /* display: inline-block; */
    background-color: white;
}

.overviewCitySearch {
    margin-top: 10px;
    margin-left: 10px;
    margin-right: 10px;
    height: auto;
    width: calc(100vw - 20px);
    /* display: inline-block; */
    background-color: white;
}

</style>
