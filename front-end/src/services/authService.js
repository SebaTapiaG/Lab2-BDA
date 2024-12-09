import { reactive } from "vue";
 

class AuthService {
  constructor() {
    // Reactive state
    this.state = reactive({
      userId: sessionStorage.getItem("userId") || null,
      logged: sessionStorage.getItem("logged") || false,
      user: sessionStorage.getItem("user") || null,
    });
  }

  login(userId, user) {
    sessionStorage.setItem("userId", userId);
    sessionStorage.setItem("logged", "1");
    sessionStorage.setItem("user", user);
		sessionStorage.setItem('carrito', [])

    // Update reactive state
    this.state.userId = userId;
    this.state.logged = true;
    this.state.user = user;
  }

  logout() {
    sessionStorage.removeItem("userId");
    sessionStorage.removeItem("user");
    sessionStorage.setItem("logged", "0");
		sessionStorage.clear()

    // Update reactive state
    this.state.userId = null;
    this.state.logged = false;
    this.state.user = null;
  }

  isLogged() {
    return this.state.logged;
  }

  getUserId() {
    return this.state.userId;
  }
}

export const auth = new AuthService();
