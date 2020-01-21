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
                      placeholder="Address line 1"
                      v-model="form.address1">
              </b-form-input>
              <p class="error" v-show="invalidLoc">Location is required</p>
              <p class="error" v-show="invalidLocGoogle">Please enter a valid address</p>
          </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-form-input
                  class="locationFormInput"
                  name="location"
                  placeholder="Address line 2"
                  v-model="form.address2">
          </b-form-input>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-form-input
                  class="locationFormInput"
                  name="location"
                  placeholder="City"
                  v-model="form.city">
          </b-form-input>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-form-input
                  class="locationFormInput"
                  name="location"
                  placeholder="Country"
                  v-model="form.country">
          </b-form-input>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-form-input
                  class="locationFormInput"
                  name="location"
                  placeholder="Postcode"
                  v-model="form.postcode">
          </b-form-input>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-form-input
                  class="locationFormInput"
                  type="number"
                  name="location"
                  placeholder="Spaces"
                  v-model="form.spaces">
          </b-form-input>
        </b-col>
        <b-col>
          <b-form-input
                  class="locationFormInput"
                  type="number"
                  name="location"
                  placeholder="Price"
                  v-model="form.price">
          </b-form-input>
        </b-col>
      </b-row>
      <b-row align-v="end">
          <b-col>
            <button class="searchBtn" type="button" name="button" v-on:click="emitToParent">Add location</button>
          </b-col>
      </b-row>
  </b-container>
</div>
</template>

<script>
import DatePicker from '@/components/DateTimePicker'
import { Datetime } from 'vue-datetime'
import PostsService from '@/services/PostsService'
// import { required } from 'vuelidate/lib/validators'
// const check_times = (arrive, leave) => (arrive < leave)
import gmapsInit from '@/utils/gmaps'
// import swal from 'sweetalert'
// import { Datetime } from 'vue-datetime';
export default {
  name: 'MapSearch',
  components: {
    DatePicker,
    Datetime
  },
  data () {
    return {
      form: {
        address1: '',
        address2: '',
        country: '',
        city: '',
        postcode: '',
        spaces: null,
        price: null
      },
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
    async emitToParent (event) {
      console.log(this.location)
      this.invalidLoc = false
      this.invalidDate = false
      this.invalidLocGoogle = false
      var that = this
      var location = this.form.address1 + ' ' + this.form.address2 + ' ' + this.form.city + ' ' + this.form.country + ' ' + this.form.postcode
      this.geocoder.geocode({ address: location }, (results, status) => {
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
        //   if (this.location === null) {
        //     this.invalidLoc = true
        //   } else if (this.arrivingTime >= this.leavingTime) {
        //     this.invalidDate = true
        //   } else {
          this.addLocation()
          var info = location
          this.$emit('childToParent', info)
          console.log(this.$emit('childToParent', info))
        //   }
        }
      })
    },
    emitCloseSearch (event) {
      this.$emit('closeSearch')
    },
    async addLocation () {
      console.log(this.form)
      const response = await PostsService.addLocation(this.form)
      console.log(response)
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
  padding-top: 5px;
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
