<template>
  <div id = "BookingWrapper">
    <div class="Nav">
      <NavBar class="nav"> </NavBar>
    </div>
    <div class="BookingInfoWrapper">
      <b-form-select v-model="selected" :options="options"></b-form-select>
      <b-form-select v-model="selected" :options="options" size="sm" class="mt-3"></b-form-select>
      <div class="mt-3">Selected: <strong>{{ selected }}</strong></div>
    </div>
  </div>
</template>

<script>
// import PostsService from '@/services/PostsService'
import NavBar from '@/components/NavBar'
export default {
  name: 'Booking',
  components: {
    NavBar
  },
  data () {
    return {
      selected: null,
      options: [
        { value: null, text: 'Please select an option' },
        { value: 'a', text: 'This is First option' },
        { value: 'b', text: 'Selected Option' },
        { value: { C: '3PO' }, text: 'This is an option with object value' },
        { value: 'd', text: 'This one is disabled', disabled: true }
      ]
    }
  },
  mounted () {
    this.getId()
  },
  methods: {
    getId () {
      console.log(this.$route.params.id)
    },
    async getBookingInfo () {
    }
  }
}
</script>

<style type="text/css">

.BookingInfoWrapper {
    /* display: block; */
    height: 100%;
    /* position: absolute; */
    width: calc(100% - 50px);
    margin-left: 50px;
    padding-top: 60px;
}

.BookingWrapper {
    margin: 0px;
    padding: 0px;
}

</style>
