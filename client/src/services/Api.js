import axios from 'axios'

// export default() => {
//   console.log('INSIDE AXIOS CREATION')
//   return axios.create({
//     baseURL: 'http://localhost:8081'
//   })
// }

// export const Api = () => axios.create({
//   // console.log('INSIDE AXIOS CREATION')
//   baseURL: 'http://localhost:8081'
// })

const Api = axios.create({
  // console.log('INSIDE AXIOS CREATION')
  baseURL: 'http://localhost:8090'
})

// Interceptors add token with every server request.
Api.interceptors.request.use(
  config => {
    // if (config.baseURL === baseApiAddress && !config.headers.Authorization) {
  // const token = getToken();

    // if (token) {
    console.log('INTERCEPTORS')
    console.log('Token: ' + localStorage.getItem('jwt'))
    config.headers.Authorization = localStorage.getItem('jwt')
    // }
    // }

    return config
  }
  // error => Promise.reject(error)
)

// Api.interceptors.response.use(function (response) {
//   console.log('status ' + response.status + ' ' + response.data.errorContent)
// })

// window.axios = axiosInstance
export default Api
