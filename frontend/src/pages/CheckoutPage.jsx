import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../contexts/CartContext';
import api from '../api/axios';

export default function CheckoutPage() {
  const { cartItems, totalPrice, fetchCart } = useCart();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    hoTen: '',
    soDienThoai: '',
    diaChi: '',
    tinhThanh: '',
    phuongThucTT: 'cod',
    ghiChu: ''
  });
  const [submitting, setSubmitting] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (cartItems.length === 0) {
      alert('Giỏ hàng trống');
      return;
    }
    // BUG FIX: was sending ma_san_pham/so_luong (snake_case),
    // backend CartItemRequest expects maSanPham/soLuong (camelCase)
    const items = cartItems.map(item => ({
      maSanPham: item.maSanPham,
      soLuong: item.soLuong
    }));
    const payload = { ...formData, items };
    setSubmitting(true);
    try {
      const response = await api.post('/don-hang', payload);
      alert(`Đặt hàng thành công! Mã đơn: ${response.data.maDonHang}`);
      await fetchCart();
      navigate('/track-order');
    } catch (error) {
      alert(error.response?.data?.loi || 'Đặt hàng thất bại');
    } finally {
      setSubmitting(false);
    }
  };

  if (cartItems.length === 0) {
    return <div style={{ padding: '2rem' }}>Giỏ hàng trống, không thể thanh toán. <a href="/products">Mua hàng</a></div>;
  }

  return (
    <div style={{ maxWidth: '600px', margin: 'auto', padding: '2rem' }}>
      <h2>Thông tin nhận hàng</h2>
      <form onSubmit={handleSubmit}>
        <div><label>Họ tên</label><input name="hoTen" required value={formData.hoTen} onChange={handleChange} /></div>
        <div><label>Số điện thoại</label><input name="soDienThoai" required value={formData.soDienThoai} onChange={handleChange} /></div>
        <div><label>Địa chỉ</label><input name="diaChi" required value={formData.diaChi} onChange={handleChange} /></div>
        <div><label>Tỉnh/Thành</label><input name="tinhThanh" required value={formData.tinhThanh} onChange={handleChange} /></div>
        <div><label>Phương thức thanh toán</label>
          <select name="phuongThucTT" value={formData.phuongThucTT} onChange={handleChange}>
            <option value="cod">COD (Thanh toán khi nhận hàng)</option>
            <option value="vnpay">VNPay</option>
          </select>
        </div>
        <div><label>Ghi chú</label><textarea name="ghiChu" value={formData.ghiChu} onChange={handleChange} /></div>
        <div style={{ marginTop: '1rem' }}>Tổng tiền: {totalPrice?.toLocaleString('vi-VN')} ₫</div>
        <button type="submit" disabled={submitting}>{submitting ? 'Đang đặt hàng...' : 'Đặt hàng'}</button>
      </form>
    </div>
  );
}
