<template>
<div id = "UserBookingsWrapper">
    <div class="Nav">
        <NavigationBar class="nav"> </NavigationBar>
    </div>
    <div class="UserBookingsInfoWrapper">
        <!-- <b-tabs card> -->
            <!-- <b-tab title="Old" active> -->
        <div v-if="isMounted">
            <div class="table-wrap">
                <h1>My bookings</h1>
                <b-tabs>
                    <b-tab title="Upcoming" active>
                        <table>
                            <tr v-for="(booking, index) in activeBookings" :key="index" >
                                <td class="BookingCardWrapper" width="650">
                                    <div id="booking_header">
                                        <h2>{{ booking.locationId.address1 + ' ' + booking.locationId.address2 }}</h2>
                                    </div>
                                    <div class="DateTimeInfo">
                                      <div class="ArriveDateTime">
                                        <h3>
                                          {{ dateFunc(booking.startDate) }} <br>
                                          {{ timeFunc(booking.startDate) }}
                                        </h3>
                                      </div>
                                      <div class="Arrow">
                                        <h1>&rArr;</h1>
                                      </div>
                                      <div class="LeaveDateTime">
                                        <h3>
                                          {{ dateFunc(booking.endDate) }} <br>
                                          {{ timeFunc(booking.endDate) }}
                                        </h3>
                                      </div>
                                    </div>
                                    {{ booking.locationId.country }} <br>
                                    {{ booking.locationId.city }} <br>
                                    {{ booking.locationId.postcode }} <br>
                                    Parking slot: {{ booking.parkingSlot }} <br>
                                    <button type="button" name="button" v-b-modal.issue v-on:click="sendInfo(booking)">Report parking issue</button>
                                    <a :href="getMapsLink(booking.locationId.latitude, booking.locationId.longitude)">Get maps</a>
                                </td>
                            </tr>
                        </table>
                    </b-tab>
                    <b-tab title="Previous">
                        <table>
                            <tr v-for="(booking, index) in previousBookings" :key="index" >
                                <td class="BookingCardWrapper" width="650">
                                  <div id="booking_header">
                                      <h2>{{ booking.locationId.address1 + ' ' + booking.locationId.address2 }}</h2>
                                  </div>
                                  <div class="DateTimeInfo">
                                    <div class="ArriveDateTime">
                                      <h3>
                                        {{ dateFunc(booking.startDate) }} <br>
                                        {{ timeFunc(booking.startDate) }}
                                      </h3>
                                    </div>
                                    <div class="Arrow">
                                      <h1>&rArr;</h1>
                                    </div>
                                    <div class="LeaveDateTime">
                                      <h3>
                                        {{ dateFunc(booking.endDate) }} <br>
                                        {{ timeFunc(booking.endDate) }}
                                      </h3>
                                    </div>
                                  </div>
                                  {{ booking.locationId.country }} <br>
                                  {{ booking.locationId.city }} <br>
                                  {{ booking.locationId.postcode }} <br>
                              </td>
                            </tr>
                        </table>
                    </b-tab>
                </b-tabs>
            </div>
        </div>
        <div v-else class="Loading">
            <SemipolarSpinner
            :animation-duration="2000"
            :size="200"
            color="#000000"
            />
        </div>
        <b-modal
          id="issue"
          ref="issue"
          @ok="handleOk"
          title="Report issue">
          <p>Please enter the registration number that's currently parked in your booked spot.</p>
          <form ref="form" @submit.stop.prevent="handleSubmit">
            <b-input-group>
              <b-form-input
                class="locationFormInput"
                placeholder="Registration number"
                v-model="regnumber">
              </b-form-input>
            <!-- <b-button variant="primary" v-on:click="purchase">Purchase</b-button> -->
            </b-input-group>
          </form>
        </b-modal>
    </div>
</div>
</template>

