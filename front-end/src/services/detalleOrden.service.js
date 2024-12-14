import httpCommon from "@/http-common";

const getAll = () => {
    return httpCommon.get("/api/detalleOrden");
}

const get = id => {
    return httpCommon.get(`/api/detalleOrden/${id}`);
}

const create = data => {
    return httpCommon.post("/api/detalleOrden/create", data);
}

const update = data => {
    return httpCommon.put("/api/detalleOrden/update", data);
}

export default {
    getAll,
    get,
    create,
    update
};