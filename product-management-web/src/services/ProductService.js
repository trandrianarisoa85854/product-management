import axios from "axios"
import authHeader from "./auth-header";

const PRODUCT_BASE_REST_API_URL = "http://localhost:8080/api/v1/products";

class ProductService {
    getAllProducts() {
        //console.log('HRADER : ', authHeader());
        return axios.get(PRODUCT_BASE_REST_API_URL + "/", {headers: authHeader()});
    }

    getProductById(productId) {
        return axios.get(`${PRODUCT_BASE_REST_API_URL}/${productId}`, {headers: authHeader()});
    }

    createProduct(product) {
        return axios.post(PRODUCT_BASE_REST_API_URL + "/", product, {headers: authHeader()});
    }

    updateProduct(productId, product) {
        return axios.put(`${PRODUCT_BASE_REST_API_URL}/${productId}`, product, {headers: authHeader()});
    }

    deleteProduct(productId) {
        return axios.delete(`${PRODUCT_BASE_REST_API_URL}/${productId}`, {headers: authHeader()});
    }
}

const productServiceInstance = new ProductService();
export default productServiceInstance;