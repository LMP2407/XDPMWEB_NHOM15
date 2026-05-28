import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

export default function RegisterPage() {
  const [formData, setFormData] = useState({
    hoTen: '',
    email: '',
    soDienThoai: '',
    matKhau: '',
    confirmMatKhau: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    if (formData.matKhau !== formData.confirmMatKhau) {
      setError('Mật khẩu xác nhận không khớp');
      return;
    }
    setLoading(true);
    const result = await register(formData.hoTen, formData.email, formData.soDienThoai, formData.matKhau);
    if (result.success) {
      alert('Đăng ký thành công! Vui lòng đăng nhập.');
      navigate('/login');
    } else {
      setError(result.message);
    }
    setLoading(false);
  };

  return (
    <div style={{ maxWidth: '400px', margin: 'auto', padding: '2rem' }}>
      <h2>Đăng ký tài khoản</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <form onSubmit={handleSubmit}>
        <div>
          <label>Họ tên</label>
          <input name="hoTen" required value={formData.hoTen} onChange={handleChange} />
        </div>
        <div>
          <label>Email</label>
          <input type="email" name="email" required value={formData.email} onChange={handleChange} />
        </div>
        <div>
          <label>Số điện thoại</label>
          <input name="soDienThoai" required value={formData.soDienThoai} onChange={handleChange} />
        </div>
        <div>
          <label>Mật khẩu</label>
          <input type="password" name="matKhau" required value={formData.matKhau} onChange={handleChange} />
        </div>
        <div>
          <label>Xác nhận mật khẩu</label>
          <input type="password" name="confirmMatKhau" required value={formData.confirmMatKhau} onChange={handleChange} />
        </div>
        <button type="submit" disabled={loading}>Đăng ký</button>
      </form>
      <p>Đã có tài khoản? <Link to="/login">Đăng nhập</Link></p>
    </div>
  );
}