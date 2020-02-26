<template>
  <div class="landingWrapper">
    <AddLocation class="MapSearch" v-on:childToParent="onMapSearchClick" v-on:closeSearch="closeSearch" v-show="showSearch"> </AddLocation>
      <div class="Nav">
        <NavigationBarOwner class="nav"> </NavigationBarOwner>
      </div>
      <div class="searchWrapper">
        <!-- <i class="fas fa-search-location fa-3x" id="searchIcon"></i> -->
        <b-button pill type="button" name="button" v-on:click="getLocSearch">
          <font-awesome-icon icon="plus" size="3x"> </font-awesome-icon>
        </b-button>
      </div>
      <div v-if="isMounted">
        <div class="LandingMapSearch">
          <OwnerMap key:mapLocations class="map" v-on:mapToLanding="onMarkerClick" v-bind:locations=mapLocations v-bind:address=userQuery> </OwnerMap>
        </div>
        <OwnerLocationView class="locationView" v-bind:location=locViewAddress v-bind:booking=locViewBooking> </OwnerLocationView>
      </div>
  </div>
</template>

<script>
import PostsService from '@/services/PostsService'
import OwnerMap from '@/components/OWNER/OwnerMap'
import AddLocation from '@/components/OWNER/AddLocation'
import OwnerLocationView from '@/components/OWNER/OwnerLocationView'
import NavigationBarOwner from '@/components/OWNER/NavigationBarOwner'
import gmapsInit from '@/utils/gmaps'
import swal from 'sweetalert'
import { EventBus } from '@/services/EventBus.js'
export default {
  name: 'OwnerLanding',
  data () {
    return {
      isMounted: false,
      locations: [],
      mapLocations: [],
      latitude: null,
      longitude: null,
      userQuery: {},
      coords: [],
      time: null,
      geocoder: null,
      google: null,
      userLoc: null,
      arrivingTime: null,
      leavingTime: null,
      showSearch: false,
      locViewAddress: null,
      locViewBooking: null
    }
  },
  created () {
    console.log(this.isMounted)
    this.getLocations()
  },
  async mounted () {
    this.google = await gmapsInit()
    this.geocoder = new this.google.maps.Geocoder()
    const that = this
    EventBus.$on('mapToLanding', function (payLoad) {
      console.log(payLoad)
    })
    // Event listener for user clicking on map
    EventBus.$on('mapClick', function (payLoad, coords) {
      console.log('TESTTTTTTTTTTTTTTTTTTTTTT')
      that.setLocSearch(payLoad, coords)
      // this.$emit('landingToAddLocation', 'test')
      // this.showSearch = true
    })
  },
  components: {
    OwnerMap,
    AddLocation,
    NavigationBarOwner,
    OwnerLocationView
  },
  methods: {
    onMarkerClick (address, booking) {
      // Set LocationView props
      console.log(address)
      this.locViewAddress = address
      this.locViewBooking = booking
    },
    closeSearch () {
      this.showSearch = false
    },
    getLocSearch () {
      // console.log('test')
      // this.$emit('landingToAddLocation', 'test')
      this.showSearch = true
      // Reset current locations when search clicked
      // this.mapLocations = []
    },
    setLocSearch (payLoad, coords) {
      this.showSearch = true
      var address1 = payLoad[0].address_components[0].long_name
      var address2 = payLoad[0].address_components[1].long_name
      var city = payLoad[0].address_components[2].long_name
      var postcode = payLoad[0].address_components[5].long_name
      var loc = {address1, address2, city, postcode}
      console.log(loc)
      EventBus.$emit('landingToAddLocation', loc, coords)
    },
    async getLocations () {
      const response = await PostsService.getOwnerLocations()
      this.locations = response.data.locations
      console.log('Locations: ' + this.locations)
      console.log(this.locations)
      // this.mapLocations = this.locations
      this.mapLocations = this.locations
      console.log('MAPLOCATIONS')
      console.log(this.mapLocations)
      this.isMounted = true
    },
    getInfo () {
      for (var i = 0; i < this.locations.length; i++) {
        console.log(this.locations[i].longitude)
        this.latitude = this.locations[i].latitude
        this.longitude = this.locations[i].longitude
        this.coords.push(this.latitude + ' ' + this.longitude)
      }
    },
    onMapSearchClick (loc, aTime, lTime) {
      this.showSearch = false
      this.userQuery = {loc, aTime, lTime}
      this.arrivingTime = aTime
      this.leavingTime = lTime
      console.log('IN LANDING ' + this.userQuery)
      this.getLocationInfo()
      // GET AVAILABLE LOCATIONS FROM API
    },
    getLocationInfo () {
      console.log(this.userQuery)
      // DO THIS IN MAPSEARCH COMPONENT???
      this.geocoder.geocode({ address: this.userQuery.loc }, (results, status) => {
        if (status !== 'OK' || !results[0]) {
          swal('Oops!', `We couldn't find any parking slots in your area!`, 'warning')
          // NEED ERROR VALIDATION HERE FOR INCORRECT LOCATION QUERY
        }
        console.log('THIS.TIME')
        console.log(results[0].geometry.location.lat())
        this.userLoc = new this.google.maps.LatLng(results[0].geometry.location.lat(), results[0].geometry.location.lng())
        console.log('filter loc')
        this.filterLocations()
      })
    },
    filterLocations () {
      // console.log('CHECK LOCATIONS')
      // console.log(this.locations)
      var locations = []
      // locations.splice(0, locations.length)
      // console.log(locations)
      // console.log(locations.length)
      // console.log('CHECKING LOCATIONS ARRAY ')
      // console.log(this.locations)
      // console.log(this.locations.length)
      for (var i = 0; i < this.locations.length; i++) {
        var loc = new this.google.maps.LatLng(this.locations[i].latitude, this.locations[i].longitude)
        console.log(loc)
        var d = this.google.maps.geometry.spherical.computeDistanceBetween(loc, this.userLoc)
        if (d < 5000) {
          console.log('filter loc')
          var tmp = this.locations[i]
          var tmpObject = {locationId: tmp.id, country: tmp.country, city: tmp.city, address1: tmp.address1, address2: tmp.address2, postcode: tmp.postcode, distance: d, latitude: tmp.latitude, longitude: tmp.longitude, arriveTime: this.arrivingTime, leavingTime: this.leavingTime}
          console.log('ADDED ')
          console.log(tmpObject)
          locations[i] = tmpObject
        }
        console.log(d)
      }
      // a[1] > b[1] second element in distances array.
      console.log(locations)
      locations.sort((a, b) => (a[1] > b[1]) ? 1 : -1)
      // filteredLocations.splice(0, 9)
      console.log('SORTED: ' + locations)
      console.log(locations)

      // DON'T WANT TO CHANGE THIS.LOCATIONS ARRAY CAUSES INDEX LENGTH ERROR
      // this.locations = locations
      // this.mapLocations = locations
    }
  }
}
</script>

