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

// Interceptors add token with every server request unless not required.
Api.interceptors.request.use(
  config => {
    // if (config.baseURL === baseApiAddress && !config.headers.Authorization) {
  // const token = getToken();
    console.log(config)
    if (config.url === 'authenticate' || config.url === 'register') {
      console.log('DONT NEED AUTH TOKEN')
    } else {
      console.log('IN IF STATEMENT FOR ' + config.url)
      console.log('INTERCEPTORS')
      console.log('Token: ' + localStorage.getItem('jwt'))
      config.headers.Authorization = 'Bearer ' + localStorage.getItem('jwt')
    }
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
