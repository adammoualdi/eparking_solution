<template>
<div id = "ProfileWrapper">
    <div class="Nav">
        <NavigationBar class="nav"> </NavigationBar>
    </div>
    <div class="profileInfoWrapper">
        <b-container class ="wrapper">
            <b-row align-h="center" class="mt-5">
                <b-col cols="10">
                    <b-form @submit="onSubmit">
                        <b-form-group id="usernameInputGroup1"
                                      label-for="usernameInput1">
                            <b-form-input id="usernameInput1"
                                          required
                                          placeholder="Enter username">
                            </b-form-input>
                        </b-form-group>
                        <b-form-group id="passwordInputGroup2"
                                      label-for="passwordInput2">
                            <b-form-input id="passwordInput2"
                                          type="password"
                                          required
                                          placeholder="Enter password">
                            </b-form-input>
                        </b-form-group>
                    </b-form>
                </b-col>
            </b-row>
        </b-container>
    </div>
</div>
</template>

<script>
import NavigationBar from '@/components/PARKINGUSER/NavigationBar'
export default {
  name: 'ProfileEdit',
  components: {
    NavigationBar
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

.profileInfoWrapper {
    /* display: block; */
    height: 100%;
    /* position: absolute; */
    width: calc(100% - 50px);
    margin-left: 50px;
}

/* .profileContainer { */
    /* width: calc(100% - 50px); */
    /* margin-left: 50px; */
    /* box-sizing: border-bo    x; */
/* } */

</style>
