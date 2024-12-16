import httpCommon from "@/http-common";

const getAll = () => {
		return httpCommon.get("/api/zona");
}

const getById = id => {
		return httpCommon.get(`/api/zona/${id}`);
}

const getByEstado = estado => {
		return httpCommon.get(`/api/zona/estado/${estado}`);
}

const findRepartidores = idZona => {
	return httpCommon.get(`/api/orden/findDeliveryCompletedInArea/${idZona}`);
}

const create = data => {
	return httpCommon.post(`/api/zona/create`, data)
}

export default {
	getAll,
	getById,
	getByEstado,
	findRepartidores,
	create,
}