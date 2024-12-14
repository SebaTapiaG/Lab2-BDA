import httpClient from "@/http-common";

const getAll = () => {
  return httpClient.get("/api/cliente");
}

const get = id => {
  return httpClient.get(`/api/cliente/${id}`);
}

const getByEmail = email => {
  return httpClient.get(`/api/cliente/email/${email}`);
}

const login = userObj => {
    return httpClient.post("/api/cliente/login", userObj);
}

const register = userObj => {
    return httpClient.post("/api/cliente/register", userObj);
}

const puntoMedio = () =>{
  return httpClient.get(`/api/cliente/${id}`);
}

export default {
    getAll,
    get,
    getByEmail,
    login,
    register
};