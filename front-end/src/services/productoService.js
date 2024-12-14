import {httpClient} from './httpCommon';

const getAll = () => {
    return httpClient.get("/api/producto");
}

const findByCategoria = idCategoria => {
    return httpClient.get(`/api/producto/categoria/${idCategoria}`);
}

const findById = id => {
    return httpClient.get(`/api/producto/${id}`);
}

const create = data => {
    return httpClient.post("/api/producto/create", data);
}

const topProductos = () => {
    return httpClient.get("/api/producto/top");
}

const update = data => {
    return httpClient.put("/api/producto/update", data);
}

const remove = id => {
    return httpClient.delete(`/api/producto/delete/${id}`);
}

export default {
    getAll,
    findByCategoria,
    findById,
    create,
    update,
    remove,
    topProductos
};