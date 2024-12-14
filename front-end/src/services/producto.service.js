import httpCommon from "@/http-common";

const getAll = () => {
    return httpCommon.get("/api/producto");
}

const findByCategoria = idCategoria => {
    return httpCommon.get(`/api/producto/categoria/${idCategoria}`);
}

const findById = id => {
    return httpCommon.get(`/api/producto/${id}`);
}

const create = data => {
    return httpCommon.post("/api/producto/create", data);
}

const topProductos = () => {
    return httpCommon.get("/api/producto/top");
}

const update = data => {
    return httpCommon.put("/api/producto/update", data);
}

const remove = id => {
    return httpCommon.delete(`/api/producto/delete/${id}`);
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