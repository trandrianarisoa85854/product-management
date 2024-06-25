import axios from "axios";

const PRODUCT_BASE_REST_API_URL = "http://localhost:8080/api/v1/auth";

class AuthenticationService {
    login(username, password) {
        return axios.post(PRODUCT_BASE_REST_API_URL + "/authenticate", {
            username,
            password,
        }).then((response) => {
            if (response.data.accessToken) {
                localStorage.setItem("user", JSON.stringify(response.data));
            }
            return response.data;
        });
    };

    logout() {
        localStorage.removeItem("user");
    };
}

const authenticationServiceInstance = new AuthenticationService();
export default authenticationServiceInstance;