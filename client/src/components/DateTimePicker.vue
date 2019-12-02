<template>
  <!-- <h1>test</h1> -->
    <v-datetime type="datetime" v-model="datetime" :minute-step=30></v-datetime>
</template>
<script>
// import PostsService from '@/services/PostsService'
import { Datetime } from 'vue-datetime'
export default {
  name: 'DateTimePicker',
  components: {
    'v-datetime': Datetime
  },
  data () {
    return {
      datetime: '',
      interval: 30
    }
  },
  mounted () {
    // this.getPosts()
  },
  methods: {
    // async getPosts () {
    //   const response = await PostsService.fetchPosts()
    //   this.posts = response.data
    // }
  }
}
</script>
