import { createContext, useContext, useState, useEffect } from 'react';
import api from '../api/axios';

const CartContext = createContext();

export const CartProvider = ({ children }) => {
  const [cartItems, setCartItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [loading, setLoading] = useState(false);

  const fetchCart = async () => {
    setLoading(true);
    try {
      const response = await api.get('/gio-hang/guest');
      setCartItems(response.data.items || []);
      setTotalPrice(response.data.tongTien || 0);
    } catch (error) {
      console.error('Lỗi lấy giỏ hàng:', error);
    } finally {
      setLoading(false);
    }
  };

  const addToCart = async (maSanPham, soLuong = 1) => {
    try {
      await api.post('/gio-hang/guest', { maSanPham, soLuong });
      await fetchCart();
    } catch (error) {
      console.error(error);
      throw error;
    }
  };

  const updateQuantity = async (maSanPham, soLuong) => {
    try {
      await api.put(`/gio-hang/guest/${maSanPham}`, { soLuong });
      await fetchCart();
    } catch (error) {
      console.error(error);
    }
  };

  const removeFromCart = async (maSanPham) => {
    try {
      await api.delete(`/gio-hang/guest/${maSanPham}`);
      await fetchCart();
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchCart();
  }, []);

  return (
    <CartContext.Provider value={{ cartItems, totalPrice, loading, addToCart, updateQuantity, removeFromCart, fetchCart }}>
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => useContext(CartContext);