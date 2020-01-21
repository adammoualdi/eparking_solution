export default {
  dateConverter (dateTime) {
    var date = dateTime.substring(0, 10)
    // var time = dateTime.substring(11, 16)
    // var ret = date + ' ' + time
    return date
  },

  timeConverter (dateTime) {
    var time = dateTime.substring(11, 16)
    return time
  }
}