<script>
import NavigationBar from '@/components/PARKINGUSER/NavigationBar'
import PostsService from '@/services/PostsService'
import {SemipolarSpinner} from 'epic-spinners'
import swal from 'sweetalert'
import DateConverter from '@/services/DateConvert'
export default {
  name: 'UserBookings',
  components: {
    NavigationBar,
    SemipolarSpinner
  },
  data () {
    return {
      bookings: [],
      activeBookings: [],
      previousBookings: [],
      isMounted: false,
      currentLoc: null,
      regnumber: null,
      bookingDTO: null
    }
  },
  mounted () {
    console.log('Get bookings')
    this.getBookings()
    navigator.geolocation.getCurrentPosition(pos => {
      // var gettingLocation = false
      this.currentLoc = pos
      console.log(this.currentLoc)
    }, err => {
      // var gettingLocation = false
      var errorStr = err.message
      console.log(errorStr)
    })
  },
  methods: {
    async getBookings () {
      var username = window.localStorage.getItem('username')
      const response = await PostsService.getUserBookings(username)
      console.log(response.data)
      this.bookings = response.data.bookings
      this.isMounted = true
      console.log('bookings: ' + this.bookings)
      console.log('Sort bookings')
      this.sortBookings()
    },
    sortBookings () {
      for (var i = 0; i < this.bookings.length; i++) {
        if (this.bookings[i].completed === false) {
          this.activeBookings.push(this.bookings[i])
          console.log(this.activeBookings)
        } else {
          this.previousBookings.push(this.bookings[i])
        }
      }
    },
    // arriveTimeFunc (booking) {
    //   console.log(booking)
    //   var date = booking.startDate.substring(0, 10)
    //   var time = booking.startDate.substring(11, 16)
    //   console.log(date + ' ' + time)
    //   var ret = date + ' ' + time
    //   return ret
    // },
    // leaveTimeFunc (booking) {
    //   var date = booking.endDate.substring(0, 10)
    //   var time = booking.endDate.substring(11, 16)
    //   console.log(date + ' ' + time)
    //   var ret = date + ' ' + time
    //   return ret
    // },
    getMapsLink (lat, lng) {
      // https://www.google.com/maps/dir/?api=1&origin=Space+Needle+Seattle+WA&destination=Pike+Place+Market+Seattle+WA&travelmode=bicycling
      if (this.currentLoc !== null) {
        const googleLink = 'https://www.google.com/maps/dir/?api=1&origin='
        return googleLink + this.currentLoc.coords.latitude + ',' + this.currentLoc.coords.longitude + '&destination=' + lat + ',' + lng + '&travelmode=driving'
      }
    },
    async handleOk () {
      console.log(this.bookingDTO.locationId.locationId)
      var bookingDTO = { id: this.bookingDTO.id, locationId: this.bookingDTO.locationId.locationId, regno: this.regnumber }
      const response = await PostsService.reportIssue(bookingDTO)
      console.log(response)
      if (response.data.errorContent !== undefined) {
        swal('Oops!', response.data.errorContent, 'warning')
      } else {
        swal('Success!', 'Your parking id has been changed to!', 'success')
        // vm.$forceUpdate()
      }
    },
    sendInfo (info) {
      // var bookingDTO = { BookingDTO = info, regno = info.car.regNo }
      this.bookingDTO = info
      console.log(info)
    },
    dateFunc (dateTime) {
      console.log(dateTime)
      return DateConverter.dateConverter(dateTime)
    },
    timeFunc (dateTime) {
      return DateConverter.timeConverter(dateTime)
    }
  }
}
</script>

<style>
/* .profileWrapper {
} */

.UserBookingsInfoWrapper {
    /* display: block; */
    height: 100%;
    /* position: absolute; */
    /* width: calc(100% - 50px) */
    width: 100%;
    float: left;
    /* margin-left: 50px; */
    padding-top: 60px;
    /* text-align: center; */
    display: inline-block;
}

.UserBookingsWrapper {
    margin: 0px;
    padding-top: 10px;
}

#booking_header {
    height:100px;
    width:100%;
    /* background-color: red; */
    border-radius: 10px 10px 0 0;
    padding-top:10px;

    /* margin-top:10px; */
}

.BookingCardWrapper {
    /* background-color: #D6D6D6; */
    color: black;
    /* border: 2px solid red; */
    border-radius: 5px;
    /* padding-top: 10px; */
    padding-left: 10px;
    margin-top:200px;
    /* border-color: black; */
}

h4 {
    margin: 0px;
}

table {
    border-radius: 50px;
}

.table-wrap {
    /* width: 50%; */
    /* width: 100%; */
    padding: 5px;
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
    background: black;
}
table tr td {
    padding-top: 0px;
}

h1 {
    text-align: left;
}

/* td {
    background: rgb(255, 255, 255);
} */
table tr:nth-child(odd) {
    background: #f2f2f2;
}
table tr:nth-child(1) {
    background: grey;
    color: #fff;
}

#__BVID__19 {
    padding: 0px;
}

.Loading {
  z-index: 9999999;
  position: absolute;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}

#issue {
  margin-top: 100px;
}

.ArriveDateTime {
  float: left;
  width: 45%;
  display: flex;
}

.LeaveDateTime {
  /* width: 45%; */
}

.Arrow {
  width: 40px;
  float: left;
}
</style>
