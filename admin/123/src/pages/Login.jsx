import { useState } from "react";
import { useNavigate } from "react-router-dom";
import authApi from "@/api/authApi";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Smartphone, Mail, Lock, Loader2 } from "lucide-react";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);
    try {
      const res = await authApi.login({ email, password });
      // ⚠️ Đổi field lấy token cho đúng với response của backend
      // Ví dụ: res.data.token, res.data.accessToken, res.data.data.token...
      const token = res.data.token || res.data.accessToken || res.data.data?.token;
      if (!token) throw new Error("Không nhận được token từ server");
      localStorage.setItem("accessToken", token);
      navigate("/");
    } catch (err) {
      setError(err.response?.data?.message || err.message || "Sai email hoặc mật khẩu");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-background px-4">
      <div className="w-full max-w-sm">
        <div className="text-center mb-8">
          <div className="w-12 h-12 rounded-xl bg-primary flex items-center justify-center mx-auto mb-4">
            <Smartphone className="w-6 h-6 text-primary-foreground" />
          </div>
          <h1 className="text-2xl font-bold">PhoneStore Admin</h1>
          <p className="text-muted-foreground text-sm mt-1">Đăng nhập vào hệ thống quản trị</p>
        </div>

        <div className="bg-card border border-border rounded-xl p-6 shadow-sm">
          {error && (
            <div className="mb-4 p-3 rounded-lg bg-destructive/10 text-destructive text-sm">
              {error}
            </div>
          )}
          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <div className="relative">
                <Mail className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                <Input
                  id="email"
                  type="email"
                  placeholder="admin@example.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="pl-10 h-12"
                  required
                  autoFocus
                />
              </div>
            </div>
            <div className="space-y-2">
              <Label htmlFor="password">Mật khẩu</Label>
              <div className="relative">
                <Lock className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                <Input
                  id="password"
                  type="password"
                  placeholder="••••••••"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="pl-10 h-12"
                  required
                />
              </div>
            </div>
            <Button type="submit" className="w-full h-12" disabled={loading}>
              {loading ? <><Loader2 className="w-4 h-4 mr-2 animate-spin" />Đang đăng nhập...</> : "Đăng nhập"}
            </Button>
          </form>
        </div>
      </div>
    </div>
  );
}