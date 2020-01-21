<template>
  <div class="map_wrapper">
    <div class="map"></div>
    <!-- <MapSearch class="MapSearch"> </MapSearch> -->
  </div>
</template>

<script>
// import MapSearch from '@/components/MapSearch'
import gmapsInit from '@/utils/gmaps'
import swal from 'sweetalert'
// import { EventBus } from '@/services/EventBus.js'

export default {
  name: 'Map',
  data () {
    return {
      outputLocations: [],
      location: null,
      distances: [],
      sortedLocations: [],
      geocoder: null,
      locInfoGlobal: null,
      firstInit: false
    }
  },
  props: {
    locations: {
      type: Array,
      required: true
      // default: () => []
    },
    address: null
    // times: 1
  },
  watch: {
    address: function () {
      this.getClosestLocations()
      // bool value used to set validation events off after first round of init.
      this.firstInit = true
    },
    locations: function () {
      this.init()
    }
  },
  // components: {
  //   MapSearch
  // },
  methods: {
    async init () {
      try {
        console.log('INIT')
        // console.log(this.$route.query)
        const google = await gmapsInit()
        this.geocoder = new google.maps.Geocoder()
        var mapProp = {
          center: {lat: 40.674, lng: -73.945},
          zoom: 12,
          styles: [
            {elementType: 'geometry', stylers: [{color: '#242f3e'}]},
            {elementType: 'labels.text.stroke', stylers: [{color: '#242f3e'}]},
            {elementType: 'labels.text.fill', stylers: [{color: '#746855'}]},
            {
              featureType: 'administrative.locality',
              elementType: 'labels.text.fill',
              stylers: [{color: '#d59563'}]
            },
            {
              featureType: 'poi',
              elementType: 'labels.text.fill',
              stylers: [{color: '#d59563'}]
            },
            {
              featureType: 'poi.park',
              elementType: 'geometry',
              stylers: [{color: '#263c3f'}]
            },
            {
              featureType: 'poi.park',
              elementType: 'labels.text.fill',
              stylers: [{color: '#6b9a76'}]
            },
            {
              featureType: 'road',
              elementType: 'geometry',
              stylers: [{color: '#38414e'}]
            },
            {
              featureType: 'road',
              elementType: 'geometry.stroke',
              stylers: [{color: '#212a37'}]
            },
            {
              featureType: 'road',
              elementType: 'labels.text.fill',
              stylers: [{color: '#9ca5b3'}]
            },
            {
              featureType: 'road.highway',
              elementType: 'geometry',
              stylers: [{color: '#746855'}]
            },
            {
              featureType: 'road.highway',
              elementType: 'geometry.stroke',
              stylers: [{color: '#1f2835'}]
            },
            {
              featureType: 'road.highway',
              elementType: 'labels.text.fill',
              stylers: [{color: '#f3d19c'}]
            },
            {
              featureType: 'transit',
              elementType: 'geometry',
              stylers: [{color: '#2f3948'}]
            },
            {
              featureType: 'transit.station',
              elementType: 'labels.text.fill',
              stylers: [{color: '#d59563'}]
            },
            {
              featureType: 'water',
              elementType: 'geometry',
              stylers: [{color: '#17263c'}]
            },
            {
              featureType: 'water',
              elementType: 'labels.text.fill',
              stylers: [{color: '#515c6d'}]
            },
            {
              featureType: 'water',
              elementType: 'labels.text.stroke',
              stylers: [{color: '#17263c'}]
            }
          ]
        }
        const map = new google.maps.Map(this.$el, mapProp)

        // disable default google maps UI e.g. zoom button.
        map.setOptions({disableDefaultUI: true})
        map.setOptions({zoomControl: false})

        console.log('CHECKING LOCATIONS AT START------------------------')
        console.log(this.locations)
        var setAddress
        console.log(this.address)
        if (JSON.stringify(this.address) === '{}') {
          console.log('UK')
          setAddress = 'England'
        } else {
          console.log('LOCATION ' + this.address.loc)
          setAddress = this.address.loc
          // setAddress = this.locations[0].country + ', ' + this.locations[0].city
        }
        console.log('ADDRESS OBJ BEFORE')
        console.log(this.address)
        const that = this
        // on start, go to below address.
        this.geocoder.geocode({ address: setAddress }, (results, status) => {
          if (status !== 'OK' || !results[0]) {
            swal('Oops!', `We couldn't find any parking slots in your area!`, 'warning')
            // throw new Error(status)
          }
          map.setCenter(results[0].geometry.location)
          map.fitBounds(results[0].geometry.viewport)
          if (this.locations[0] !== undefined) {
            const markerClickHandler = (marker) => {
              this.locInfoGlobal = marker.locationInfo
              console.log('LOCATIONINFO')
              map.setZoom(13)
              map.setCenter(marker.getPosition())
              // SEND DATA BACK TO PARENT TO PASS TO LOCATIONVIEW COMPONENT
              this.$emit('mapToLanding', this.locInfoGlobal, this.address)
              // this.emitToParent(this.locInfoGloba)
              // this.$router.push({ name: 'Booking', params: {location: this.locInfoGlobal, times: this.address} })
            }
            var markers = []
            var i = 0
            // this.$emit('childToParent1')
            this.locations.forEach(function (location) {
              console.log('LOCATION-------------------------- TEST ID MAP')
              console.log(location)
              var latLng = new google.maps.LatLng(location.latitude, location.longitude)
              console.log(location.postcode)
              // console.log('length ' + this.locations.length)
              // Creating a marker and putting it on the map
              console.log('ADDRESS OBJECT')
              console.log(that.address)
              var marker = new google.maps.Marker({
                position: latLng,
                map: map,
                locationInfo: location,
                times: that.address
              })
              // create markers array
              markers[i] = marker
              i = i + 1
              // HOW TO GET LAT AND LONG FROM MARKER OBJECT
              // var markerTest = markers[i].getPosition().lat()
              // console.log(markerTest)
              console.log('set visible')
              marker.setVisible(true)
              marker.addListener('click', () => markerClickHandler(marker))
              // google.maps.event.addListener(infowindow, 'domready', function () {
              //   console.log('DOM LOADED')
              //   google.maps.event.addDomListener(contentBtn, 'click', function () {
              //     console.log('SUCCESS')
              //   })
              // // Bind the click event on your button here
              // // this.$router.push({ name: 'Booking', params: {location: marker.locationInfo, times: that.address} })
              // })
              // marker.addListener('mouseover', () => markerHoverHandler(marker))
              // var infowindow = new google.maps.InfoWindow()
              // infowindow.setContent('hello')
              // infowindow.open(map, this)
            })
            // reset index to 0
            i = 0
            // for (var i = 0; i < this.locations.length; i++) {
            //   var location = this.locations[i]
            //   console.log('LOCATION')
            //   console.log(location)
            //   var latLng = new google.maps.LatLng(location.latitude, location.longitude)
            //   console.log(this.locations[i].postcode)
            //   console.log('length ' + this.locations.length)
            //   // Creating a marker and putting it on the map
            //   var marker = new google.maps.Marker({
            //     position: latLng,
            //     map: map
            //   })
            //   // create markers array
            //   markers[i] = marker
            //   // HOW TO GET LAT AND LONG FROM MARKER OBJECT
            //   // var markerTest = markers[i].getPosition().lat()
            //   // console.log(markerTest)
            //   // marker.setVisible(false)
            //   marker.addListener('click', () => markerClickHandler(marker))
            //   // return marker
            // }

            // this.getLocations()
            // create constant of global this to be able to pass through event listener.
            console.log('that' + that.locations[0].postcode)
            // Add listener when map is clicked
            // google.maps.event.addListener(map, 'click', (event) => {
            //   geocoder.geocode({
            //     'latLng': event.latLng
            //   }, function (results, status) {
            //     if (status === google.maps.GeocoderStatus.OK) {
            //       if (results[0]) {
            //         // this.getLocations() // results[0].address_components
            //         // that.sortByCountry(country)
            //         console.log(results[0].address_components[3].long_name)
            //         console.log('BEFORE PASSING TO METHOD CITY: ' + results[0].address_components[3].long_name + ' CITY LOC: ' + that.locations)
            //         // JUST TO TEST SORT
            //         // var filteredLocations = that.locations
            //         var filteredLocations = that.sortByCity(results[0].address_components[3].long_name, that.locations)
            //         that.getClosestLocations()
            //         // var closest = -1
            //         console.log('FILTERED: ' + filteredLocations.length)
            //         for (i = 0; i < filteredLocations.length; i++) {
            //           var loc = new google.maps.LatLng(filteredLocations[i].latitude, filteredLocations[i].longitude)
            //           console.log(event.latLng)
            //           console.log(loc)
            //           var d = google.maps.geometry.spherical.computeDistanceBetween(loc, event.latLng)
            //           console.log(filteredLocations[i])
            //           that.distances[i] = [filteredLocations[i].latitude, filteredLocations[i].longitude, d]
            //           // console.log(that.distances)
            //           // console.log(results[0].address_components)
            //           // alert(results[0].formatted_address)
            //         }
            //         // sort distances so we can get closest x
            //         // that.sortDistances()
            //         var closestDistances = that.distances
            //         console.log(closestDistances)
            //         closestDistances.sort((a, b) => (a[2] > b[2]) ? 1 : -1)
            //         filteredLocations.splice(0, 9)
            //         console.log('SORTED: ' + closestDistances)
            //         console.log(closestDistances[0])

            //         console.log(markers)
            //         for (i = 0; i < markers.length; i++) {
            //           // only looping through 10 values for closestDistances
            //           for (var ii = 0; ii < closestDistances.length; ii++) {
            //             console.log(markers[i].getPosition().lat() + '===' + closestDistances[ii][0] + ' ' + markers[i].getPosition().lng() + '===' + closestDistances[ii][1])
            //             if (markers[i].getPosition().lat() === closestDistances[ii][0] &&
            //                 markers[i].getPosition().lng() === closestDistances[ii][1]) {
            //               console.log('SET TO TRUE')
            //               markers[i].setVisible(true)
            //             }
            //           }
            //         }
            //       }
            //     }
            //   })
            // })

            // this.locations
            //   // .map(x => new google.maps.Marker({ ...x, map }))
            //   .map((location) => {
            //     const marker = new google.maps.Marker({
            //       ...location,
            //       map
            //       // icon: {
            //       //   url: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
            //       // }
            //     })
            //     marker.addListener('click', () => markerClickHandler(marker))
            //     return marker
            //   })
          } else {
            console.log(this.firstInit)
            if (this.firstInit === true) {
              swal('Oops!', `We couldn't find any parking slots in your area!`, 'warning')
            }
          }
        })
      } catch (error) {
        console.error(error)
      }
    },
    getClosestLocations () {
      console.log('test')
      this.geocoder.geocode({ address: this.address.loc }, (results, status) => {
        if (status !== 'OK' || !results[0]) {
          // throw new Error(status)
          swal('Oops!', `We couldn't find any parking slots in your area!`, 'warning')
        }
        console.log(results[0].geometry)
      })
    },
    sortByCity (city, locations) {
      for (var i = 0; i < locations.length; i++) {
        console.log('CITY: ' + city + ' CITY LOC: ' + locations[i].city)
        if (city !== locations[i].city) {
          console.log('DELETED')
          locations.splice(i, i + 1)
        }
      }
      console.log('LOCATIONS AFTER SORT ' + locations[0])
      console.log('LOCATIONS AFTER SORT ' + locations[1])
      return locations
    }
  },
  created () {
    this.locations = []
    this.init()
  }
}
</script>

