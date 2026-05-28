import { Link } from 'react-router-dom';
import { useCart } from '../contexts/CartContext';

export default function Header() {
  const { cartItems } = useCart();
  const itemCount = cartItems.reduce((sum, item) => sum + item.soLuong, 0);

  return (
    <header style={{ background: '#333', color: '#fff', padding: '1rem' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h1>
          <Link to="/" style={{ color: '#fff', textDecoration: 'none' }}>
            Phone Store
          </Link>
        </h1>
        <nav>
          <Link to="/products" style={{ margin: '0 1rem', color: '#fff' }}>Sản phẩm</Link>
          <Link to="/cart" style={{ margin: '0 1rem', color: '#fff' }}>Giỏ hàng ({itemCount})</Link>
          <Link to="/track-order" style={{ margin: '0 1rem', color: '#fff' }}>Tra cứu đơn hàng</Link>
        </nav>
      </div>
    </header>
  );
}