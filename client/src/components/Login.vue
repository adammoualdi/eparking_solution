<template>
    <div class="LoginWrapper">
      <div class="headerNav">
        <NavigationBarReg></NavigationBarReg>
      </div>
      <b-container class ="wrapper">
      <b-row align-h="center">
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

        <!-- <b-form-group id="rememberMeGroup4">
          <b-form-checkbox-group v-model="form.checked" id="rememberMeChecks">
            <b-form-checkbox value="remember">Remember me</b-form-checkbox>
          </b-form-checkbox-group>
        </b-form-group> -->
        <div class="d-flex justify-content-between">
          <div>
            <b-button type="submit" variant="primary">Submit</b-button>&nbsp;
            <!-- <b-button type="reset" variant="danger">Reset</b-button> -->
          </div>
          <div>
            <a href="javascript:void(0)" v-on:click="changeRegisterVar" v-b-modal.modal1>Haven't got an account? Register here</a>
          </div>
        </div>
      </b-form>
      <p class="error" v-show="invalidLogin">Username and password are incorrect</p>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
    <div v-if=registerChoice class="RegisterChoices">
      <div class="RegisterChoiceInfo">
        <b-container class="OptionsContainer">
          <b-row align-v="start">
              <b-col>
                <!-- <h2>What service do you need?</h2> -->
              </b-col>
          </b-row>
          <b-row align-v="center">
              <b-col>
                <button class="searchBtn" type="button" name="button" v-on:click="userRegister">Parking User</button>
              </b-col>
              <b-col>
                <button class="searchBtn" type="button" name="button" v-on:click="ownerRegister">Parking Owner</button>
              </b-col>
          </b-row>
        </b-container>
      </div>
    </div>
  </div>
</template>

<script>
import PostsService from '@/services/PostsService'
import NavigationBarReg from '@/components/NavigationBarReg'
// import Vue from 'vue'
// import Vue from 'vue'
// import VeeValidate from 'vee-validate'
// Vue.use(VeeValidate)
export default {
  data () {
    return {
      form: {
        username: '',
        password: '',
        checked: []
      },
      registerChoice: false,
      extErrors: [],
      token: '',
      formSubmitted: false,
      invalidLogin: false,
      error: null,
      response: null
    }
  },
  components: {
    NavigationBarReg
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
      console.log('--------------- password: ' + this.form.username)
      console.log('--------------- password: ' + this.form.password)
      const that = this
      this.response = await PostsService.login({
        username: this.form.username,
        password: this.form.password
      }).catch(function () {
        that.error = 'ERROR'
      })
      // console.log(this.response.data)

      // Remove local storage items if they already exist in browser
      localStorage.removeItem('jwt')
      localStorage.removeItem('role')
      localStorage.removeItem('username')
      localStorage.removeItem('deposit')

      // Check if response error from API login
      if (this.response !== undefined) {
        console.log('check tok ' + this.response.data.token)

        // Set items in local storage for later use
        window.localStorage.setItem('jwt', this.response.data.token)
        window.localStorage.setItem('role', this.response.data.role)
        window.localStorage.setItem('username', this.response.data.username)
        window.localStorage.setItem('deposit', this.response.data.deposit)
        this.route()
        // var tok = window.localStorage.getItem('jwt')
        // var role = window.localStorage.getItem('role')
        // var uname = window.localStorage.getItem('username')
        // console.log('role: ' + role)
        // console.log('token after login: ' + tok)
        // console.log(uname)
        // console.log('LOGIN STATUS' + response.data.status)
      }
    },
    route () {
      // Check what user route to persue
      if (this.response.data.role === 'User') {
        this.$router.push({ name: 'Landing' })
      } else if (this.response.data.role === 'Owner') {
        this.$router.push({ name: 'OwnerLanding' })
      } else if (this.response.data.role === 'Admin') {
        this.$router.push({ name: 'AdminApproval' })
      } else if (this.response.data.role === 'Security') {
        console.log(this.response.data.defaultPassword)
        // If first time security user has logged in
        if (this.response.data.defaultPassword === true) {
          this.$router.push({ name: 'PasswordChange' })
        } else {
          this.$router.push({ name: 'SecurityOverview' })
        }
      }
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
    },
    changeRegisterVar () {
      this.registerChoice = true
      console.log(this.registerChoice)
    },
    userRegister () {
      this.$router.push({ name: 'Register' })
    },
    ownerRegister () {
      this.$router.push({ name: 'OwnerRegister' })
    }
  }
}
</script>
<style>
.error {
  color: red;
}

.wrapper {
  padding-top: 80px;
}

.RegisterChoices {
  position: absolute;
  top: 0px;
  z-index: 99;
  height: 100vh;
  width: 100vw;
  /* margin-left: calc(100vw - 80px); */
  background-color:rgba(128,128,128,0.8);
}

.RegisterChoiceInfo {
  /* opacity: 50%; */
  background: white;
  padding: 10px;
  z-index: 9999;
  padding-top: 100px;
  height: 200px;
  width: 400px;
  /* font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif */
  /* position: absolute; */
  /* margin-left: calc(100vw - 80px); */
  /* padding-top: 100px; */
  /* top: 40%; */
  text-align: center;
  display: inline-block;
  /* left: 50vw; */
  /* padding-left: 50vw; */
  /* -webkit-transform: translate(-50%, -50%); */
  /* transform: translate(-50%, -50%); */
}

.OptionsContainer {
  text-align: center;
}

.bv-example-row {
  max-width: 500px;
  width: 100%;
  text-align: center;
}

.RegisterChoiceInfo h2 {
  /* font-family: 'Kulim Park', sans-serif; */
  /* font-family: 'Karla', sans-serif; */
  text-align: center;
}

.searchBtn {
  width: 100%;
  background-color: #4CAF50;
  border: none;
  color: white;
  font-family: 'Karla', sans-serif;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  /* border-color: black; */
  /* border-width: 1px; */
}

.col {
  padding-right: 2px;
  padding-left: 2px;
}

</style>
