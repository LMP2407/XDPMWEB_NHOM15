import axiosClient from "./axiosClient";

const categoryApi = {
  getAll: () => axiosClient.get("/api/v1/categories"),
  create: (data) => axiosClient.post("/api/v1/categories", data),
  update: (id, data) => axiosClient.put(`/api/v1/categories/${id}`, data),
  delete: (id) => axiosClient.delete(`/api/v1/categories/${id}`),
};

export default categoryApi;