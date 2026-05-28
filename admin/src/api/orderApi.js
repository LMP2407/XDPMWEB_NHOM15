import axiosClient from './axiosClient';

export const fetchOrders = async () => {
  const { data } = await axiosClient.get('/orders');
  return data;
};

export const fetchOrderById = async (id) => {
  const { data } = await axiosClient.get(`/orders/${id}`);
  return data;
};

export const updateOrderStatus = async (id, status) => {
  const { data } = await axiosClient.patch(`/orders/${id}`, { status });
  return data;
};
