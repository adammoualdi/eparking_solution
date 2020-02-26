<template>
  <div id = "LocationViewWrapper">
    <div v-if="firstInit">
        <div class="LocationView">
            <b-card bg-variant="dark" text-variant="white" class="card">
                <b-card-text>
                    <h3>{{ location.address1 + ' ' + location.address2 }} </h3>
                    <h4>{{ location.country + ', ' + location.city + ', ' + location.postcode }}</h4>
                </b-card-text>
                <!-- <b-button href="#" variant="primary">Go somewhere</b-button> -->
                <b-button type="submit" variant="primary" v-on:click="goToOverview">OVERVIEW</b-button>&nbsp;
            </b-card>
        </div>
    </div>
      <!-- </div> -->
    <!-- </div> -->
  </div>
</template>

<script>
// import PostsService from '@/services/PostsService'
export default {
  name: 'OwnerLocationView',
  data () {
    return {
      firstInit: false
    }
  },
  props: {
    location: null,
    booking: null
  },
  watch: {
    location: function () {
      // bool value used to set validation events off after first round of init.
      this.firstInit = true
    }
  },
  computed: {
    titleText () {
      return location.address1 + location.address2
    }
  },
  created () {
  },
  methods: {
    goToOverview () {
      console.log('OVERVIEW')
      this.$router.push({ name: 'LocationOverview', params: {locationId: this.location.id, location: this.location} })
    }
  }
}
</script>

<style type="text/css">

.LocationViewInfoWrapper {
    /* display: block; */
    /* height: 100%; */
    /* position: absolute; */
    /* display: block; */
    background: red;
    height: 100%;
    /* width: calc(100% - 50px); */
    width: 100%;
    /* margin-left: 50px; */
    /* height: 20vh; */
    /* padding-top: 60px; */
}

#LocationViewWrapper {
    display: block;
    width: 100%;
    /* height: 100%; */
    background: #2a2a2e;
}

.LocationView {
    /* height: calc(40vh - 60px); */
    height: 100%;
}

.LocationView .card {
    /* height: calc(40vh - 60px); */
    height: 100%;
}

</style>
