import Api from '@/services/Api'

export default {

  login (params) {
  // get login details
    return Api.post('authenticate', params)
  },

  register (params) {
    return Api.post('register', params)
  },

  getLocations () {
    return Api.get('dashboard')
  },

  getAvailableLocations (params) {
    return Api.post('dashboard/available', params)
  },

  getUserDetails (params) {
    return Api.get('profile/' + params)
  },

  bookParkingSlot (params) {
    return Api.post('booking', params)
  },

  getUserBookings (params) {
    return Api.get('bookings/' + params)
  }

}
