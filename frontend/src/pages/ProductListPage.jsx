import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import api from '../api/axios';
import ProductCard from '../components/ProductCard';
import Pagination from '../components/Pagination';
import FilterSidebar from '../components/FilterSidebar';

export default function ProductListPage() {
  const [searchParams, setSearchParams] = useSearchParams();
  const [products, setProducts] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);
  const [categories, setCategories] = useState([]);

  const page = parseInt(searchParams.get('trang') || '0');
  const keyword = searchParams.get('tu_khoa') || '';
  const thuongHieu = searchParams.get('thuong_hieu') || '';
  const giaMin = searchParams.get('gia_min') || '';
  const giaMax = searchParams.get('gia_max') || '';
  const maDanhMuc = searchParams.get('ma_danh_muc') || '';

  const fetchProducts = async () => {
    setLoading(true);
    try {
      const params = new URLSearchParams();
      params.append('trang', page);
      params.append('gioi_han', 12);
      if (keyword) params.append('tu_khoa', keyword);
      if (thuongHieu) params.append('thuong_hieu', thuongHieu);
      if (giaMin) params.append('gia_min', giaMin);
      if (giaMax) params.append('gia_max', giaMax);
      if (maDanhMuc) params.append('ma_danh_muc', maDanhMuc);

      const response = await api.get(`/san-pham?${params.toString()}`);
      setProducts(response.data.data);
      const total = response.data.tong;
      setTotalPages(Math.ceil(total / 12));
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const fetchCategories = async () => {
    try {
      const res = await api.get('/danh-muc');
      setCategories(res.data.data);
    } catch (error) {}
  };

  useEffect(() => {
    fetchProducts();
  }, [page, keyword, thuongHieu, giaMin, giaMax, maDanhMuc]);

  useEffect(() => {
    fetchCategories();
  }, []);

  const handlePageChange = (newPage) => {
    setSearchParams({ ...Object.fromEntries(searchParams), trang: newPage });
  };

  const handleFilter = (filters) => {
    setSearchParams({ ...filters, trang: '0' });
  };

  return (
    <div style={{ display: 'flex', padding: '2rem' }}>
      <div style={{ width: '250px', marginRight: '2rem' }}>
        <FilterSidebar categories={categories} onFilter={handleFilter} currentFilters={{ keyword, thuongHieu, giaMin, giaMax, maDanhMuc }} />
      </div>
      <div style={{ flex: 1 }}>
        {loading && <p>Đang tải...</p>}
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(220px,1fr))', gap: '1rem' }}>
          {products.map(p => <ProductCard key={p.maSanPham} product={p} />)}
        </div>
        {totalPages > 1 && <Pagination currentPage={page} totalPages={totalPages} onPageChange={handlePageChange} />}
      </div>
    </div>
  );
}