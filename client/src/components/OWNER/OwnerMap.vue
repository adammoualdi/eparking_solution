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
import { EventBus } from '@/services/EventBus.js'
// import { EventBus } from '@/services/EventBus.js'

export default {
  name: 'OwnerMap',
  data () {
    return {
      outputLocations: [],
      location: null,
      distances: [],
      sortedLocations: [],
      geocoder: null,
      locInfoGlobal: null,
      firstInit: false,
      results: null
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
      console.log('LOCATIONS ARRAY CHANGED')
      this.init()
    }
  },
  created () {
    // this.locations = []
    this.$emit('mapClick', 'test')
    this.init()
  },
  // components: {
  //   MapSearch
  // },
  methods: {
    async init () {
      console.log(this.locations)
      try {
        console.log('INIT')
        // console.log(this.$route.query)
        const google = await gmapsInit()
        this.geocoder = new google.maps.Geocoder()
        const map = new google.maps.Map(this.$el)

        // disable default google maps UI e.g. zoom button.
        map.setOptions({disableDefaultUI: true})
        map.setOptions({zoomControl: false})

        console.log('CHECKING LOCATIONS')
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
            }
            var markers = []
            var i = 0
            // this.$emit('childToParent1')
            this.locations.forEach(function (location) {
              console.log('LOCATION')
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
            })
            // reset index to 0
            i = 0
          } else {
            console.log(this.firstInit)
            if (this.firstInit === true) {
              swal('Oops!', `We couldn't find any parking slots in your area!`, 'warning')
            }
          }

          // Event listener to click on map
          // Allows users to plot parking location on the map for accurate GPS coords.
          google.maps.event.addListener(map, 'click', (event) => {
            this.geocoder.geocode({
              'latLng': event.latLng
            }, function (results, status) {
              console.log('TEST')
              EventBus.$emit('mapClick', results, event.latLng)
              if (status === google.maps.GeocoderStatus.OK) {
                var marker = new google.maps.Marker({
                  position: event.latLng,
                  map: map
                  // locationInfo: location,
                })
                marker.setVisible(true)
              }
            })
          })
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
    }
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
  display: block;
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

</style>
