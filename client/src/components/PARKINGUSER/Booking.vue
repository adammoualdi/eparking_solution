<template>
  <div id = "BookingWrapper">
    <div class="Nav">
      <NavigationBar class="nav"> </NavigationBar>
    </div>
    <div v-if="isMounted">
      <div class="BookingInfoWrapper">
        <div class="BookingContents">
          <b-container class ="wrapper">
            <div>
              <b-card no-body>
                <div class="BookingInfo">
                  <b-card-text>
                    <h2> {{ location.address1 + ', ' + location.address2 }} </h2>
                  </b-card-text>
                  <b-card-text>
                    <h4>{{ location.country + ', ' + location.city + ', ' + location.postcode}} </h4>
                    </b-card-text>
                  <!-- <b-card-text>{{ location.postcode }} </b-card-text> -->
                  <!-- <b-card-text>{{ userQuery.aTime + ' -> ' + userQuery.lTime }} </b-card-text> -->
                  <b-card-text>
                    <div class="DateTimeInfo">
                      <div class="ArriveDateTime">
                        <h3>
                          {{ dateFunc(location.arriveTime) }} <br>
                          {{ timeFunc(location.arriveTime) }}
                        </h3>
                      </div>
                      <div class="Arrow">
                        <h1>&rArr;</h1>
                      </div>
                      <div class="LeaveDateTime">
                        <h3>
                          {{ dateFunc(location.leavingTime) }} <br>
                          {{ timeFunc(location.leavingTime) }}
                        </h3>
                      </div>
                    </div>
                  </b-card-text>
                  <b-card-text>
                    <h4> TOTAL: </h4>
                    <h5> £{{ location.deposit }} + £{{ location.depositFee }}</h5>
                    £{{ location.depositFee }} will not be taken out of your account unless there's an issue.
                  </b-card-text>
                  <b-card-text>
                    Please select what car you'll be using?
                  </b-card-text>
                  <b-card-text>
                      <div class="SelectCarIcon">
                        <font-awesome-icon icon="car" size="2x"> </font-awesome-icon>
                      </div>
                      <div class="SelectCar">
                        <b-form-select v-model="carSelected" :options="carOptions" :select-size="1" class="Select"></b-form-select>
                      </div>
                  </b-card-text>
                    <!-- <font-awesome-icon icon="money-bill-alt" size="2x"> </font-awesome-icon> -->
                    <!-- <h4> Cost: </h4> -->
                  <b-card-text>
                    <button class="SubmitButton" type="button" name="button" v-on:click="saveBooking">BOOK</button>
                  </b-card-text>
                </div>
              </b-card>
            </div>
          </b-container>
        </div>
      </div>
    </div>
  <div v-else class="Loading">
    <SemipolarSpinner
      :animation-duration="2000"
      :size="200"
      color="#000000"
    />
  </div>
  </div>
</template>

