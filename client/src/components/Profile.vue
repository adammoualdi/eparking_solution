<template>
<div id = "ProfileWrapper">
    <div class="Nav">
        <NavBar class="nav"> </NavBar>
    </div>
    <div class="ProfileInfoWrapper">
        <b-container class ="wrapper">
            <div>
                <b-card no-body v-if="show">
                    <b-tabs card>
                        <b-tab title="User" active>
                            <b-card-text>{{ profile.username }}</b-card-text>
                            <b-card-text>{{ profile.email }} </b-card-text>
                            <b-card-text>{{ profile.firstname }} </b-card-text>
                            <b-card-text>{{ profile.lastname }} </b-card-text>
                            <b-card-text>Date of birth </b-card-text>
                            <!-- <b-card-text>: </b-card-text> -->
                        </b-tab>
                        <b-tab title="Car" active>
                          <div class="CarBox" v-for="(car, index) in profile.cars.cars" :key="index">
                            <b-card-text>{{ car.regNo }}</b-card-text>
                            <b-card-text>{{ car.model }}</b-card-text>
                          </div>
                        </b-tab>
                    </b-tabs>
                </b-card>
            </div>
        </b-container>
    </div>
</div>
</template>

<script>
import PostsService from '@/services/PostsService'
import NavBar from '@/components/NavBar'
export default {
  name: 'Profile',
  components: {
    NavBar
  },
  data () {
    return {
      profile: null,
      show: false
    }
  },
  mounted () {
    this.getDetails()
  },
  methods: {
    async getDetails () {
      var username = window.localStorage.getItem('username')
      console.log(username)
      const response = await PostsService.getUserDetails(username)
      console.log(response.data.username)
      this.profile = response.data
      console.log('profile info: ' + this.profile)
      this.show = true
    }
  }
}
</script>

<style>
/* .profileWrapper {
} */

.ProfileInfoWrapper {
    /* display: block; */
    height: 100%;
    /* position: absolute; */
    width: calc(100% - 50px);
    margin-left: 50px;
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

</style>
