<template>
    <div id = "headerWrapper">
<b-container class="bv-example-row bv-example-row-flex-cols">
  <b-row align-v="start">
    <b-col>
      <b-form-input
        name="location"
        v-model="location">
      </b-form-input>
    </b-col>
  </b-row>
  <b-row align-v="center">
    <b-col>
      <DatePicker v-model="arrivingTime"> </DatePicker>
      <b-form-select name="arrivingTime" v-model="arrivingTime" :options="options"></b-form-select></b-col>
    <b-col>
      <DatePicker v-model="leavingTime"> </DatePicker>
      <b-form-select name="leavingTime" v-model="leavingTime" :options="options"></b-form-select>
    </b-col>
  </b-row>
  <b-row align-v="end">
    <b-col>
    </b-col>
  </b-row>
</b-container>
    </div>
</template>

<script>
import DatePicker from '@/components/DatePicker'
export default {
  name: 'MapSearch',
  components: {
    DatePicker
  },
  data () {
    return {
      selected: null,
      location: null,
      arrivingTime: null,
      leavingTime: null,
      options: [
        { value: null, text: 'Please select a time' },
        { value: '00', text: '00:00' },
        { value: '01', text: '01:00' },
        { value: '02', text: '02:00' },
        { value: '03', text: '03:00' },
        { value: '04', text: '04:00' },
        { value: '05', text: '05:00' },
        { value: '06', text: '06:00' },
        { value: '07', text: '07:00' },
        { value: '08', text: '08:00' },
        { value: '09', text: '09:00' },
        { value: '10', text: '10:00' },
        { value: '11', text: '11:00' },
        { value: '12', text: '12:00' },
        { value: '13', text: '13:00' },
        { value: '14', text: '14:00' },
        { value: '15', text: '15:00' },
        { value: '16', text: '16:00' },
        { value: '17', text: '17:00' },
        { value: '18', text: '18:00' },
        { value: '19', text: '19:00' },
        { value: '20', text: '20:00' },
        { value: '21', text: '21:00' },
        { value: '22', text: '22:00' },
        { value: '23', text: '23:00' },
        { value: '24', text: '24:00' }
      ],
      range: {
        start: new Date(), // Jan 16th, 2018
        end: new Date() // Jan 19th, 2018
      }
    }
  }
}
</script>

<style>
html,
body {
  margin: 0;
  padding: 0;
  height: 100%;
}

* {
  margin: 0px;
  padding: 0px;
}

head {
  margin: 0px;
  padding: 0px;
}

.v-sidebar-menu {
  height: 100%;
  padding-top: 60px;
}

#row1 {
  background-color: red;
}

#row2 {
  background-color: green;
}

#row3 {
  background-color: yellow;
}

/* #headerWrapper {
  width: 100%;
  height: 60px;
  background-color: #2a2a2e;
  position: absolute;
  margin: 0px;
  padding: 0px;
  float: left;
} */

#mySidenav {
  padding: 0px;
  margin: 0px;
}
</style>
