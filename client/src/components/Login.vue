<template>
    <b-container class ="wrapper">
    <b-row align-h="center" class="mt-5">
      <b-col cols="10">
        <b-card class="p-3">
          <h3 class="mb-4">Login</h3>
          <b-form @submit="onSubmit">
      <b-form-group id="usernameInputGroup1"
                    label="Username:"
                    label-for="usernameInput1"
                    >
        <b-form-input id="usernameInput1"
                      v-model="form.username"
                      required
                      placeholder="Enter username">
        </b-form-input>
      </b-form-group>
      <b-form-group id="passwordInputGroup2"
                    label="Password:"
                    label-for="passwordInput2">
        <b-form-input id="passwordInput2"
                      type="password"
                      v-model="form.password"
                      required
                      placeholder="Enter password">
        </b-form-input>
      </b-form-group>

      <b-form-group id="rememberMeGroup4">
        <b-form-checkbox-group v-model="form.checked" id="rememberMeChecks">
          <b-form-checkbox value="remember">Remember me</b-form-checkbox>
        </b-form-checkbox-group>
      </b-form-group>
      <div class="d-flex justify-content-between">
        <div>
          <b-button type="submit" variant="primary">Submit</b-button>&nbsp;
          <!-- <b-button type="reset" variant="danger">Reset</b-button> -->
        </div>
        <div>
          <a href="register" v-b-modal.modal1>Haven't got an account? Register here</a>
        </div>
      </div>
    </b-form>
    <p class="error" v-show="invalidLogin">Username and password are incorrect</p>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import PostsService from '@/services/PostsService'
// import Vue from 'vue'
// import Vue from 'vue'
// import VeeValidate from 'vee-validate'
// Vue.use(VeeValidate)
export default {
  data () {
    return {
      form: {
        email: '',
        username: '',
        password: '',
        checked: []
      },
      extErrors: [],
      // email: '',
      token: '',
      password: '',
      formSubmitted: false,
      invalidLogin: false,
      error: null
    }
  },
  methods: {
    // validateBeforeSubmit (e) {
    //   this.$validator.validateAll()
    //   if (!this.errors.any()) {
    //     this.submitForm()
    //   } else {
    //     console.log(this.errors.any())
    //   }
    // },
    onSubmit (evt) {
      evt.preventDefault()
      console.log('SUBMIT')
      this.login()
      // alert(JSON.stringify(this.form))
    },
    // submitForm () {
    //   console.log('FORM SUBMITTED')
    //   // this.formSubmitted = false
    //   this.login()
    // },
    async login () {
      this.extErrors = []
      console.log('--------------- username: ' + this.form.email)
      console.log('--------------- password: ' + this.form.password)
      const that = this
      const response = await PostsService.login({
        username: this.form.username,
        password: this.form.password
      }).catch(function () {
        that.error = 'ERROR'
      })
      // console.log("------------------ Passing login method")
      console.log(response)
      // if (response.data.success === true) {
      localStorage.removeItem('jwt')
      localStorage.removeItem('role')
      localStorage.removeItem('username')
      // console.log(console.log)
      if (response !== undefined) {
        console.log('check tok ' + response.data.token)
        window.localStorage.setItem('jwt', response.data.token)
        window.localStorage.setItem('role', response.data.role)
        window.localStorage.setItem('username', response.data.username)
        var tok = window.localStorage.getItem('jwt')
        var role = window.localStorage.getItem('role')
        var uname = window.localStorage.getItem('username')
        console.log('role: ' + role)
        console.log('token after login: ' + tok)
        console.log(uname)
        // console.log('LOGIN STATUS' + response.data.status)
        this.$router.push({ name: 'Landing' })
      } else {
        // console.log(response.data.errors)
        // console.log(response.data)
        // console.log(response.data.message)
        console.log('ERROR LOGGING IN')
        this.extErrors.push('Error logging in')
        this.invalidLogin = true
        // console.log(this.extErrors)
        // var error = response.data.errors[0]
        // console.log(error)
        // return error
      }
    }
  }
}
</script>
<style>
.error {
  color: red;
}
</style>
