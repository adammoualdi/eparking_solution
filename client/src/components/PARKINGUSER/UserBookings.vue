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
                                    <h4>Arrival: </h4> <br>
                                    {{ arriveTimeFunc(booking) }} <br>
                                    <!-- <i class="fas fa-arrow-right"></i> -->
                                    <h4> Leaving: </h4> <br>
                                    {{ leaveTimeFunc(booking) }} <br>
                                    {{ booking.locationId.country }} <br>
                                    {{ booking.locationId.city }} <br>
                                    {{ booking.locationId.postcode }} <br>

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
                                    {{ arriveTimeFunc(booking) }}
                                    <i class="fas fa-arrow-right"></i>
                                    {{ leaveTimeFunc(booking) }} <br>
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
            <!-- </b-tab> -->
        <!-- </b-tabs> -->
    </div>
</div>
</template>

<script>
import NavigationBar from '@/components/PARKINGUSER/NavigationBar'
import PostsService from '@/services/PostsService'
import {SemipolarSpinner} from 'epic-spinners'
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
      isMounted: false
    }
  },
  mounted () {
    console.log('Get bookings')
    this.getBookings()
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
        if (this.bookings[i].active === true) {
          this.activeBookings.push(this.bookings[i])
          console.log(this.activeBookings)
        } else {
          this.previousBookings.push(this.bookings[i])
        }
      }
    },
    arriveTimeFunc (booking) {
      console.log(booking)
      var date = booking.startDate.substring(0, 10)
      var time = booking.startDate.substring(11, 16)
      console.log(date + ' ' + time)
      var ret = date + ' ' + time
      return ret
    },
    leaveTimeFunc (booking) {
      var date = booking.endDate.substring(0, 10)
      var time = booking.endDate.substring(11, 16)
      console.log(date + ' ' + time)
      var ret = date + ' ' + time
      return ret
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
    /* margin-left: 50px; */
    padding-top: 60px;
    text-align: center;
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
    background: #4d7ef7;
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
</style>