<script>
import DateConverter from '@/services/DateConvert'
import PostsService from '@/services/PostsService'
import NavigationBar from '@/components/PARKINGUSER/NavigationBar'
import swal from 'sweetalert'
import {SemipolarSpinner} from 'epic-spinners'
export default {
  name: 'Booking',
  components: {
    NavigationBar,
    SemipolarSpinner
  },
  data () {
    return {
      userQuery: {},
      location: null,
      selected: null,
      userInfo: null,
      isMounted: false,
      carOptions: [
        { value: null, text: 'Please select an option' }
      ],
      carSelected: []
    }
  },
  created () {
    // console.log(this.$route.params.times)
    if (this.$route.params.location === undefined) {
      console.log('ERROR')
      this.$router.push({ name: 'Landing' })
    }
    this.location = this.$route.params.location
    // this.userQuery = this.$route.params.times
  },
  mounted () {
    this.getCars()
  },
  methods: {
    async getInfo () {
    },
    async saveBooking () {
      console.log(this.location)
      // console.log(this.userQuery)
      var uname = window.localStorage.getItem('username')
      console.log(this.carSelected)
      const response = await PostsService.bookParkingSlot({
        id: 1,
        locationId: {
          locationId: this.location.locationId,
          country: this.location.country,
          city: this.location.city,
          address1: this.location.address1,
          address2: this.location.address2,
          postcode: this.location.postcode
        },
        username: uname,
        // startDate: this.userQuery.aTime,
        // endDate: this.userQuery.lTime,
        startDate: this.location.arriveTime,
        endDate: this.location.leavingTime,
        active: true,
        car: this.carSelected
      })
      console.log(response.data.deposit)
      const that = this
      if (response.data.deposit !== undefined) {
        window.localStorage.setItem('deposit', response.data.deposit)
        // NEED TO CHECK IF ERROR
        swal('Booked!', 'Your booking has been successfully made', 'success')
          .then(function () {
            console.log('GO TO')
            that.$router.push({ name: 'Landing' })
          })
      } else if (response.data.errorContent === 'Exists') {
        swal('Unsuccessful!', `There's a booking clash with the car you picked`, 'error')
      } else {
        swal('Unsuccessful!', `You don't have enough credit for this transaction`, 'error')
          .then(function () {
            console.log('GO TO')
            that.$router.push({ name: 'Landing' })
          })
      // this.$router.push({ name: 'Landing' })
      }
    },
    async getCars () {
      var cars = []
      const response = await PostsService.getUserDetails(window.localStorage.getItem('username'))
      this.userInfo = response.data
      for (var i = 0; i < this.userInfo.cars.cars.length; i++) {
        var car = {value: this.userInfo.cars.cars[i], text: this.userInfo.cars.cars[i].model}
        console.log(car)
        cars.push(car)
      }
      this.carOptions = cars
      this.isMounted = true
    },
    dateFunc (dateTime) {
      return DateConverter.dateConverter(dateTime)
    },
    timeFunc (dateTime) {
      return DateConverter.timeConverter(dateTime)
    }
    // onChange: function (event) {
    //   this.carOptions = []
    //   for (var i = 0; i < this.userInfo.cars.cars.length; i++) {
    //     // var city = {value: this.locations[i].city, text: this.locations[i].city}
    //     var car = {value: this.userInfo[i].cars.cars[i], text: this.userInfo[i].cars.cars[i].model}
    //     this.carOptions.push(car)
    //   }
    // }
  }
}
</script>

<style type="text/css">

.BookingInfoWrapper {
    /* display: block; */
    height: 100%;
    /* position: absolute; */
    /* width: calc(100% - 50px); */
    width: 100%;
    /* margin-left: 50px; */
    padding-top: 60px;
}

.BookingWrapper {
    margin: 0px;
    padding: 0px;
}

.Image {
  width: 50%;
  height: 30vh;
  background-color: red;
}
@media (max-width: 450px) {
  .Image {
    width: 100%;
  }

  .BookingInfo {
    margin-left: 30px;
    margin-top: 20px;
    margin-right: 30px;
  }

  .container {
    padding: 0px;
  }
}

.swal-modal {
    /* margin-left: 50px; */
    /* width: calc(100% - 60px); */
    /* margin:10px; */
    /* width: 100%; */
}

.DateTimeInfo {
  width: 100%;
  /* float: left; */
  /* display: flex; */
}

.TimeInfo {
  font-size: 40px;
  /* float: left; */
  /* positio */
}

.ArriveDateTime {
  float: left;
  width: 45%;
  display: flex;
}

.LeaveDateTime {
  /* width: 45%; */
}

.Arrow {
  width: 40px;
  float: left;
}

.SelectCarIcon {
  float: left;
  width: 10%;
}

.SelectCar {
  float: left;
  width: 88%;
  margin-left: 2%;
}

.SubmitButton {
  margin-top: 10px;
  width: 100%;
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
}

</style>
