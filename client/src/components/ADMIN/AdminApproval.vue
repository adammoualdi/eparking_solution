<template>
<div id = "UserBookingsWrapper">
    <div class="Nav">
        <NavigationBarAdmin class="nav"> </NavigationBarAdmin>
    </div>
    <div class="UserBookingsInfoWrapper">
        <!-- <b-tabs card> -->
            <!-- <b-tab title="Old" active> -->
        <div v-if="isMounted">
            <!-- <b-tabs content-class="mt-3"> -->
                <!-- <b-tab title="Locations" active> -->
                    <div class="table-wrap" v-if="locations.length > 0">
                        <table>
                            <tr>
                            <td width="90%">Location</td>
                            <td width="80%">Automatic checks</td>
                            <td width="50%" align="center">Action</td>
                            </tr>
                            <tr v-for="(location, index) in locations" :key="index" class="border_bottom" >
                            <td>{{ location.country + ', ' + location.city + ', ' + location.address1 + ', ' + location.address2 }} <br>
                                {{ location.postcode }} <br>
                                {{ 'Spaces: ' + location.spaces }} <br>
                                {{ 'Parking owner: ' + location.userId.firstname + ' ' + location.userId.firstname }} <br>
                                {{ 'Email: ' + location.userId.email }}
                            </td>
                            <td>
                                <div v-if="location.warning">
                                    <font-awesome-icon class="WarningIcon" icon="exclamation-circle" size="2x"> </font-awesome-icon>
                                </div>
                                <div v-else>
                                    <font-awesome-icon class="OkIcon" icon="check-circle" size="2x"> </font-awesome-icon>
                                </div>
                            </td>
                            <td align="center">
                                <!-- <router-link v-bind:to="{ name: 'EditPost', params: { id: post._id } }">Edit</router-link> | -->
                                <a href="javascript:void(0)" @click="approveLocation(location, true)">Approve</a>
                                <a href="javascript:void(0)" @click="approveLocation(location, false)">Disapprove</a>
                            </td>
                            </tr>
                        </table>
                    </div>
                    <div v-else>
                        There are no requests
                    </div>
                <!-- </b-tab> -->
                <!-- <b-tab title="Users" active> USERS </b-tab> -->
            <!-- </b-tabs> -->
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
import NavigationBarAdmin from '@/components/ADMIN/NavigationBarAdmin'
import PostsService from '@/services/PostsService'
import {SemipolarSpinner} from 'epic-spinners'
export default {
  name: 'AdminApproval',
  components: {
    NavigationBarAdmin,
    SemipolarSpinner
  },
  data () {
    return {
      locations: [],
      isMounted: false
    }
  },
  mounted () {
    this.getBookings()
  },
  methods: {
    async getBookings () {
      const response = await PostsService.getAdminApprovals()
      console.log(response.data)
      this.locations = response.data.locations
      this.isMounted = true
      console.log('locations: ' + this.locations)
    },
    async approveLocation (location, approved) {
      location.approved = approved
      const response = await PostsService.setApproval(location)
      console.log(response.data)
      console.log('Approve')
      // remove location from list
      this.locations = this.locations.filter(function (returnableObjects) {
        return returnableObjects.id !== location.id
      })
      console.log(this.locations)
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
    /* width: calc(100% - 50px); */
    /* margin-left: 50px; */
    width: 100%;
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

table {
    border-radius: 50px;
}

.table-wrap {
    width: 100%;
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

.Loading {
  z-index: 9999999;
  position: absolute;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}
</style>
