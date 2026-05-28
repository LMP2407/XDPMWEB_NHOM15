import axiosClient from "./axiosClient";

const orderApi = {
  getAll: (params) => axiosClient.get("/api/v1/orders", { params }),
  updateStatus: (id, status) => axiosClient.put(`/api/v1/orders/${id}/status`, { status }),
};

export default orderApi;