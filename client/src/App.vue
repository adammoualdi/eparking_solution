<template>
  <div id="app">
     <!-- APP TEST -->
    <!-- <img src="./assets/logo.png"> -->
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'nodeApp'
}
</script>

<style>
@import url('https://fonts.googleapis.com/css?family=Roboto+Condensed');
@import url('https://fonts.googleapis.com/css?family=Kulim+Park&display=swap');
@import url('https://fonts.googleapis.com/css?family=Karla&display=swap');

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  background: #f5f5f5;
  /* color: #2c3e50; */
  min-height:100vh;
  /* margin-top: 60px; */
}
</style>
