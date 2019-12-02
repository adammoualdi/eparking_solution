<template>
<div id = "MapSearchWrapper">
    <b-container class="bv-example-row bv-example-row-flex-cols">
        <b-row align-v="start">
            <b-col>
                <b-form-input
                        class="locationFormInput"
                        name="location"
                        placeholder="Enter an address"
                        v-model="location">
                </b-form-input>
                <p class="error" v-show="invalidLoc">Location is required</p>
            </b-col>
        </b-row>
        <b-row align-v="center">
            <b-col>
                <Datetime type="datetime" v-model="$v.arrivingTime.$model" :minute-step=30></Datetime>
                <!-- <p class="error" v-if="!$v.arrivingTime.required">this field is required</p> -->
            </b-col>
            <b-col>
                <Datetime type="datetime" v-model="$v.leavingTime.$model" @change="$v.leavingTime.$touch" :minute-step=30></Datetime>
                <p class="error" v-show="invalidDate">Arrive date must be before leaving date</p>
                <!-- <p class="error" v-if="!$v.arrivingTime.isValid">leaving time cannot be before arriving time</p> -->
            </b-col>
        </b-row>
        <b-row align-v="end">
            <b-col>
              <button type="button" name="button" v-on:click="emitToParent">Search</button>
            </b-col>
        </b-row>
    </b-container>
</div>
</template>

<script>
import DatePicker from '@/components/DateTimePicker'
import { Datetime } from 'vue-datetime'
// import { required } from 'vuelidate/lib/validators'
// const check_times = (arrive, leave) => (arrive < leave)
// import gmapsInit from '@/utils/gmaps'
// import { Datetime } from 'vue-datetime';
export default {
  name: 'MapSearch',
  components: {
    DatePicker,
    Datetime
  },
  data () {
    return {
      selected: null,
      location: null,
      arrivingTime: ' ',
      leavingTime: ' ',
      invalidDate: false,
      invalidLoc: false
    }
  },
  validations: {
    arrivingTime: {
      // required
    },
    leavingTime: {
      // required
    }
  },
  async mounted () {
    // const google = await gmapsInit()
    // const geocoder = new google.maps.Geocoder()
  },
  methods: {
    emitToParent (event) {
      console.log(this.location)
      this.invalidLoc = false
      this.invalidDate = false
      if (this.location === null) {
        this.invalidLoc = true
      } else if (this.arrivingTime >= this.leavingTime) {
        this.invalidDate = true
      } else {
        var info = this.location
        this.$emit('childToParent', info, this.arrivingTime, this.leavingTime)
      }
    },
    isValid () {
      if (this.arrivingTime <= this.leavingTime) {
        return false
      } else {
        return true
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

#MapSearchWrapper {
  display: block;
  z-index: 20;
  /* float: right; */
}

/* .locationFormInput col{
  width: 20px;
} */

/* .vdatetime-overlay {
  width: calc(100% - 50px);
  margin-left:50px;
}

.vdatetime-popup {

} */

/*
#row1 {
  background-color: red;
}

#row2 {
  background-color: green;
}

#row3 {
  background-color: yellow;
} */

/* #MapSearchWrapper {
  margin-left: 50px;
  display: inline-block;
  text-align: center;
} */
/* #mySidenav {
  padding: 0px;
  margin: 0px;
} */
</style>
