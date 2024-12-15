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

export default {
	getAll,
	getById,
	getByEstado
}