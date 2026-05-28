import { useEffect, useState } from 'react';
import api from '../api/axios';
import ProductCard from '../components/ProductCard';

export default function HomePage() {
  const [products, setProducts] = useState([]);
  useEffect(() => {
    api.get('/san-pham?trang=0&gioi_han=8')
      .then(res => setProducts(res.data.data))
      .catch(err => console.error(err));
  }, []);
  return (
    <div>
      <h2>Trang chủ</h2>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(4,1fr)', gap: '1rem' }}>
        {products.map(p => <ProductCard key={p.maSanPham} product={p} />)}
      </div>
    </div>
  );
}