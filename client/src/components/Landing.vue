<template>
  <div class="landingWrapper">
      <div class="Nav">
        <NavBar class="nav"> </NavBar>
      </div>
      <div class="LandingMapSearch">
        <Map class="map"> </Map>
        <MapSearch> </MapSearch>
      </div>
  </div>
</template>

<script>

import Map from '@/components/Map'
import MapSearch from '@/components/MapSearch'
import NavBar from '@/components/NavBar'
export default {
  name: 'Landing',
  components: {
    Map,
    MapSearch,
    NavBar
  },
  data () {
    return {

    }
  },
  methods: {

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
</style>
