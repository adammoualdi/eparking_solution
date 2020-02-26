<template>
  <div class="landingWrapper">
    <MapSearch class="MapSearch" v-on:closeSearch="closeSearch" v-on:childToParent="onMapSearchClick" v-show="showSearch"> </MapSearch>
      <!-- v-on:closeSearch="closeSearch" v-show="showSearch" -->
      <div class="Nav">
        <NavigationBar class="nav"> </NavigationBar>
      </div>
      <div class="searchWrapper">
        <!-- <i class="fas fa-search-location fa-3x" id="searchIcon"></i> -->
        <b-button pill type="button" name="button" v-on:click="getLocSearch">
          <font-awesome-icon icon="search-location" size="3x"> </font-awesome-icon>
        </b-button>
      </div>
      <div v-if="isMounted">
        <div class="LandingLocationsView" v-if="$mq !== 'mobile'" >
          <ul>
            <li>
              <div v-for="(location, index) in mapLocations" :key="index">
                <b-card :title="location.address1">
                  <b-media no-body>
                    <b-media-aside vertical-align="center">
                      <b-img blank blank-color="#ccc" width="128" height="128" alt="placeholder"></b-img>
                    </b-media-aside>

                    <b-media-body class="ml-3">
                      <b-media>
                        <h5 class="mt-0">{{ location.address2 + ' ' + location.postcode }}</h5>
                        Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in
                        faucibus.
                      </b-media>
                    </b-media-body>
                  </b-media>
                  <!-- <b-card-text> -->
                    <!-- {{ location.address2 + ' ' + location.postcode }} -->
                  <!-- </b-card-text> -->

                  <!-- <b-card-text>A second paragraph of text in the card.</b-card-text> -->

                  <!-- <a href="#" class="card-link">Card link</a> -->
                  <b-button type="submit" variant="primary" v-on:click="goToBooking(location)">Book</b-button>&nbsp;
                  <b-link href="#" class="card-link">Another link</b-link>
                </b-card>
              </div>
            </li>
          </ul>
        </div>
        <div class="LandingMapSearch">
          <Map key:mapLocations class="map" v-on:mapToLanding="onMarkerClick" v-bind:locations=mapLocations v-bind:address=userQuery> </Map>
        </div>
        <LocationView class="locationView" v-bind:location=locViewAddress v-bind:booking=locViewBooking v-if="$mq === 'mobile' && showLocView"> </LocationView>
      </div>
      <div v-else class="Loading">
        <SemipolarSpinner
          :animation-duration="2000"
          :size="200"
          color="#000000"
        />
      </div>
  </div>
</template>

