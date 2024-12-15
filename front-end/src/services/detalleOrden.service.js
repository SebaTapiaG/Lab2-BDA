import httpCommon from "@/http-common";

const getAll = () => {
    return httpCommon.get("/api/detalle_orden");
}

const get = id => {
    return httpCommon.get(`/api/detalle_orden/${id}`);
}

const create = data => {
    return httpCommon.post("/api/detalle_orden/create", data);
}

const update = data => {
    return httpCommon.put("/api/detalle_orden/update", data);
}

export default {
    getAll,
    get,
    create,
    update
};