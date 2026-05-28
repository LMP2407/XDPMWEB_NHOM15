import { useState } from 'react';

export default function FilterSidebar({ categories, onFilter, currentFilters }) {
  const [thuongHieu, setThuongHieu] = useState(currentFilters.thuongHieu || '');
  const [giaMin, setGiaMin] = useState(currentFilters.giaMin || '');
  const [giaMax, setGiaMax] = useState(currentFilters.giaMax || '');
  const [maDanhMuc, setMaDanhMuc] = useState(currentFilters.maDanhMuc || '');
  const [keyword, setKeyword] = useState(currentFilters.keyword || '');

  const applyFilter = () => {
    onFilter({ tu_khoa: keyword, thuong_hieu: thuongHieu, gia_min: giaMin, gia_max: giaMax, ma_danh_muc: maDanhMuc });
  };

  const clearFilter = () => {
    setThuongHieu('');
    setGiaMin('');
    setGiaMax('');
    setMaDanhMuc('');
    setKeyword('');
    onFilter({});
  };

  return (
    <div style={{ background: '#f5f5f5', padding: '1rem', borderRadius: '8px' }}>
      <h3>Lọc sản phẩm</h3>
      <div>
        <label>Tìm kiếm</label>
        <input type="text" value={keyword} onChange={e => setKeyword(e.target.value)} placeholder="Tên sản phẩm" />
      </div>
      <div>
        <label>Thương hiệu</label>
        <select value={thuongHieu} onChange={e => setThuongHieu(e.target.value)}>
          <option value="">Tất cả</option>
          <option value="Apple">Apple</option>
          <option value="Samsung">Samsung</option>
          <option value="Xiaomi">Xiaomi</option>
        </select>
      </div>
      <div>
        <label>Danh mục</label>
        <select value={maDanhMuc} onChange={e => setMaDanhMuc(e.target.value)}>
          <option value="">Tất cả</option>
          {categories.map(cat => (
            <option key={cat.maDanhMuc} value={cat.maDanhMuc}>{cat.tenDanhMuc}</option>
          ))}
        </select>
      </div>
      <div>
        <label>Giá từ</label>
        <input type="number" value={giaMin} onChange={e => setGiaMin(e.target.value)} placeholder="0" />
      </div>
      <div>
        <label>Đến</label>
        <input type="number" value={giaMax} onChange={e => setGiaMax(e.target.value)} placeholder="100000000" />
      </div>
      <button onClick={applyFilter}>Áp dụng</button>
      <button onClick={clearFilter} style={{ marginLeft: '0.5rem' }}>Xóa lọc</button>
    </div>
  );
}