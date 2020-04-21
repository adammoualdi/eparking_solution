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
  },

  getOwnerLocations () {
    return Api.get('locations/owners')
  },

  getAdminApprovals () {
    return Api.get('locations/admin')
  },

  setApproval (params) {
    return Api.post('locations/approve', params)
  },

  addLocation (params) {
    return Api.post('locations/add', params)
  },

  addImage (params) {
    return Api.post('locations/upload', params)
  },

  changePassword (params) {
    return Api.post('authenticate/changepassword', params)
  },

  getLocationBookings (params) {
    return Api.post('locations/bookings', params)
  },

  purchasePoints (params) {
    return Api.post('dashboard/purchase', params)
  },

  getSecurityBookings (params) {
    return Api.post('dashboard/security/bookings', params)
  },

  getUserInfo () {
    return Api.post('profile/user')
  },

  confirmParking (params) {
    return Api.post('booking/confirmation/' + params)
  },

  securityUpdate () {
    return Api.get('/dashboard/security/bookings/update')
  },

  reportIssue (params) {
    return Api.post('/booking/issue', params)
  },

  confirmIssue (params) {
    return Api.post('/dashboard/security/bookings/issue', params)
  },

  getSecurityInfo (params) {
    return Api.post('/locations/security', params)
  },

  removeSecurityLocation (param1, param2) {
    return Api.post('/locations/security/remove/' + param1, param2)
  },
  addSecurityLocation (param1, param2) {
    return Api.post('/locations/security/add/' + param1, param2)
  }

}
