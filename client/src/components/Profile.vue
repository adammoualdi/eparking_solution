<template>
<div id = "ProfileWrapper">
    <div class="Nav">
        <NavBar class="nav"> </NavBar>
    </div>
    <div class="ProfileInfoWrapper">
        <b-container class ="wrapper">
            <div>
                <b-card no-body>
                    <b-tabs card>
                        <b-tab title="User" active>
                            <b-card-text>Username:       </b-card-text>
                            <b-card-text>Email: </b-card-text>
                            <b-card-text>First name: </b-card-text>
                            <b-card-text>Last name: </b-card-text>
                            <b-card-text>Date of birth: </b-card-text>
                            <!-- <b-card-text>: </b-card-text> -->
                        </b-tab>
                        <b-tab title="Car" active>
                            <b-card-text>Registration number:</b-card-text>
                            <b-card-text>Car model:</b-card-text>
                        </b-tab>
                    </b-tabs>
                </b-card>
            </div>
        </b-container>
    </div>
</div>
</template>

<script>
import NavBar from '@/components/NavBar'
export default {
  name: 'Profile',
  components: {
    NavBar
  },
  data () {
    return {
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
