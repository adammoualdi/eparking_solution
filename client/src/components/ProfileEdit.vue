<template>
<div id = "ProfileWrapper">
    <div class="Nav">
        <NavBar class="nav"> </NavBar>
    </div>
    <div class="profileInfoWrapper">
        <b-container class ="wrapper">
            <b-row align-h="center" class="mt-5">
                <b-col cols="10">
                    <!-- <b-card class="p-3"> -->
                    <b-form @submit="onSubmit">
                        <b-form-group id="usernameInputGroup1"
                                label-for="usernameInput1"
                                >
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

                <!-- <b-form-group id="rememberMeGroup4">
                    <b-form-checkbox-group id="rememberMeChecks">
                    <b-form-checkbox value="remember">Remember me</b-form-checkbox>
                    </b-form-checkbox-group>
                </b-form-group>
                <div class="d-flex justify-content-between">
                    <div>
                    <b-button type="submit" variant="primary">Submit</b-button>&nbsp;
                    <b-button type="reset" variant="danger">Reset</b-button>
                    </div>
                    <div>
                    <a href="register" v-b-modal.modal1>Forgot Password</a>
                    </div>
                </div> -->
                </b-form>
                    <!-- </b-card> -->
            </b-col>
            </b-row>
        </b-container>
    </div>
</div>
</template>

<script>
import NavBar from '@/components/NavBar'
export default {
  name: 'ProfileEdit',
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
