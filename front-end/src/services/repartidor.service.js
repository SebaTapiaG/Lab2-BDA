import httpCommon from "@/http-common";

const getAll = () => {
    return httpCommon.get("/api/repartidor");
}

const getById = id => {
    return httpCommon.get(`/api/repartidor/${id}`);
}

const getByEmail = email => {
    return httpCommon.get(`/api/repartidor/email/${email}`);
}

const create = data => {
    return httpCommon.post("/api/repartidor/register", data);
}

const login = data => {
    return httpCommon.post("/api/repartidor/login", data);
}

const getByAlmacen = idAlmacen => {
    return httpCommon.get(`/api/repartidor/almacen/${idAlmacen}`);
}

export default {
    getAll,
    getById,
    create,
    login,
    getByEmail,
    getByAlmacen
};