<style>
html,
body {
  margin: 0;
  padding: 0;
}

.map_wrapper {
  /* width: 10vw;
  height: 50vh; */
  /* height:100%; */
  /* display: block; */
  /* float: right; */
  /* margin-left: 50px; */
  /* width: calc(100% - 50px); */
  padding-top: 60px;
  box-sizing: border-box;
  /* float: left; */
  z-index: 1;
  /* display: inline-block; */
}

#iw-container {
  margin-bottom: 10px;
}
#iw-container .iw-title {
  font-family: 'Open Sans Condensed', sans-serif;
  font-size: 22px;
  font-weight: 400;
  padding: 10px;
  background-color: #48b5e9;
  color: white;
  margin: 0;
  border-radius: 2px 2px 0 0;
}
#iw-container .iw-content {
  font-size: 13px;
  line-height: 18px;
  font-weight: 400;
  margin-right: 1px;
  padding: 15px 5px 20px 15px;
  max-height: 140px;
  overflow-y: auto;
  overflow-x: hidden;
}
.iw-content img {
  float: right;
  margin: 0 5px 5px 10px;
}
.iw-subTitle {
  font-size: 16px;
  font-weight: 700;
  padding: 5px 0;
}
.iw-bottom-gradient {
  position: absolute;
  width: 326px;
  height: 25px;
  bottom: 10px;
  right: 18px;
  background: linear-gradient(to bottom, rgba(255,255,255,0) 0%, rgba(255,255,255,1) 100%);
  background: -webkit-linear-gradient(top, rgba(255,255,255,0) 0%, rgba(255,255,255,1) 100%);
  background: -moz-linear-gradient(top, rgba(255,255,255,0) 0%, rgba(255,255,255,1) 100%);
  background: -ms-linear-gradient(top, rgba(255,255,255,0) 0%, rgba(255,255,255,1) 100%);
}

/* .MapSearch {
  margin-left: 50px;
  z-index: 10000000;
  /* position: absolute;
  width: calc(100% - 50px);
  margin-top: 50px;
} */

.swal-modal {
    /* margin-left: 50px; */
    /* width: calc(100% - 60px); */
    width: 100%;
}
</style>
