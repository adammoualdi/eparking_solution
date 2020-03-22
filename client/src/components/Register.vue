<template>
  <div class="registerWrapper">
    <div class="headerNav">
      <NavigationBarReg></NavigationBarReg>
    </div>
    <b-container class="wrapper">
      <b-row align-h="center">
        <b-col cols="10">
          <b-card class="p-3">
            <h3 class="mb-4">Register</h3>
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
                  label="First and Last Name"
                  label-for="example-input-3"
                >

                  <b-input-group class="mb-2">
                    <b-form-input
                      placeholder="Firstname"
                      aria-label="Firstname"
                      id="example-input-3"
                      name="example-input-3"
                      v-model="$v.form.firstname.$model"
                      :state="$v.form.firstname.$dirty ? !$v.form.firstname.$error : null"
                      aria-describedby="input-3-live-feedback">
                    </b-form-input>
                    <b-form-input
                      placeholder="Lastname"
                      aria-label="Lastname"
                      id="example-input-4"
                      name="example-input-4"
                      v-model="$v.form.lastname.$model"
                      :state="$v.form.lastname.$dirty ? !$v.form.lastname.$error : null"
                      aria-describedby="input-4-live-feedback">
                    </b-form-input>
                  </b-input-group>
                  <b-form-invalid-feedback id="input-3-live-feedback">This is a required field.</b-form-invalid-feedback>
                </b-form-group>

                <!-- <b-form-group
                  id="example-input-group-4"
                  label="Last Name"
                  label-for="example-input-4"
                >
                  <b-form-input
                  ></b-form-input>
                  <b-form-invalid-feedback id="input-4-live-feedback">This is a required field.</b-form-invalid-feedback>
                </b-form-group> -->

                <b-form-group id="example-input-group-5" label="Password" label-for="example-input-5">
                  <b-form-input
                    id="example-input-5"
                    name="example-input-5"
                    type="password"
                    v-model="$v.form.password.$model"
                    :state="$v.form.password.$dirty ? !$v.form.password.$error : null"
                    aria-describedby="input-5-live-feedback"
                  ></b-form-input>

                  <b-form-invalid-feedback id="input-5-live-feedback">This is a required field.</b-form-invalid-feedback>
                </b-form-group>

                <b-form-group
                  id="example-input-group-6"
                  label="Confirm Password"
                  label-for="example-input-6"
                >
                  <b-form-input
                    id="example-input-6"
                    name="example-input-6"
                    type="password"
                    v-model="$v.form.password2.$model"
                    :state="$v.form.password2.$dirty ? !$v.form.password2.$error : null"
                    aria-describedby="input-6-live-feedback"
                  ></b-form-input>
                  <b-form-invalid-feedback id="input-6-live-feedback">This is a required field.</b-form-invalid-feedback>
                </b-form-group>

                <b-form-group
                  id="example-input-group-7"
                  label="Date of birth"
                  label-for="example-input-7">

                  <b-form-input
                    type="date"
                    id="example-input-7"
                    name="example-input-7"
                    v-model="$v.form.dofb.$model"
                    :state="$v.form.dofb.$dirty ? !$v.form.dofb.$error : null"
                    aria-describedby="input-7-live-feedback"
                    placeholder="Enter the users date of birth">
                </b-form-input>

                  <b-form-invalid-feedback id="input-7-live-feedback">This is a required field.</b-form-invalid-feedback>
                </b-form-group>
                <b-form-group
                  id="example-input-group-7"
                  label="Car Registration"
                  label-for="example-input-7">
                  <b-form-input
                    placeholder="Number plate"
                    aria-label="Number plate"
                    v-model="$v.form.carreg.$model"
                    :state="$v.form.carreg.$dirty ? !$v.form.carreg.$error : null">
                  </b-form-input>
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
import { required, minLength, maxLength, email, sameAs } from 'vuelidate/lib/validators'

export default {
  name: 'Register',
  mixins: [validationMixin],
  data () {
    return {
      submitted: false,
      form: {
        username: null,
        firstname: null,
        lastname: null,
        password: null,
        password2: null,
        email: null,
        dofb: null,
        carreg: null
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
      password: {
        required,
        minLength: minLength(6),
        maxLength: maxLength(20)
      },
      password2: {
        required,
        minLength: minLength(6),
        maxLength: maxLength(20),
        sameAsPassword: sameAs('password')
      },
      dofb: {
        required
      },
      carreg: {
        required
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
        password: this.form.password,
        password2: this.form.password2,
        dofb: this.form.dofb,
        roleName: 'USER',
        carreg: this.form.carreg
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
