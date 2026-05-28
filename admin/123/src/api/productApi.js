import axiosClient from "./axiosClient";

const productApi = {
  getAll: (params) => axiosClient.get("/api/v1/products", { params }),
  create: (data) => axiosClient.post("/api/v1/products", data),
  update: (id, data) => axiosClient.put(`/api/v1/products/${id}`, data),
  delete: (id) => axiosClient.delete(`/api/v1/products/${id}`),
};

export default productApi;