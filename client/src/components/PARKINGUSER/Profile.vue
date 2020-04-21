<template>
<div id = "ProfileWrapper">
    <div class="Nav">
        <NavigationBar class="nav"> </NavigationBar>
    </div>
    <div class="ProfileInfoWrapper">
        <b-container class ="wrapper">
            <div>
                <b-card v-if="show">
                  <b-form class = "Form" @submit.stop.prevent="onSubmit">
                    <!-- <b-tabs card> -->
                      <b-form-group id="usernameInputGroup1"
                                    label="Username"
                                      label-for="usernameInput1">
                        <b-form-input id="usernameInput1"
                                      class="FormInput"
                                      required
                                      :disabled="true"
                                      v-model="profile.username"
                                      placeholder="">
                        </b-form-input>
                      </b-form-group>
                      <b-form-group id="EmailInputGroup2"
                                    label="Email"
                                    label-for="passwordInput2">
                        <b-form-input id="emailInput"
                                      class="FormInput"
                                      required
                                      :disabled="edit == false"
                                      v-model="profile.email"
                                      placeholder="">
                        </b-form-input>
                      </b-form-group>
                      <b-form-group id="firstnameInputGroup"
                                    label="Firstname"
                                    label-for="firstnameInputGroup">
                        <b-form-input id="firstnameInput"
                                      required
                                      :disabled="edit == false"
                                      v-model="profile.firstname"
                                      placeholder="">
                        </b-form-input>
                      </b-form-group>
                      <b-form-group id="lastnameInputGroup"
                                    label="Lastname"
                                    label-for="lastnameInputGroup">
                        <b-form-input id="lastnameInput"
                                      class="FormInput"
                                      required
                                      :disabled="edit == false"
                                      v-model="profile.lastname"
                                      placeholder="">
                        </b-form-input>
                      </b-form-group>
                      <b-form-group id="dofbInputGroup"
                                    label="Date of birth"
                                    label-for="dofbInputGroup">
                        <b-form-input id="dofbInput"
                                      type="date"
                                      class="FormInput"
                                      required
                                      :disabled="edit == false"
                                      v-model="profile.dofb"
                                      placeholder="">
                        </b-form-input>
                      </b-form-group>
                      <div class="CarHeader">
                        <div class="CarTitle">
                          <h3>Cars</h3>
                        </div>
                        <div class="AddCarSection" v-if="edit == true">
                          <b-button type="button" name="button" v-b-modal.addCar>Add car</b-button>
                        </div>
                      </div>
                      <div class="table-wrap">
                        <table>
                            <tr>
                            <td width="100%">Registration number</td>
                            <td width="100%" align="center" v-if="edit == true">Action</td>
                            </tr>
                            <tr v-for="(car, index) in activeCars" :key="index" class="border_bottom" >
                            <td>{{ car.regNo }}
                            </td>
                            <td align="center">
                                <!-- <router-link v-bind:to="{ name: 'EditPost', params: { id: post._id } }">Edit</router-link> | -->
                                <a href="javascript:void(0)" @click="removeCar(car)" v-if="edit == true">Remove</a>
                            </td>
                            </tr>
                        </table>
                      </div>
                      <div class="Buttons">
                        <div v-if="edit == false">
                          <b-button type="button" name="button" v-on:click="editDetails">Edit</b-button>
                        </div>
                        <div v-else>
                          <b-button type="button" name="button" v-on:click="editDetails">Submit</b-button>
                        </div>
                      </div>
                        <!-- <b-tab title="User" active>
                            <b-card-text>{{ profile.username }}</b-card-text>
                            <b-card-text>{{ profile.email }} </b-card-text>
                            <b-card-text>{{ profile.firstname }} </b-card-text>
                            <b-card-text>{{ profile.lastname }} </b-card-text>
                            <b-card-text>Date of birth </b-card-text> -->
                            <!-- <b-card-text>: </b-card-text> -->
                        <!-- </b-tab> -->
                        <!-- <b-tab title="Car" active>
                          <div class="CarBox" v-for="(car, index) in profile.cars.cars" :key="index">
                            <b-card-text>{{ car.regNo }}</b-card-text>
                            <b-card-text>{{ car.model }}</b-card-text>
                          </div>
                        </b-tab>
                    </b-tabs> -->
                  </b-form>
                </b-card>
            </div>
        </b-container>
    </div>
    <b-modal
          id="addCar"
          ref="addCar"
          title="Add a car"
          @ok="handleOk"
          modal-footer=hide>
            <p>Enter the registration of the car you want to add.</p>
            <form ref="form" @submit.stop.prevent="handleSubmit">
                <b-input-group>
                  <b-form-input v-model="newCarReg"></b-form-input>
                </b-input-group>
            </form>
        </b-modal>
