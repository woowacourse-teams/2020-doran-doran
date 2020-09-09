<template>
  <v-app>
    <v-app-bar app clipped-left height="60px">
      <VApp-bar-nav-icon @click.stop="drawer = !drawer" />
      <v-toolbar-title> 도란도란 </v-toolbar-title>
      <VSpacer />
    </v-app-bar>

    <v-navigation-drawer v-model="drawer" app clipped>
      <v-list>
        <v-list-item
          v-for="navigator in navigators"
          :key="navigator.title"
          :to="navigator.to"
        >
          <v-list-item-action>
            <v-icon> {{ navigator.icon }} </v-icon>
          </v-list-item-action>
          <v-list-item-title> {{ navigator.title }} </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-main>
      <RouterView />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",
  data: () => {
    return {
      drawer: true,
      navigators: [
        {
          to: "/",
          title: "Home",
          icon: "mdi-home",
        },
        {
          to: "/reports",
          title: "신고 관리",
          icon: "mdi-bell",
        },
      ],
    };
  },
};
</script>
