<template>
  <v-list dense>
    <v-list-tile v-for="item in items" :key="item.text">
      <v-list-tile-action>
        <v-icon>{{ item.icon }}</v-icon>
      </v-list-tile-action>
      <v-list-tile-content>
        <v-list-tile-title @click="onItemSelected">
          {{ item.text }}
        </v-list-tile-title>
      </v-list-tile-content>
    </v-list-tile>
  </v-list>
</template>

<script>
  export default {
    name: 'SideBarMenu',
    methods: {
      onItemSelected: function (event) {
        var innerText = event.target.innerHTML.trim();
        if (innerText == "Currencies") {
          this.$emit("navToCurrency");
        }
        else {
          console.log("Invalid element clicked!");
          console.log(innerText);
        }
      }
    },
    data: () => ({
      items: [
        { icon: 'trending_up', text: 'Currencies', onClick:'onCurrency' }
      ]
    })
  }
</script>
