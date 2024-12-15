import httpCommon from "@/http-common";

const getAll = () => {
  return httpCommon.get("/api/orden");
}

const getPagadas = () => {
  return httpCommon.get("/api/orden/pagadas");
}

const get = id => {
    return httpCommon.get(`/api/orden/${id}`);
}

const create = data => {
    return httpCommon.post("/api/orden/create", data);
}

const update = data => {
    return httpCommon.put("/api/orden/update", data);
}

const findByCliente = idCliente => {
    return httpCommon.get(`/api/orden/findByCliente/${idCliente}`);
}

const updateEstado = (id_orden, estado, id_repartidor) => {
    return httpCommon.put(`/api/orden/updateEstado/${id_orden}/${id_repartidor}/${estado}`);
  };

  

export default {
    getAll,
		getPagadas,
    get,
    create,
    update,
    findByCliente,
    updateEstado
};