<template>
  <div class="landingWrapper">
      <div class="Nav">
        <NavBar class="nav"> </NavBar>
      </div>
      <div v-if="isMounted" class="LandingMapSearch">
        <Map key:mapLocations class="map" v-bind:locations=mapLocations v-bind:address=userQuery> </Map>
        <MapSearch class="MapSearch" v-on:childToParent="onMapSearchClick"> </MapSearch>
      </div>
  </div>
</template>

<script>
import PostsService from '@/services/PostsService'
import Map from '@/components/Map'
import MapSearch from '@/components/MapSearch'
import NavBar from '@/components/NavBar'
import gmapsInit from '@/utils/gmaps'
export default {
  name: 'Landing',
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
      leavingTime: null
    }
  },
  created () {
    // this.locTime = '0'
    console.log(this.isMounted)
    this.getLocations()
    // this.getInfo(this.locations)
  },
  async mounted () {
    // this.isMounted = true
    this.google = await gmapsInit()
    this.geocoder = new this.google.maps.Geocoder()
  },
  components: {
    Map,
    MapSearch,
    NavBar
  },
  methods: {
    async getLocations () {
      const response = await PostsService.getLocations()
      this.locations = response.data.locations
      console.log('Locations: ' + this.locations)
      console.log(this.locations)
      this.mapLocations = this.locations
      console.log('mapLocations')
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
      // this.isMounted = true
    },
    onMapSearchClick (loc, aTime, lTime) {
      // this.arrivingTime = value
      // this.locTime = aTime + '//' + lTime
      // this.userQuery = loc + '///' + aTime + '///' + lTime
      this.userQuery = {loc, aTime, lTime}
      this.arrivingTime = aTime
      this.leavingTime = lTime
      console.log('IN LANDING ' + this.userQuery)
      this.getLocationInfo()
      // GET AVAILABLE LOCATIONS FROM API
    },
    getLocationInfo () {
      console.log(this.userQuery)
      this.geocoder.geocode({ address: this.userQuery.loc }, (results, status) => {
        if (status !== 'OK' || !results[0]) {
          throw new Error(status)
        }
        console.log('THIS.TIME')
        console.log(results[0].geometry.location.lat())
        this.userLoc = new this.google.maps.LatLng(results[0].geometry.location.lat(), results[0].geometry.location.lng())
        console.log('filter loc')
        this.filterLocations()
      })
    },
    filterLocations () {
      // this.getLocations() // results[0].address_components
      // that.sortByCountry(country)
      // console.log(results[0].address_components[3].long_name)
      // console.log('BEFORE PASSING TO METHOD CITY: ' + results[0].address_components[3].long_name + ' CITY LOC: ' + that.locations)
      // JUST TO TEST SORT
      // var filteredLocations = that.locations
      // var filteredLocations = that.sortByCity(results[0].address_components[3].long_name, that.locations)
      // that.getClosestLocations()
      // var closest = -1
      // console.log('FILTERED: ' + filteredLocations.length)
      console.log('CHECK LOCATIONS')
      console.log(this.locations)
      var locations = []
      locations.splice(0, locations.length)
      console.log(locations)
      console.log(locations.length)
      console.log('CHECKING LOCATIONS ARRAY ')
      console.log(this.locations)
      console.log(this.locations.length)
      for (var i = 0; i < this.locations.length; i++) {
        var loc = new this.google.maps.LatLng(this.locations[i].latitude, this.locations[i].longitude)
        // console.log(event.latLng)
        console.log(loc)
        var d = this.google.maps.geometry.spherical.computeDistanceBetween(loc, this.userLoc)
        // console.log(filteredLocations[i])
        if (d < 5000) {
          var tmp = this.locations[i]
          var tmpObject = {locationId: tmp.id, country: tmp.country, city: tmp.city, address1: tmp.address1, address2: tmp.address2, postcode: tmp.postcode, distance: d, latitude: tmp.latitude, longitude: tmp.longitude, arriveTime: this.arrivingTime, leavingTime: this.leavingTime}
          console.log('ADDED ')
          console.log(tmpObject)
          locations[i] = tmpObject
        }
        console.log(d)
        // console.log(that.distances)
        // console.log(results[0].address_components)
        // alert(results[0].formatted_address)
      }
      // sort distances so we can get closest x
      // that.sortDistances()
      // var closestDistances = that.distances
      // console.log(closestDistances)
      // a[1] > b[1] second element in distances array.
      console.log(locations)
      locations.sort((a, b) => (a[1] > b[1]) ? 1 : -1)
      // filteredLocations.splice(0, 9)
      console.log('SORTED: ' + locations)
      console.log(locations)

      // DON'T WANT TO CHANGE THIS.LOCATIONS ARRAY CAUSES INDEX LENGTH ERROR
      // this.locations = locations
      this.mapLocations = locations
      // this.getAvailableLocations(locations)

    //   console.log(markers)
    //   for (i = 0; i < markers.length; i++) {
    //     // only looping through 10 values for closestDistances
    //     for (var ii = 0; ii < closestDistances.length; ii++) {
    //       console.log(markers[i].getPosition().lat() + '===' + closestDistances[ii][0] + ' ' + markers[i].getPosition().lng() + '===' + closestDistances[ii][1])
    //       if (markers[i].getPosition().lat() === closestDistances[ii][0] &&
    //           markers[i].getPosition().lng() === closestDistances[ii][1]) {
    //         console.log('SET TO TRUE')
    //         markers[i].setVisible(true)
    //       }
    //     }
    //   }
    }
    // async getAvailableLocations (locations) {
    //   // console.log(distances)
    //   // var test = JSON.parse(distances)
    //   // console.log(test)
    //   // var arr = {locations}
    //   // locations = {locations}
    //   var locationObject = JSON.stringify(locations)
    //   console.log(locationObject)
    //   // console.log(JSON.stringify(arr))
    //   const response = await PostsService.getAvailableLocations({
    //     locations: locations
    //   })
    //   console.log(response)
    // }
  }
}
</script>

<style>
.map {
    width: 100%;
    height: 60vh;
    float: left;
    display: block;
    box-sizing: border-box;
    margin-top: 60px;
}

.Nav {
    display: block;
    float: left;
}

.LandingMapSearch {
  /* margin-left: 0px; */
  width: calc(100% - 50px);
  margin-left: 50px;
  box-sizing: border-box;

}

.MapSearch {
  /* margin-left: 50px; */
  /* position: absolute; */
  /* float:left; */
  padding-left: 50px;
  width: calc(100% - 50px);
  padding-top: 60px;
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

</style>
