import { useCart } from '../contexts/CartContext';
import { Link } from 'react-router-dom';

export default function CartPage() {
  const { cartItems, totalPrice, updateQuantity, removeFromCart, loading } = useCart();

  if (loading) return <p>Đang tải giỏ hàng...</p>;

  if (cartItems.length === 0) {
    return <div style={{ padding: '2rem', textAlign: 'center' }}>Giỏ hàng trống. <Link to="/products">Tiếp tục mua sắm</Link></div>;
  }

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Giỏ hàng của bạn</h2>
      <table style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr><th>Sản phẩm</th><th>Đơn giá</th><th>Số lượng</th><th>Thành tiền</th><th></th></tr>
        </thead>
        <tbody>
          {cartItems.map(item => (
            <tr key={item.maSanPham} style={{ borderBottom: '1px solid #ddd' }}>
              <td>{item.tenSanPham}</td>
              <td>{item.gia?.toLocaleString('vi-VN')} ₫</td>
              <td>
                <input type="number" min="1" value={item.soLuong} onChange={e => updateQuantity(item.maSanPham, parseInt(e.target.value))} style={{ width: '60px' }} />
              </td>
              <td>{(item.gia * item.soLuong).toLocaleString('vi-VN')} ₫</td>
              <td><button onClick={() => removeFromCart(item.maSanPham)}>Xóa</button></td>
            </tr>
          ))}
        </tbody>
      </table>
      <div style={{ textAlign: 'right', marginTop: '1rem' }}>
        <strong>Tổng tiền: {totalPrice?.toLocaleString('vi-VN')} ₫</strong>
      </div>
      <div style={{ textAlign: 'right', marginTop: '1rem' }}>
        <Link to="/checkout"><button>Tiến hành thanh toán</button></Link>
      </div>
    </div>
  );
}