<template>
  <div class="map">
  </div>
</template>

<script>
// import PostsService from '@/services/PostsService'
import gmapsInit from '@/utils/gmaps'

const locations = [
  {
    position: {
      lat: 52.619497522,
      lng: -1.121332848
    }
  },
  {
    position: {
      lat: 53.34186,
      lng: -1.44232
    }
  }
]

export default {
  name: 'Map',
  async mounted () {
    try {
      console.log(this.$route.query)
      const google = await gmapsInit()
      const geocoder = new google.maps.Geocoder()
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

      // on start, go to below address.
      geocoder.geocode({ address: 'Leicester' }, (results, status) => {
        if (status !== 'OK' || !results[0]) {
          throw new Error(status)
        }
        map.setCenter(results[0].geometry.location)
        map.fitBounds(results[0].geometry.viewport)
        const markerClickHandler = (marker) => {
          this.$router.push({ name: 'Booking' })
          // infowindow.open(map, marker)
          // map.setZoom(13)
          // map.setCenter(marker.getPosition())
        }

        console.log(location)

        locations
          // .map(x => new google.maps.Marker({ ...x, map }))
          .map((location) => {
            const marker = new google.maps.Marker({
              ...location,
              map
              // icon: {
              //   url: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
              // }
            })
            marker.addListener('click', () => markerClickHandler(marker))
            return marker
          })
      })
    } catch (error) {
      console.error(error)
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

.map {
  /* width: 10vw;
  height: 50vh; */
  display: block;
  /* float: right; */
  /* margin-left: 50px; */
  /* width: calc(100% - 50px); */
  /* margin-top: 60px; */
  box-sizing: border-box;
  /* float: left; */
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