<script>
import PostsService from '@/services/PostsService'
import Map from '@/components/PARKINGUSER/Map'
import MapSearch from '@/components/PARKINGUSER/MapSearch'
import LocationView from '@/components/PARKINGUSER/LocationView'
import NavigationBar from '@/components/PARKINGUSER/NavigationBar'
import gmapsInit from '@/utils/gmaps'
import swal from 'sweetalert'
import {SemipolarSpinner} from 'epic-spinners'
import { EventBus } from '@/services/EventBus.js'
export default {
  name: 'Landing',
  props: ['location', 'start', 'end'],
  data () {
    return {
      isMounted: false,
      locations: [],
      mapLocations: [],
      latitude: null,
      longitude: null,
      userQuery: {},
      geocoder: null,
      google: null,
      userLoc: null,
      showSearch: false,
      showLocView: false,
      locViewAddress: null,
      locViewBooking: null
    }
  },
  created () {
    console.log(this.isMounted)
    this.getLocations()
    console.log('GET ALL LOCATIONS------------------------------------')
  },
  async mounted () {
    console.log(this.$route.query.location)
    this.google = await gmapsInit()
    this.geocoder = new this.google.maps.Geocoder()
    EventBus.$on('mapToLanding', function (payLoad) {
      console.log(payLoad)
    })
  },
  components: {
    Map,
    MapSearch,
    NavigationBar,
    LocationView,
    SemipolarSpinner
  },
  methods: {
    onMarkerClick (address, booking) {
      // Set LocationView props
      console.log(address)
      this.locViewAddress = address
      this.locViewBooking = booking
      this.showLocView = true
    },
    closeSearch () {
      this.showSearch = false
    },
    getLocSearch () {
      console.log('test')
      this.showSearch = true
      // Reset current locations when search clicked
      // this.mapLocations = []
    },
    async getLocations () {
      console.log('GET LOCATIONS-----------------------------------')
      const response = await PostsService.getLocations()
      this.locations = response.data.locations
      console.log(this.locations)
      // this.mapLocations = this.locations
      console.log('MAP LOCATIONS CHECK ID -------------------------')
      console.log(this.mapLocations)
      this.isMounted = true
      // If a search query has been placed then find location
      if (this.$route.query.location !== undefined &&
        this.$route.query.start !== undefined &&
        this.$route.query.end !== undefined) {
        console.log('TEST12345')
        var loc = this.$route.query.location
        var aTime = this.$route.query.start
        var lTime = this.$route.query.end
        this.userQuery = {loc, aTime, lTime}
        this.getLocationInfo()
      }
    },
    onMapSearchClick (loc, aTime, lTime) {
      console.log('MAP SEARCH CLICKED----------------------------------')
      this.showSearch = false
      this.userQuery = {loc, aTime, lTime}
      var url = '/search?location=' + loc + '&start=' + aTime + '&end=' + lTime
      history.pushState(null, null, url)
      console.log('IN LANDING ' + this.userQuery)
      this.getLocationInfo()
      // GET AVAILABLE LOCATIONS FROM API
    },
    getLocationInfo () {
      console.log(this.userQuery)
      console.log(this.locations)
      // DO THIS IN MAPSEARCH COMPONENT???
      this.geocoder.geocode({ address: this.userQuery.loc }, (results, status) => {
        if (status !== 'OK' || !results[0]) {
          swal('Oops!', `We couldn't find any parking slots in your area!`, 'warning')
          // NEED ERROR VALIDATION HERE FOR INCORRECT LOCATION QUERY
        }
        console.log(results[0].geometry.location.lat())
        this.userLoc = new this.google.maps.LatLng(results[0].geometry.location.lat(), results[0].geometry.location.lng())
        console.log('filter loc')
        this.showLocView = true
        this.filterLocations()
      })
    },
    async filterLocations () {
      var locations = []
      for (var i = 0; i < this.locations.length; i++) {
        var loc = new this.google.maps.LatLng(this.locations[i].latitude, this.locations[i].longitude)
        var d = this.google.maps.geometry.spherical.computeDistanceBetween(loc, this.userLoc)
        if (d < 5000) {
          var tmp = this.locations[i]
          var tmpObject = {locationId: tmp.id, country: tmp.country, city: tmp.city, address1: tmp.address1, address2: tmp.address2, postcode: tmp.postcode, distance: d, latitude: tmp.latitude, longitude: tmp.longitude, arriveTime: this.userQuery.aTime, leavingTime: this.userQuery.lTime}
          locations[i] = tmpObject
        }
      }
      // Remove any empty objects
      locations = locations.filter(value => Object.keys(value).length !== 0)
      console.log(locations)
      var object = {arriveTime: this.userQuery.aTime, leavingTime: this.userQuery.lTime, locations}
      console.log(object)
      const response = await PostsService.getAvailableLocations(object)
      console.log('AVAILABLE LOCATIONS -----------------------------------------')
      console.log(this.mapLocations)
      // DON'T WANT TO CHANGE THIS.LOCATIONS ARRAY CAUSES INDEX LENGTH ERROR
      // this.locations = locations
      this.mapLocations = response.data.locations
    },
    goToBooking (location) {
      this.$router.push({ name: 'Booking', params: {location: location} })
    }
  }
}
</script>

<style>

@media (max-width: 450px) {
  .map {
    width: 100vw;
    height: 100vh;
    /* float: left; */
    display: block;
  }

  .LandingMapSearch {
    /* margin-left: 0px; */
    /* position: static; */
    /* width: calc(100% - 50px); */
    /* margin-left: 50px; */
    box-sizing: border-box;
    /* height: 100px; */
    /* width: 700px; */
    position: relative;
    /* float: right; */
    /*
    z-index: 999999;
    height: 100vh;
    width: 100vw;
    background: red; */
  }
}

@media (min-width: 451px) {
  .map {
      /* width: calc(100% - 50px); */
      /* width: calc(100% -350px);
      */
      /* width: calc(400p; */
      /* width: 700px; */
      width: calc(100vw - 380px);
      height: 100vh;
      float: right;
      /* margin-left: 50px; */
      display: block;
      /* box-sizing: border-box; */
      /* position: relative; */
      /* float: left;  */
      /* margin-top: 50px; */
      /* padding-top   */
      /* padding-left: 500px; */
      z-index: 10;
  }

  .LandingMapSearch {
    /* margin-left: 0px; */
    /* position: static; */
    /* width: calc(100% - 50px); */
    /* margin-left: 50px; */
    box-sizing: border-box;
    /* height: 100px; */
    width: 700px;
    position: relative;
    float: right;
    /*
    z-index: 999999;
    height: 100vh;
    width: 100vw;
    background: red; */
  }
}
.Nav {
    display: block;
    /* float: left; */
    z-index: 20;
    /* position: absolute; */
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
  width: calc(100% - 50px);
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
  /* width: 100%; */
  margin-left: calc(100vw - 90px);
  /* float: right; */
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
  /* z-index: 2; */
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

.Loading {
  z-index: 9999999;
  position: absolute;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}

.LandingLocationsView {
  float: left;
  display: block;
  padding-top: 60px;
  width: 350px;
}

.LandingLocationsView ul {
  height: calc(100vh - 70px);
  width:350px;
}

.LandingLocationsView ul {
  overflow:hidden;
  overflow-y:scroll;
}

</style>
