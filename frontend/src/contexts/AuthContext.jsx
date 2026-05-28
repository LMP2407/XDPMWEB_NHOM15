import { createContext, useState, useEffect, useContext } from 'react';
import api from '../api/axios';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');
    if (token && storedUser) {
      setUser(JSON.parse(storedUser));
    }
    setLoading(false);
  }, []);

  const login = async (email, password) => {
    try {
      const response = await api.post('/auth/dang-nhap', { email, mat_khau: password });
      const { token, hoTen, email: userEmail } = response.data;
      localStorage.setItem('token', token);
      const userData = { hoTen, email: userEmail };
      localStorage.setItem('user', JSON.stringify(userData));
      setUser(userData);
      return { success: true };
    } catch (error) {
      return { success: false, message: error.response?.data?.loi || 'Đăng nhập thất bại' };
    }
  };

  const register = async (hoTen, email, soDienThoai, matKhau) => {
    try {
      await api.post('/auth/dang-ky', { ho_ten: hoTen, email, so_dien_thoai: soDienThoai, mat_khau: matKhau });
      return { success: true };
    } catch (error) {
      return { success: false, message: error.response?.data?.loi || 'Đăng ký thất bại' };
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
    window.location.href = '/';
  };

  return (
    <AuthContext.Provider value={{ user, loading, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);