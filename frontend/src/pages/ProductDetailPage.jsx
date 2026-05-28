import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/axios';
import { useCart } from '../contexts/CartContext';

export default function ProductDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { addToCart } = useCart();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [quantity, setQuantity] = useState(1);
  const [adding, setAdding] = useState(false);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await api.get(`/san-pham/${id}`);
        setProduct(response.data.san_pham);
      } catch (error) {
        console.error(error);
        alert('Không tìm thấy sản phẩm');
        navigate('/products');
      } finally {
        setLoading(false);
      }
    };
    fetchProduct();
  }, [id, navigate]);

  const handleAddToCart = async () => {
    if (quantity < 1) return;
    setAdding(true);
    try {
      await addToCart(product.maSanPham, quantity);
      alert('Đã thêm vào giỏ hàng');
    } catch (error) {
      alert('Thêm thất bại');
    } finally {
      setAdding(false);
    }
  };

  if (loading) return <div style={{ padding: '2rem', textAlign: 'center' }}>Đang tải sản phẩm...</div>;
  if (!product) return null;

  return (
    <div style={{ maxWidth: '1200px', margin: 'auto', padding: '2rem', display: 'flex', gap: '2rem', flexWrap: 'wrap' }}>
      <div style={{ flex: 1, minWidth: '250px' }}>
        <img src={product.hinhAnh ? `/${product.hinhAnh}` : 'https://via.placeholder.com/400'} alt={product.tenSanPham} style={{ width: '100%', borderRadius: '8px' }} />
      </div>
      <div style={{ flex: 2 }}>
        <h2>{product.tenSanPham}</h2>
        <p><strong>Thương hiệu:</strong> {product.thuongHieu}</p>
        <p><strong>Màu sắc:</strong> {product.mauSac || 'Không có'}</p>
        <p><strong>Dung lượng:</strong> {product.dungLuong || 'Không có'}</p>
        <p><strong>Giá:</strong> <span style={{ fontSize: '1.5rem', color: 'red' }}>{product.gia?.toLocaleString('vi-VN')} ₫</span></p>
        <p><strong>Tồn kho:</strong> {product.soLuongTon}</p>
        <p><strong>Mô tả:</strong> {product.moTa}</p>
        <div style={{ marginTop: '1rem' }}>
          <label>Số lượng: </label>
          <input type="number" min="1" max={product.soLuongTon} value={quantity} onChange={e => setQuantity(parseInt(e.target.value))} style={{ width: '70px', marginRight: '1rem' }} />
          <button onClick={handleAddToCart} disabled={adding || product.soLuongTon === 0}>
            {adding ? 'Đang thêm...' : (product.soLuongTon === 0 ? 'Hết hàng' : 'Thêm vào giỏ')}
          </button>
        </div>
      </div>
    </div>
  );
}