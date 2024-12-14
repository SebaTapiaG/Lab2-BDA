import axios from "axios";

const tbdServer = "localhost:8080";

export default axios.create({
    baseURL: `http://${tbdServer}`,
    headers: {
        'Content-Type': 'application/json'
    } 
});