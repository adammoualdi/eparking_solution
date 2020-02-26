<template>
<div id = "MapSearchWrapper">
  <div class="exitButton">
    <b-button type="submit" variant="primary" v-on:click="emitCloseSearch">EXIT</b-button>
  </div>
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
            <p class="error" v-show="invalidLocGoogle">Please enter a valid address</p>
          </b-col>
      </b-row>
      <b-row align-v="center">
          <b-col>
              <Datetime class="dateTimeInput" placeholder="Enter arriving time" type="datetime" v-model="$v.arrivingTime.$model" :minute-step=30></Datetime>
              <!-- <p class="error" v-if="!$v.arrivingTime.required">this field is required</p> -->
          </b-col>
          <b-col>
              <Datetime class="dateTimeInput" placeholder="Enter leaving time" type="datetime" v-model="$v.leavingTime.$model" @change="$v.leavingTime.$touch" :minute-step=30></Datetime>
              <!-- <p class="error" v-if="!$v.arrivingTime.isValid">leaving time cannot be before arriving time</p> -->
          </b-col>
      </b-row>
      <b-row align-v="end">
          <b-col>
            <button class="searchBtn" type="button" name="button" v-on:click="emitToParent">Search</button>
            <p class="error" v-show="invalidDate">Arrive date must be before leaving date</p>
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
import gmapsInit from '@/utils/gmaps'
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
      invalidLoc: false,
      invalidLocGoogle: false,
      google: null,
      geocoder: null
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
    this.google = await gmapsInit()
    this.geocoder = new this.google.maps.Geocoder()
  },
  methods: {
    emitToParent (event) {
      console.log(this.location)
      this.invalidLoc = false
      this.invalidDate = false
      this.invalidLocGoogle = false
      var that = this
      this.geocoder.geocode({ address: this.location }, (results, status) => {
        if (status !== 'OK' || !results[0]) {
          console.log('ERROR WITH ADDRESS')
          // not working because inside local function
          that.invalidLocGoogle = true
          console.log(that.invalidLoc)
          // swal('Type something:', {
          //   content: 'input'
          // })
          //   .then((value) => {
          //     swal(`You typed: ${value}`)
          //     th
          //   })
          // throw new Error(status)
          // NEED ERROR VALIDATION HERE FOR INCORRECT LOCATION QUERY
        } else {
          if (this.location === null) {
            this.invalidLoc = true
          } else if (this.arrivingTime >= this.leavingTime) {
            this.invalidDate = true
          } else {
            var info = this.location
            console.log('SEARCH')
            this.$emit('childToParent', info, this.arrivingTime, this.leavingTime)
            // console.log(this.$emit('childToParent', info, this.arrivingTime, this.leavingTime))
          }
        }
      })
    },
    emitCloseSearch (event) {
      this.$emit('closeSearch')
    },
    // isValid () {
    //   if (this.arrivingTime <= this.leavingTime) {
    //     return false
    //   } else {
    //     return true
    //   }
    // },
    async checkLocation () {}
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
  padding-top: 60px;
  /* background: red;  */
  /* float: right; */
}

.exitButton {
  /* position: absolute; */
  padding-left: 80%;
  padding-top: 10px;
  z-index: 999;
}

.bv-example-row {
  max-width: 500px;
  width: 100%;
}

.dateTimeInput {
  max-width: 500px;
  width: 100%;
}

.vdatetime-input {
  width: 100%;
  height: 50px;
}

.vdatetime-input:focus {
  background-color: lightblue;
}

.col {
  padding-right: 0px;
  padding-left: 0px;
}

.searchBtn {
  width: 100%;
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
}

.searchBtn:hover {
  box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
}

.locationFormInput:focus {
  background-color: lightblue;
}

.form-control {
  height: 50px;
}

</style>
