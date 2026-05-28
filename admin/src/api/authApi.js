import axiosClient from './axiosClient';

export const login = async (credentials) => {
  const { data } = await axiosClient.post('/auth/login', credentials);
  return data;
};

export const logout = async () => {
  const { data } = await axiosClient.post('/auth/logout');
  return data;
};

export const getCurrentUser = async () => {
  const { data } = await axiosClient.get('/auth/me');
  return data;
};
