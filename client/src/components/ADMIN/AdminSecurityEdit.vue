<template>
<div id = "UserBookingsWrapper">
    <div class="Nav">
        <NavigationBarAdmin class="nav"> </NavigationBarAdmin>
    </div>
    <div class="UserBookingsInfoWrapper">
        <!-- <b-tabs card> -->
            <!-- <b-tab title="Old" active> -->
        <div class="Search">
            <b-input-group>
                <b-form-input
                    class="UsernameFormInput"
                    placeholder="Username"
                    v-model="username">
                </b-form-input>
                <b-button variant="success" @click="findUser()">
                    Search
                </b-button>
                <!-- <b-button variant="primary" v-on:click="purchase">Purchase</b-button> -->
          </b-input-group>
        </div>
        <div class="UserInfo" v-if="user !== null">
            <b-card title="User">
                <b-card-text>
                    {{ user.firstname + " " + user.lastname}}
                    {{ user.email }}
                </b-card-text>
                <b-button variant="primary" @click="filterLocs()" v-b-modal.securityLoc >Add location</b-button>
            </b-card>
            <!-- <b-card class="overviewTitle" v-if="locations.length > 0"> -->
            <!-- <div v-if="locations.length > 0">
                {{ locations[0].firstname + locations[0].lastname }}
            </div> -->
            <!-- </b-card> -->
        </div>
        <div v-if="isMounted">
            <!-- <b-tabs content-class="mt-3"> -->
                <!-- <b-tab title="Locations" active> -->
                    <div class="table-wrap" v-if="locations.length > 0">
                        <table>
                            <tr>
                            <td width="100%">Location</td>
                            <td width="100%" align="center">Action</td>
                            </tr>
                            <tr v-for="(location, index) in locations" :key="index" class="border_bottom" >
                            <td>{{ location.country + ', ' + location.city + ', ' + location.address1 + ', ' + location.address2 }} <br>
                                {{ location.postcode }} <br>
                            </td>
                            <td align="center">
                                <!-- <router-link v-bind:to="{ name: 'EditPost', params: { id: post._id } }">Edit</router-link> | -->
                                <a href="javascript:void(0)" @click="removeLocation(location)">Remove</a>
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
        <b-modal
          id="securityLoc"
          ref="securityLoc"
          title="Add a location"
          @ok="handleOk"
          modal-footer=hide>
            <p>Select a location to add.</p>
            <form ref="form" @submit.stop.prevent="handleSubmit">
                <b-input-group>
                    <b-form-select v-model="selectedLoc" :options="optionsLoc"></b-form-select>
                </b-input-group>
            </form>
        </b-modal>
    </div>
</div>
</template>

<script>
import NavigationBarAdmin from '@/components/ADMIN/NavigationBarAdmin'
import PostsService from '@/services/PostsService'
import {SemipolarSpinner} from 'epic-spinners'
import swal from 'sweetalert'
export default {
  name: 'AdminSecurityEdit',
  components: {
    NavigationBarAdmin,
    SemipolarSpinner
  },
  data () {
    return {
      locations: [],
      isMounted: false,
      username: '',
      selectedLoc: null,
      optionsLoc: [],
      user: null
    }
  },
  mounted () {
    this.getLocations()
  },
  methods: {
    async getLocations () {
      const response = await PostsService.getLocations()
      for (var i = 0; i < response.data.locations.length; i++) {
        console.log(response.data)
        var text = response.data.locations[i].address1 + ', ' + response.data.locations[i].address2 + ', ' + response.data.locations[i].city + ', ' + response.data.locations[i].postcode
        var value = {locationId: response.data.locations[i].id, address1: response.data.locations[i].address1, address2: response.data.locations[i].address2, city: response.data.locations[i].city, country: response.data.locations[i].country, postcode: response.data.locations[i].postcode}
        // #1
        var obj = {value: value, text: text}
        this.optionsLoc.push(obj)
      }
      this.isMounted = true
    },
    async removeLocation (location) {
      console.log(location)
      console.log(this.locations)
      var obj = { username: this.username }
      const response = await PostsService.removeSecurityLocation(location.locationId, obj)
      console.log(response)
      console.log(this.locations)
      console.log(location.locationId)
      for (var i = 0; i < this.locations.length; i++) {
        if (this.locations[i].locationId === location.locationId) {
          delete this.locations[i]
          break
        }
      }
    },
    async findUser () {
      console.log('TEST')
      var obj = { username: this.username }
      console.log(obj)
      //   console.log(this.username)
      const response = await PostsService.getSecurityInfo(obj)
      console.log(response)
      var tmp = []
      if (response.data.errorContent === undefined) {
        for (var i = 0; i < response.data.bookings.length; i++) {
          tmp.push(response.data.bookings[i].locationId)
        }
        this.locations = tmp
        if (response.data.bookings !== undefined) {
          this.user = response.data.bookings[0]
        }
      } else {
        swal('Oops!', response.data.errorContent, 'warning')
      }
    },
    async handleOk () {
      console.log(this.selectedLoc)
      var obj = { username: this.username }
      const response = await PostsService.addSecurityLocation(this.selectedLoc.locationId, obj)
      console.log(response)
      this.locations.push(this.selectedLoc)
      console.log(this.locations)
    },
    filterLocs () {
      var tmp = []
      console.log(this.locations)
      console.log(this.optionsLoc)
      for (var i = 0; i < this.locations.length; i++) {
        for (var ii = 0; ii < this.optionsLoc.length; ii++) {
          if (this.locations[i].locationId !== this.optionsLoc[ii].value.locationId) {
            tmp.push(this.optionsLoc[ii])
          }
        }
      }
      this.optionsLoc = tmp
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
    border-color: black;
    padding-top:10px;

    /* margin-top:10px; */
}

.Search {
    padding: 10px;
}

.UserInfo {
    padding: 10px;
}

#securityLoc {
    margin-top: 100px;
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
