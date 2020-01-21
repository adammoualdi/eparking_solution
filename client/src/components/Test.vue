<template>
  <div id = "headerWrapper">
    <div class="test" v-if="$mq === 'mobile'">
      TEST
    </div>
    <nav>
      <ul>
        <li>
          <b-card title="Card title" sub-title="Card subtitle">
            <b-card-text>
            Some quick example text to build on the <em>card title</em> and make up the bulk of the card's
            content.
            </b-card-text>

            <b-card-text>A second paragraph of text in the card.</b-card-text>

            <a href="#" class="card-link">Card link</a>
            <b-link href="#" class="card-link">Another link</b-link>
          </b-card>
        </li>
        <li>
          <b-card title="Card title" sub-title="Card subtitle">
            <b-card-text>
            Some quick example text to build on the <em>card title</em> and make up the bulk of the card's
            content.
            </b-card-text>

            <b-card-text>A second paragraph of text in the card.</b-card-text>

            <a href="#" class="card-link">Card link</a>
            <b-link href="#" class="card-link">Another link</b-link>
          </b-card>
        </li>
        <li>
          <b-card title="Card title" sub-title="Card subtitle">
            <b-card-text>
            Some quick example text to build on the <em>card title</em> and make up the bulk of the card's
            content.
            </b-card-text>

            <b-card-text>A second paragraph of text in the card.</b-card-text>

            <a href="#" class="card-link">Card link</a>
            <b-link href="#" class="card-link">Another link</b-link>
          </b-card>
        </li>
        <li>
          <b-card title="Card title" sub-title="Card subtitle">
            <b-card-text>
            Some quick example text to build on the <em>card title</em> and make up the bulk of the card's
            content.
            </b-card-text>

            <b-card-text>A second paragraph of text in the card.</b-card-text>

            <a href="#" class="card-link">Card link</a>
            <b-link href="#" class="card-link">Another link</b-link>
          </b-card>
        </li>
        <li>Link 5</li>
        <li>Link 6</li>
        <li>Link 7</li>
        <li>Link 8</li>
        <li>Link 9</li>
        <li>Link 10</li>
        <li>Link 11</li>
        <li>Link 13</li>
        <li>Link 13</li>
        <li>Link 1</li>
        <li>Link 2</li>
        <li>Link 3</li>
        <li>Link 4</li>
        <li>Link 5</li>
        <li>Link 6</li>
        <li>Link 7</li>
        <li>Link 8</li>
        <li>Link 9</li>
        <li>Link 10</li>
        <li>Link 11</li>
        <li>Link 13</li>
        <li>Link 13</li>
        <li>Link 1</li>
        <li>Link 2</li>
        <li>Link 3</li>
        <li>Link 4</li>
        <li>Link 5</li>
        <li>Link 6</li>
        <li>Link 7</li>
        <li>Link 8</li>
        <li>Link 9</li>
        <li>Link 10</li>
        <li>Link 11</li>
        <li>Link 13</li>
        <li>Link 13</li>
        <li>Link 1</li>
        <li>Link 2</li>
        <li>Link 3</li>
        <li>Link 4</li>
        <li>Link 5</li>
        <li>Link 6</li>
        <li>Link 7</li>
        <li>Link 8</li>
        <li>Link 9</li>
        <li>Link 10</li>
        <li>Link 11</li>
        <li>Link 13</li>
        <li>Link 13</li>
      </ul>
    </nav>
  </div>
</template>

<script>
import DatePicker from '@/components/DatePicker'
export default {
  name: 'MapSearch',
  components: {
    DatePicker
  },
  data () {
    return {
      selected: null,
      location: null,
      arrivingTime: null,
      leavingTime: null,
      options: [
        { value: null, text: 'Please select a time' },
        { value: '00', text: '00:00' },
        { value: '01', text: '01:00' },
        { value: '02', text: '02:00' },
        { value: '03', text: '03:00' },
        { value: '04', text: '04:00' },
        { value: '05', text: '05:00' },
        { value: '06', text: '06:00' },
        { value: '07', text: '07:00' },
        { value: '08', text: '08:00' },
        { value: '09', text: '09:00' },
        { value: '10', text: '10:00' },
        { value: '11', text: '11:00' },
        { value: '12', text: '12:00' },
        { value: '13', text: '13:00' },
        { value: '14', text: '14:00' },
        { value: '15', text: '15:00' },
        { value: '16', text: '16:00' },
        { value: '17', text: '17:00' },
        { value: '18', text: '18:00' },
        { value: '19', text: '19:00' },
        { value: '20', text: '20:00' },
        { value: '21', text: '21:00' },
        { value: '22', text: '22:00' },
        { value: '23', text: '23:00' },
        { value: '24', text: '24:00' }
      ],
      range: {
        start: new Date(), // Jan 16th, 2018
        end: new Date() // Jan 19th, 2018
      }
    }
  }
}
</script>

<style>
html,
body {
  margin: 0;
  padding: 0;
  height: 100%;
}

* {
  margin: 0px;
  padding: 0px;
}

head {
  margin: 0px;
  padding: 0px;
}

.v-sidebar-menu {
  height: 100%;
  padding-top: 60px;
}

#row1 {
  background-color: red;
}

#row2 {
  background-color: green;
}

#row3 {
  background-color: yellow;
}

/* #headerWrapper {
  width: 100%;
  height: 60px;
  background-color: #2a2a2e;
  position: absolute;
  margin: 0px;
  padding: 0px;
  float: left;
} */

#mySidenav {
  padding: 0px;
  margin: 0px;
}

nav ul{height:100vh; width:18%;}
nav ul{overflow:hidden; overflow-y:scroll;}
</style>
