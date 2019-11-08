<template>
  <!-- <h1>test</h1> -->
  <v-date-picker
    v-model='date'
  />
</template>
<script>
// import PostsService from '@/services/PostsService'
export default {
  name: 'DatePicker',
  data () {
    return {
      date: new Date()
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
