<template>
  <div class="map_wrapper">
    <div class="map"></div>
    <!-- <MapSearch class="MapSearch"> </MapSearch> -->
  </div>
</template>

<script>
// import MapSearch from '@/components/MapSearch'
import gmapsInit from '@/utils/gmaps'

export default {
  name: 'Map',
  data () {
    return {
      outputLocations: [],
      location: null,
      distances: [],
      sortedLocations: [],
      geocoder: null
    }
  },
  props: {
    locations: {
      type: Array,
      required: true
      // default: () => []
    },
    address: {}
    // times: 1
  },
  watch: {
    address: function () {
      this.getClosestLocations()
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
        const map = new google.maps.Map(this.$el)

        // disable default google maps UI e.g. zoom button.
        map.setOptions({disableDefaultUI: true})
        map.setOptions({zoomControl: false})

        // InfoWindow content
        // var content = '<div id="iw-container">' +
        //                   '<div class="iw-title">Porcelain Factory of Vista Alegre</div>' +
        //                   '<div class="iw-content">' +
        //                     '<div class="iw-subTitle">History</div>' +
        //                     '<img src="http://maps.marnoto.com/en/5wayscustomizeinfowindow/images/vistalegre.jpg" alt="Porcelain Factory of Vista Alegre" height="115" width="83">' +
        //                     '<p>Founded in 1824, the Porcelain Factory of Vista Alegre was the first industrial unit dedicated to porcelain production in Portugal. For the foundation and success of this risky industrial development was crucial the spirit of persistence of its founder, José Ferreira Pinto Basto. Leading figure in Portuguese society of the nineteenth century farm owner, daring dealer, wisely incorporated the liberal ideas of the century, having become "the first example of free enterprise" in Portugal.</p>' +
        //                     '<div class="iw-subTitle">Contacts</div>' +
        //                     '<p>VISTA ALEGRE ATLANTIS, SA<br>3830-292 Ílhavo - Portugal<br>' +
        //                     '<br>Phone. +351 234 320 600<br>e-mail: geral@vaa.pt<br>www: www.myvistaalegre.com</p>' +
        //                   '</div>' +
        //                   '<div class="iw-bottom-gradient"></div>' +
        //                 '</div>'

        // var infowindow = new google.maps.InfoWindow({
        //   content: content
        // })
        console.log('CHECKING LOCATIONS')
        console.log(this.locations)
        var setAddress
        if (this.locations[0] === undefined) {
          console.log('UK')
          setAddress = 'England'
        } else {
          console.log('LEICESTER')
          setAddress = this.locations[0].country + ', ' + this.locations[0].city
        }
        console.log('ADDRESS OBJ BEFORE')
        console.log(this.address)
        const that = this
        // on start, go to below address.
        this.geocoder.geocode({ address: setAddress }, (results, status) => {
          if (status !== 'OK' || !results[0]) {
            throw new Error(status)
          }
          map.setCenter(results[0].geometry.location)
          map.fitBounds(results[0].geometry.viewport)
          if (this.locations[0] !== undefined) {
            const markerClickHandler = (marker) => {
              console.log(marker.locationInfo)
              this.$router.push({ name: 'Booking', params: {location: marker.locationInfo, times: that.address} })
              // infowindow.open(map, marker)
              // map.setZoom(13)
              // map.setCenter(marker.getPosition())
            }

            // Create markers
            var markers = []
            var i = 0
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
          throw new Error(status)
        }
        console.log(results[0].geometry)
      })
    },
    sortByCountry () {
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
    },
    sortDistances (distances) {
    },
    splitMarkerObject (marker) {
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
  display: block;
  /* float: right; */
  /* margin-left: 50px; */
  /* width: calc(100% - 50px); */
  /* margin-top: 60px; */
  box-sizing: border-box;
  /* float: left; */
  /* z-index: 1; */
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
</style>
