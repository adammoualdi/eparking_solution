<template>
<div id = "MapSearchWrapper">
  <div class="exitButton">
    <b-button type="submit" variant="primary" v-on:click="emitCloseSearch">EXIT</b-button>
  </div>
  <!-- <b-modal id="modal-1" title="BootstrapVue"> -->
    <b-container class="bv-example-row bv-example-row-flex-cols">
        <b-row align-v="start">
            <b-col>
                <b-form-input
                        class="locationFormInput"
                        name="location"
                        placeholder="Address line 1"
                        v-model="$v.form.address1.$model"
                        :state="$v.form.address1.$dirty ? !$v.form.address1.$error : null">
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
                    v-model="$v.form.address2.$model"
                    :state="$v.form.address2.$dirty ? !$v.form.address2.$error : null">
            </b-form-input>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <b-form-select required
                    class="locationFormInput"
                    :options="cityOptions"
                    placeholder="City"
                    v-model="$v.form.city.$model"
                    :state="$v.form.city.$dirty ? !$v.form.city.$error : null">
            </b-form-select>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <b-form-select required
              class="locationFormInput"
              :options="countryOptions"
              placeholder="Country"
              v-model="$v.form.country.$model"
              :state="$v.form.country.$dirty ? !$v.form.country.$error : null">
            </b-form-select>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <b-form-input
                    class="locationFormInput"
                    name="location"
                    placeholder="Postcode"
                    v-model="$v.form.postcode.$model"
                    :state="$v.form.postcode.$dirty ? !$v.form.postcode.$error : null">
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
                    v-model="$v.form.spaces.$model"
                    :state="$v.form.spaces.$dirty ? !$v.form.spaces.$error : null">
            </b-form-input>
          </b-col>
          <b-col>
            <b-input-group prepend="£" append="Per Hour">
              <b-form-input
                      class="locationFormInput"
                      type="number"
                      placeholder="Price"
                      v-model="$v.form.costPerHour.$model"
                      :state="$v.form.costPerHour.$dirty ? !$v.form.costPerHour.$error : null">
              </b-form-input>
            </b-input-group>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <b-form-group>
              <b-form-radio-group
                id="btn-radios-2"
                v-model="form.sensors"
                :options="options"
                buttons
                name="radios-btn-default"
              ></b-form-radio-group>
            </b-form-group>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <b-form-file
              v-model="$v.form.imagetmp.$model"
              :state="$v.form.imagetmp.$dirty ? !$v.form.imagetmp.$error : null"
              placeholder="Choose a file or drop it here..."
              drop-placeholder="Drop file here..."
            ></b-form-file>
          </b-col>
        </b-row>
        <b-row align-v="end">
            <b-col>
              <button class="searchBtn" type="button" name="button" :disabled="$v.form.$invalid" v-on:click="emitToParent">Add location</button>
            </b-col>
        </b-row>
    </b-container>
  <!-- </b-modal> -->
</div>
</template>

<script>
import DatePicker from '@/components/DateTimePicker'
import { Datetime } from 'vue-datetime'
import PostsService from '@/services/PostsService'
import swal from 'sweetalert'
import { EventBus } from '@/services/EventBus.js'
import gmapsInit from '@/utils/gmaps'
import { required } from 'vuelidate/lib/validators'
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
        country: 'United Kingdom',
        city: '',
        postcode: '',
        spaces: null,
        costPerHour: null,
        sensors: false,
        latitude: null,
        longitude: null,
        imagetmp: null,
        image: null
      },
      options: [
        { text: 'Sensors', value: 'true' },
        { text: 'No Sensors', value: 'false' }
        // { text: 'Radio 4', value: 'radio4' }
      ],
      countryOptions: [
        { text: 'United Kingdom', value: 'United Kingdom' }
      ],
      cityOptions: [
        { text: 'Leicester', value: 'Leicester' },
        { text: 'Sheffield', value: 'Sheffield' },
        { text: 'London', value: 'London' }
      ],
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
    form: {
      address1: {
        required
      },
      address2: {
        required
      },
      city: {
        required
      },
      country: {
        required
      },
      postcode: {
        required
      },
      spaces: {
        required
      },
      costPerHour: {
        required
      },
      imagetmp: {
        required
      }
    }
  },
  async mounted () {
    console.log('ADD LOADING')
    // const google = await gmapsInit()
    // const geocoder = new google.maps.Geocoder()
    this.google = await gmapsInit()
    this.geocoder = new this.google.maps.Geocoder()
    // this.form.country = 'United Kingdom'
    // Event listener for user clicking on map
    const that = this
    EventBus.$on('landingToAddLocation', function (payLoad, coords) {
      that.form.address1 = payLoad.address1
      that.form.address2 = payLoad.address2
      that.form.city = payLoad.city
      that.form.country = payLoad.country
      // that.form.postcode = payLoad.postcode
      that.checkPostcode(payLoad.postcode)
      that.form.longitude = coords.lng()
      that.form.latitude = coords.lat()
      console.log(that.form)
    })

    // this.form.address1 =
  },
  methods: {
    async emitToParent (event) {
      this.validate()
      console.log(this.form.image)
      this.form.image = new FormData()
      this.form.image.append('name', this.form.latitude + this.form.longitude)
      this.form.image.append('file', this.form.imagetmp)
      console.log(this.form)
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
          swal('Error!', 'Error finding location GPS', 'Error')
          // throw new Error(status)
          // NEED ERROR VALIDATION HERE FOR INCORRECT LOCATION QUERY
        } else {
        //   if (this.location === null) {
        //     this.invalidLoc = true
        //   } else if (this.arrivingTime >= this.leavingTime) {
        //     this.invalidDate = true
        //   } else {
          // that.form.latitude = results[0].geometry.location.lat()
          // that.form.longitude = results[0].geometry.location.lng()
          console.log(results)
          this.addLocation(this.form)
          var info = location
          this.$emit('childToParent', info)
          console.log(this.$emit('childToParent', info))
        //   }
        }
      })
    },
    emitCloseSearch (event) {
      this.$emit('closeSearch')
      console.log('REMOVE POINTER')
      EventBus.$emit('removePointer', this.form)
    },
    async addLocation (location) {
      console.log(location)
      const response = await PostsService.addLocation(location)
      const resp = await PostsService.addImage(location.image)
      console.log(resp)
      swal('Requested!', 'Your location has been requested - we will get back to you soon!', 'success')
      console.log(response)
      EventBus.$emit('addLocation', this.form)
    },
    validate () {
      this.checkPostcode(this.form.postcode)
    },
    checkPostcode (postcode) {
      console.log(postcode)
      var pcodeRegularExp = /^([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([A-Za-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z])))) {0,1}[0-9][A-Za-z]{2})$/
      if (!pcodeRegularExp.test(postcode)) {
        // this.form.postcode = ''
        // alert('Invalid postcode')
      } else {
        this.form.postcode = postcode
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
