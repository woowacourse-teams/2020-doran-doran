<template>
  <v-app>
    <v-navigation-drawer class="doran-doran-color" v-model="drawer" app clipped>
      <v-list>
        <v-list-item
          v-for="navigator in navigators"
          :key="navigator.title"
          :to="navigator.to"
        >
          <v-list-item-action>
            <v-icon> {{ navigator.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-title> {{ navigator.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar class="doran-doran-color" app clipped-left height="60px">
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>도란도란</v-toolbar-title>
      <v-spacer></v-spacer>
    </v-app-bar>
  </v-app>
</template>

<script>
export default {
  name: "App",
  data: () => {
    return {
      drawer: null,
      navigators: [
        { to: "/", title: "Home", icon: "mdi-account-box" },
        { to: "/manage-report", title: "신고 관리", icon: "mdi-bell" },
      ],
      isLogin: false,
    };
  },
};
</script>
