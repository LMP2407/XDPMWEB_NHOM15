// src/components/ProductCard.jsx
import { Link } from 'react-router-dom';
import { useCart } from '../contexts/CartContext';

export default function ProductCard({ product }) {
  const { addToCart } = useCart();

  const handleAddToCart = async () => {
    try {
      await addToCart(product.maSanPham, 1);
      alert('Đã thêm vào giỏ hàng');
    } catch (error) {
      alert('Thêm thất bại');
    }
  };

  // BUG FIX: thêm "/" để trỏ vào thư mục public/
  const imgSrc = product.hinhAnh
    ? `/${product.hinhAnh}`
    : 'https://via.placeholder.com/200';

  return (
    <div style={{ border: '1px solid #ddd', padding: '1rem', borderRadius: '8px', textAlign: 'center' }}>
      <Link to={`/product/${product.maSanPham}`}>
        <img src={imgSrc} alt={product.tenSanPham} style={{ width: '100%', height: '200px', objectFit: 'cover' }} />
        <h3>{product.tenSanPham}</h3>
      </Link>
      <p>{product.gia?.toLocaleString('vi-VN')} ₫</p>
      <button onClick={handleAddToCart}>Thêm vào giỏ</button>
    </div>
  );
}