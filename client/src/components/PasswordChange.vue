<template>
  <div class="registerWrapper">
    <div class="headerNav">
      <NavigationBarReg></NavigationBarReg>
    </div>
    <b-container class="wrapper">
      <b-row align-h="center">
        <b-col cols="10">
          <b-card class="p-3">
            <h3 class="mb-4">Change password</h3>
            <div>
              <b-form @submit.stop.prevent="onSubmit">
                <!-- <b-form-group id="example-input-group-1" label="Password" label-for="example-input-1">
                  <b-form-input
                    id="example-input-1"
                    name="example-input-1"
                    type="password"
                    v-model="$v.form.password.$model"
                    :state="$v.form.password.$dirty ? !$v.form.password.$error : null"
                    aria-describedby="input-1-live-feedback"
                  ></b-form-input> -->

                  <!-- <div v-if="errorPassword"> -->
                    <!-- <b-form-invalid-feedback -->
                  <!-- <p class="errorPassword" v-show="errorPassword">This password doesn't match the previous password.</p> -->
                    <!-- </b-form-invalid-feedback> -->
                  <!-- </div> -->

                  <!-- <b-form-invalid-feedback
                    id="input-1-live-feedback"
                  >This is a required field and must be at least 6 characters.</b-form-invalid-feedback>
                </b-form-group> -->

                <b-form-group id="example-input-group-2" label="New password" label-for="example-input-2">
                  <b-form-input
                    id="example-input-2"
                    name="example-input-2"
                    type="password"
                    v-model="$v.form.newPassword.$model"
                    :state="$v.form.newPassword.$dirty ? !$v.form.newPassword.$error : null"
                    aria-describedby="input-2-live-feedback"
                  ></b-form-input>

                  <b-form-invalid-feedback id="input-2-live-feedback">This is a required field.</b-form-invalid-feedback>
                </b-form-group>

                <b-form-group
                  id="example-input-group-3"
                  label="Confirm password"
                  label-for="example-input-3"
                >
                  <b-form-input
                    id="example-input-3"
                    name="example-input-3"
                    type="password"
                    v-model="$v.form.confirmPassword.$model"
                    :state="$v.form.confirmPassword.$dirty ? !$v.form.confirmPassword.$error : null"
                    aria-describedby="input-3-live-feedback"
                  ></b-form-input>

                  <b-form-invalid-feedback id="input-3-live-feedback">This is a required field.</b-form-invalid-feedback>
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
import { required, minLength, maxLength, sameAs } from 'vuelidate/lib/validators'
import swal from 'sweetalert'

export default {
  name: 'PasswordChange',
  mixins: [validationMixin],
  data () {
    return {
      submitted: false,
      form: {
        password: null,
        newPassword: null,
        confirmPassword: null
      },
      errorPassword: false
    }
  },
  components: {
    NavigationBarReg
  },
  validations: {
    form: {
      newPassword: {
        required,
        minLength: minLength(6),
        maxLength: maxLength(20)
      },
      confirmPassword: {
        required,
        minLength: minLength(6),
        maxLength: maxLength(20),
        sameAsPassword: sameAs('newPassword')
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
      const response = await PostsService.changePassword({
        // password: this.form.password,
        newPassword: this.form.newPassword,
        confirmPassword: this.confirmPassword
      }).catch(function (data) {
        console.log(data)
      })
      console.log(response.data.errorContent)
      if (response.data.errorContent === undefined) {
        const that = this
        swal('Password changed!', 'Your password has been successfully changed', 'success')
          .then(function () {
            console.log('GO TO')
            if (localStorage.getItem('role') === 'Security') {
              that.$router.push({ name: 'SecurityOverview' })
            }
          })
      } else {
        console.log('ERROR')
        // this.$v.form.password.$error = true
        this.errorPassword = true
      }
      // console.log(response.data.errorContent)
      // console.log('Error ' + this.error)
      // console.log('RESPONSE ' + response.data.statusCode)
      // this.$router.push({ name: 'Login' })
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

.errorPassword {
  color: red;
}
</style>