</div>
</template>

<script>
import PostsService from '@/services/PostsService'
import NavigationBar from '@/components/PARKINGUSER/NavigationBar'
import {SemipolarSpinner} from 'epic-spinners'
export default {
  name: 'Profile',
  components: {
    NavigationBar,
    SemipolarSpinner
  },
  data () {
    return {
      profile: null,
      show: false,
      edit: false,
      newCarReg: '',
      cars: []
    }
  },
  mounted () {
    this.getDetails()
  },
  computed: {
    activeCars: function () {
      return this.profile.cars.cars.filter(function (car) {
        return !car.remove
      })
    }
  },
  methods: {
    async getDetails () {
      var username = window.localStorage.getItem('username')
      console.log(username)
      const response = await PostsService.getUserDetails(username)
      console.log(response.data.username)
      this.profile = response.data
      console.log(this.profile)
      this.show = true
    },
    editDetails () {
      this.edit = true
    },
    onSubmit () {
    },
    handleOk () {
      var obj = {id: 0, regNo: this.newCarReg, model: '', remove: false, add: true}
      this.profile.cars.cars.push(obj)
    },
    removeCar (car) {
      for (var i = 0; i < this.profile.cars.cars.length; i++) {
        // console.log(car.id)
        // console.log(this.profile.cars.cars[i].id)
        if (this.profile.cars.cars[i].id === car.id) {
          this.profile.cars.cars.remove = true
          // this.profile.cars.cars.splice(i, i+1)
          break
        }
      }
    }
  }
}
</script>

<style>
/* .profileWrapper {
} */

.wrapper {
  padding-top: 0px;
}

.Form {
  border: none;
  /* background: white; */
}

.form-control:disabled {
  background: none;
  border: 0;
  border-radius: 0;
  border-bottom: 1px solid grey;
}

.FormInput {
  /* border: none; */
  /* background: white; */
}

.ProfileInfoWrapper {
    /* display: block; */
    height: 100%;
    /* position: absolute; */
    /* width: calc(100% - 50px); */
    width: 100%;
    /* margin-left: 50px; */
    padding-top: 60px;
}

.ProfileWrapper {
    margin: 0px;
    padding: 0px;
}

/* .profileContainer { */
    /* width: calc(100% - 50px); */
    /* margin-left: 50px; */
    /* box-sizing: border-bo    x; */
/* } */

table {
    border-radius: 50px;
}

.table-wrap {
    width: 100%;
    /* width: 100%; */
    padding: 5px;
    margin: 0 auto;
    text-align: center;
    display: inline-block;
    /* border-radius: 100px; */
}
table th, table tr {
    text-align: left;
    height: 50px;
    padding-left: 5px;
    /* padding: 0px; */

}
table thead {
    background: #f2f2f2;
}
table tr td {
    width: 100%;
    padding-top: 0px;
}

tr.border_bottom td {
  border-bottom:1pt solid black;
  /* padding-top: 20px; */
}

/* td {
    background: rgb(255, 255, 255);
} */
table tr:nth-child(odd) {
    background: #f2f2f2;
}
table tr:nth-child(1) {
    background: #4d7ef7;
    color: #fff;
}

.CarHeader {
  width: 100%;
}

.CarTitle {
  width: 70%;
  display: block;
  float: left;
}

.AddCarSection {
  width: 25%;
  float: right;
  display: block;
}

.Buttons {
  width: 100%;
}

#addCar {
  margin-top: 100px;
}

</style>
