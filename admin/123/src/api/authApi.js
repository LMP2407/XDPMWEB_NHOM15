import axiosClient from "./axiosClient";

// ⚠️ Đổi endpoint đăng nhập cho đúng với Swagger của nhóm bạn
const authApi = {
  login: (data) => axiosClient.post("/api/v1/auth/login", data),
};

export default authApi;