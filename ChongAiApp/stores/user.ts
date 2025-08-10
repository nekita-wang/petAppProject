import { defineStore } from "pinia";
import { ref } from "vue";

interface UserData {
  token?: string;
  userId?: string;
  phone?: string;
}

export const useUserStore = defineStore("userStore", {
  state: (): UserData => {
    return {
      token: "",
      userId: "",
      phone: "",
    };
  },
  actions: {
    setUserInfo(userData: UserData): void {
      this.$patch(
        Object.fromEntries(
          Object.entries(userData).filter(([_, value]) => value !== undefined)
        )
      );
      uni.setStorageSync("userInfo", JSON.stringify(this.$state));
    },
  },
});
