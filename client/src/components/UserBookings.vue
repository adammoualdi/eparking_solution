<template>
<div id = "UserBookingsWrapper">
    <div class="Nav">
        <NavBar class="nav"> </NavBar>
    </div>
    <div class="UserBookingsInfoWrapper">
        <!-- <b-tabs card> -->
            <!-- <b-tab title="Old" active> -->
            <div class="table-wrap">
                <table>
                    <tr v-for="(booking, index) in bookings" :key="index" >
                        <td class="BookingCardWrapper" width="650">
                            <div id="booking_header">
                                <h2>{{ booking.locationId.address1 + ' ' + booking.locationId.address2 }} <b-badge v-if="booking.active">Active</b-badge></h2>
                            </div>
                            From: Friday 15th November <br>
                            <h4>
                            16.30
                            <i class="fas fa-arrow-right"></i>
                            18.30
                            </h4> <br>
                            {{ booking.locationId.country }} <br>
                            {{ booking.locationId.city }} <br>
                            {{ booking.locationId.postcode }} <br>

                        </td>
                        <!-- <td>test123</td> -->
                        <!-- <td align="center"> -->
                        <!-- </td> -->
                    </tr>
                </table>
            </div>
            <!-- </b-tab> -->
        <!-- </b-tabs> -->
    </div>
</div>
</template>

<script>
import NavBar from '@/components/NavBar'
import PostsService from '@/services/PostsService'
export default {
  name: 'UserBookings',
  components: {
    NavBar
  },
  data () {
    return {
      bookings: []
    }
  },
  mounted () {
    this.getBookings()
  },
  methods: {
    async getBookings () {
      var username = window.localStorage.getItem('username')
      const response = await PostsService.getUserBookings(username)
      console.log(response.data)
      this.bookings = response.data.bookings
      console.log('bookings: ' + this.bookings)
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
    width: calc(100% - 50px);
    margin-left: 50px;
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
    background: #f2f2f2;
}
table tr td {
    padding-top: 0px;
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
</style>
