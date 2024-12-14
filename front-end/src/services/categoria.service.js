import httpCommon from "@/http-common";

const getAll = () => {
    return httpCommon.get("/api/categoria");
}

const get = id => {
    return httpCommon.get(`/api/categoria/${id}`);
}

const create = data => {
    return httpCommon.post("/api/categoria/create", data);
}

const update = data => {
    return httpCommon.put("/api/categoria", data);
}

const remove = id => {
    return httpCommon.delete(`/api/categoria/${id}`);
}

export default {
    getAll,
    get,
    create,
    update,
    remove
};