<style>
.map {
    /* width: calc(100% - 50px); */
    width: 100%;
    height: 60vh;
    /* float: right; */
    /* margin-left: 50px; */
    display: block;
    box-sizing: border-box;
    margin-top: 50px;
    z-index: 10;
}

.Nav {
    display: block;
    /* float: left; */
    z-index: 20;
    /* position: absolute; */
}

.LandingMapSearch {
  /* margin-left: 0px; */
  /* position: static; */
  /* width: calc(100% - 50px); */
  /* margin-left: 50px; */
  box-sizing: border-box;
  /* position: absolute;
  z-index: 999999;
  height: 100vh;
  width: 100vw;
  background: red; */

}

.MapSearch {
  /* margin-left: 50px; */
  position: absolute;
  z-index: 999999;
  height: 100vh;
  width: 100vw;
  background-color:rgba(128,128,128,0.5);
  /* float:left; */
  /* padding-left: 50px; */
  /* width: 30px;
    height: 30px;
    padding: 6px 0px;
    border-radius: 15px;
    text-align: center;
    font-size: 12px;
    line-height: 1.42857; */
  /* width: calc(100% - 50px); */
  /* padding-top: 30vh; */
  /* opacity: 0.5; */
}

.rounded-pill {
    border-radius: 50%!important;
}

.btn-secondary {
    color: black;
    background-color: white;
    border-color: white;
}

.vdatetime {
  /* width: calc(100% - 50px); */
  /* margin-left:70px; */
  display: block;
}

.vdatetime-input {
  /* width: 40px; */
  /* width: 40%; */
}

.vdatetime-popup {
  margin-left:25px;
}

.searchWrapper {
  position: absolute;
  /* margin-left: calc(100vw - 80px); */
  margin-top: 70px;
  z-index: 99;
  /* background-color:; */
  border-radius: 100px;
  /* width: 40px;
  height: 40px; */
  /* padding: 6px 0px; */
  /* border-radius: 15px; */
  /* text-align: center; */
  /* font-size: 12px; */
  /* line-height: 1.42857; */
}

.searchWrapper b-button {
  border-radius: 100px;
  background-color: white;
}

.locationView {
  display: block;
  /* padding-left: 50px; */
  /* width: calc(100% - 50px); */
  /* -top: 600px; */
  height: calc(40vh - 60px);
  float: left;
}

.card-body {
  height: calc(40vh - 60px);
}

</style>
