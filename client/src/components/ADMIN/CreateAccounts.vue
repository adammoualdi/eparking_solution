<template>
  <div class="registerWrapper">
    <div class="headerNav">
      <NavigationBarReg></NavigationBarReg>
    </div>
    <b-container class="wrapper">
      <b-row align-h="center">
        <b-col cols="10">
          <b-card class="p-3">
            <h3 class="mb-4">Request Security Accounts</h3>
            <div>
              <b-form @submit.stop.prevent="onSubmit">
                <b-form-group id="example-input-group-1" label="Username" label-for="example-input-1">
                  <b-form-input
                    id="example-input-1"
                    name="example-input-1"
                    v-model="$v.form.username.$model"
                    :state="$v.form.username.$dirty ? !$v.form.username.$error : null"
                    aria-describedby="input-1-live-feedback"
                  ></b-form-input>

                  <b-form-invalid-feedback
                    id="input-1-live-feedback"
                  >This is a required field and must be at least 3 characters.</b-form-invalid-feedback>
                </b-form-group>

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

                <b-button type="submit" variant="primary" :disabled="$v.form.$invalid">Submit</b-button>
              </b-form>
            </div>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import PostsService from '@/services/PostsService'
import { validationMixin } from 'vuelidate'
import NavigationBarReg from '@/components/NavigationBarReg'
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
        username: null,
        firstname: null,
        lastname: null,
        dofb: null,
        email: null
      }
    }
  },
  components: {
    NavigationBarReg
  },
  validations: {
    form: {
      username: {
        required,
        minLength: minLength(4),
        maxLength: maxLength(20)
      },
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
      }
    }
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
      console.log(this.form.dofb)
      const response = await PostsService.register({
        username: this.form.username,
        email: this.form.email,
        firstname: this.form.firstname,
        lastname: this.form.lastname,
        password: 'fakepassword',
        password2: 'fakepassword',
        dofb: this.form.dofb,
        roleName: 'SECURITY'
      })
      console.log(response.data.errorContent)
      // console.log('Error ' + this.error)
      // console.log('RESPONSE ' + response.data.statusCode)
      this.$router.push({ name: 'Login' })
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
