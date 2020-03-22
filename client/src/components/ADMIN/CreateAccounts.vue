<template>
  <div class="registerWrapper">
    <div class="headerNav">
      <NavigationBarAdmin></NavigationBarAdmin>
    </div>
    <div v-if="isMounted">
      <b-container class="wrapper">
        <b-row align-h="center">
          <b-col cols="10">
            <b-card class="p-3">
              <h3 class="mb-4">Request Security Accounts</h3>
              <div>
                <b-form @submit.stop.prevent="onSubmit">
                  <b-form-group id="example-input-group-2" label="Email" label-for="example-input-2">
                    <b-form-input
                      id="example-input-2"
                      name="example-input-2"
                      v-model="$v.form.email.$model"
                      :state="$v.form.email.$dirty ? !$v.form.email.$error : null"
                      aria-describedby="input-2-live-feedback"
                    ></b-form-input>

                    <b-form-invalid-feedback id="input-2-live-feedback">This is a required field.</b-form-invalid-feedback>
                  </b-form-group>

                  <b-form-group
                    id="example-input-group-3"
                    label="First Name"
                    label-for="example-input-3"
                  >
                    <b-form-input
                      id="example-input-3"
                      name="example-input-3"
                      v-model="$v.form.firstname.$model"
                      :state="$v.form.firstname.$dirty ? !$v.form.firstname.$error : null"
                      aria-describedby="input-3-live-feedback"
                    ></b-form-input>

                    <b-form-invalid-feedback id="input-3-live-feedback">This is a required field.</b-form-invalid-feedback>
                  </b-form-group>

                  <b-form-group
                    id="example-input-group-4"
                    label="Last Name"
                    label-for="example-input-4"
                  >
                    <b-form-input
                      id="example-input-4"
                      name="example-input-4"
                      v-model="$v.form.lastname.$model"
                      :state="$v.form.lastname.$dirty ? !$v.form.lastname.$error : null"
                      aria-describedby="input-4-live-feedback"
                    ></b-form-input>

                    <b-form-invalid-feedback id="input-4-live-feedback">This is a required field.</b-form-invalid-feedback>
                  </b-form-group>

                  <b-form-group
                    id="example-input-group-5"
                    label="Date of birth"
                    label-for="example-input-5"
                  >
                    <b-form-input
                      type="date"
                      id="example-input-5"
                      name="example-input-5"
                      v-model="$v.form.dofb.$model"
                      :state="$v.form.dofb.$dirty ? !$v.form.dofb.$error : null"
                      aria-describedby="input-5-live-feedback"
                      placeholder="Enter the users date of birth">
                    </b-form-input>

                    <b-form-invalid-feedback id="input-5-live-feedback">This is a required field.</b-form-invalid-feedback>
                  </b-form-group>

                  <b-form-group
                    id="input-group-6"
                    label="Location"
                    label-for="input-6"
                  >

                    <b-form-select
                      v-model="$v.form.location.$model"
                      :options="cityOptions"
                      :select-size="1"
                      :state="$v.form.location.$dirty ? !$v.form.location.$error : null"
                    >
                    </b-form-select>
                    <b-form-invalid-feedback id="input-6-live-feedback">This is a required field.</b-form-invalid-feedback>
                  </b-form-group>

                  <b-button type="submit" variant="primary" :disabled="$v.form.$invalid">Submit</b-button>
                </b-form>
              </div>
            </b-card>
          </b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
import PostsService from '@/services/PostsService'
import { validationMixin } from 'vuelidate'
import NavigationBarAdmin from '@/components/ADMIN/NavigationBarAdmin'
import { required, minLength, maxLength, email } from 'vuelidate/lib/validators'
// var currentTime = new Date()
// console.log(currentTime)
// console.log(currentTime - 18)
// const checkAge = (value) => {
//   console.log(value)
//   if (currentTime - 18 < value) {
//     return true
//   } else {
//     return false
//   }
// }

// const currentDate = moment(new Date()).startOf('day')
// const minDate = window.vuelidate.withParams({minDate: currentDate.format('DD.MM.YYYY')}, value => moment(value, 'DD.MM.YYYY', true).isSameOrAfter(currentDate))
export default {
  name: 'CreateAccounts',
  mixins: [validationMixin],
  data () {
    return {
      submitted: false,
      form: {
        firstname: null,
        lastname: null,
        dofb: null,
        email: null,
        location: null
      },
      cityOptions: [],
      isMounted: false
    }
  },
  components: {
    NavigationBarAdmin
  },
  validations: {
    form: {
      email: {
        required,
        email
      },
      firstname: {
        required,
        minLength: minLength(2),
        maxLength: maxLength(20)
      },
      lastname: {
        required,
        minLength: minLength(2),
        maxLength: maxLength(20)
      },
      dofb: {
        required
        // minDate
      },
      location: {
        required
      }
    }
  },
  mounted () {
    this.getLocations()
  },
  methods: {
    onSubmit (evt) {
      evt.preventDefault()
      console.log('submitted')
      this.submitted = true
      this.$v.form.$touch()
      // if (this.$v.form.$anyError) {
      if (this.$v.form.$error) {
        console.log('error')
        return
      }

      console.log('success')
      this.register()
      // Form submit logic
    },
    async register () {
      console.log(this.form.location)
      const response = await PostsService.register({
        username: 'username',
        email: this.form.email,
        firstname: this.form.firstname,
        lastname: this.form.lastname,
        password: 'fakepassword',
        password2: 'fakepassword',
        dofb: this.form.dofb,
        roleName: 'SECURITY',
        locationId: this.form.location
      })
      console.log(response.data.errorContent)
      // console.log('Error ' + this.error)
      // console.log('RESPONSE ' + response.data.statusCode)
      this.$router.push({ name: 'AdminApproval' })
    },
    async getLocations () {
      const response = await PostsService.getLocations()
      this.locations = response.data.locations
      // console.log(response)
      // this.locationOptions = []
      for (var i = 0; i < response.data.locations.length; i++) {
        console.log(response.data.locations[i])
        // var address = {value: response.data.locations[i].address1, text: response.data.locations[i].address1}
        var name = response.data.locations[i].city + ', ' + response.data.locations[i].address1 + ', ' + response.data.locations[i].address2 + ', ' + response.data.locations[i].postcode
        var loc = {value: response.data.locations[i].id, text: name}
        // console.log(address)
        // this.locationOptions.push(address)
        // returns -1 if not found
        // if (this.cityOptions.indexOf(city) === -1) {
        //   this.cityOptions.push(city)
        // }
        this.cityOptions.push(loc)
      }
      this.isMounted = true
    }
  }
}
</script>

<style>

.headerNav {
  width: 100%;
  height: 60px;
  /* background-color: #2a2a2e; */
  position: absolute;
  /* margin-top: -60px; */
  display: block;
  padding: 0px;
}

.wrapper {
  padding-top: 80px;
}

.navbar {
  z-index: 9999;
}
</style>
