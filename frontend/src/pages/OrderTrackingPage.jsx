import { useState } from 'react';
import api from '../api/axios';

export default function OrderTrackingPage() {
  const [sdt, setSdt] = useState('');
  const [maDon, setMaDon] = useState('');
  const [order, setOrder] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSearch = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      const response = await api.get(`/don-hang/tra-cuu?sdt=${sdt}&ma=${maDon}`);
      setOrder(response.data);
    } catch (err) {
      setError(err.response?.data?.loi || 'Không tìm thấy đơn hàng');
      setOrder(null);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: '800px', margin: 'auto', padding: '2rem' }}>
      <h2>Tra cứu đơn hàng</h2>
      <form onSubmit={handleSearch}>
        <div><label>Số điện thoại</label><input required value={sdt} onChange={e => setSdt(e.target.value)} /></div>
        <div><label>Mã đơn hàng</label><input required value={maDon} onChange={e => setMaDon(e.target.value)} /></div>
        <button type="submit">Tra cứu</button>
      </form>
      {loading && <p>Đang tra cứu...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {order && (
        <div style={{ marginTop: '2rem' }}>
          <h3>Thông tin đơn hàng</h3>
          <p>Mã đơn: {order.donHang.maDonHang}</p>
          <p>Người nhận: {order.donHang.hoTenNguoiNhan}</p>
          <p>SĐT: {order.donHang.soDienThoaiNhan}</p>
          <p>Địa chỉ: {order.donHang.diaChi}</p>
          <p>Trạng thái: {order.donHang.trangThai}</p>
          <p>Thanh toán: {order.donHang.trangThaiTT}</p>
          <p>Tổng tiền: {order.donHang.tongTien?.toLocaleString('vi-VN')} ₫</p>
          <h4>Chi tiết sản phẩm</h4>
          <table style={{ width: '100%' }}>
            <thead><tr><th>Sản phẩm</th><th>Số lượng</th><th>Đơn giá</th><th>Thành tiền</th></tr></thead>
            <tbody>
              {order.chiTiet.map((item, idx) => (
                <tr key={idx}><td>{item.tenSanPham}</td><td>{item.soLuong}</td><td>{item.donGia?.toLocaleString('vi-VN')} ₫</td><td>{item.thanhTien?.toLocaleString('vi-VN')} ₫</td></tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}