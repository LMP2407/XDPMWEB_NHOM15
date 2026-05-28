import { useQuery } from "@tanstack/react-query";
import productApi from "@/api/productApi";
import orderApi from "@/api/orderApi";
import { Package, ShoppingCart, DollarSign, AlertTriangle } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import StatCard from "@/components/dashboard/StatCard";
import RecentOrders from "@/components/dashboard/RecentOrders";

export default function Dashboard() {
  const { data: productsRes } = useQuery({ queryKey: ["products"], queryFn: () => productApi.getAll() });
  const { data: ordersRes } = useQuery({ queryKey: ["orders"], queryFn: () => orderApi.getAll() });

  const products = productsRes?.data?.content || productsRes?.data || [];
  const orders = ordersRes?.data?.content || ordersRes?.data || [];

  const totalRevenue = orders.filter(o => o.status === "delivered").reduce((s, o) => s + (o.total || 0), 0);
  const pendingOrders = orders.filter(o => o.status === "pending").length;
  const lowStock = products.filter(p => p.stock <= 5).length;
  const fmt = (n) => new Intl.NumberFormat("vi-VN").format(n);

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold tracking-tight">Tổng quan</h1>
        <p className="text-muted-foreground text-sm mt-1">Chào mừng trở lại! Đây là tình hình cửa hàng hôm nay.</p>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-4">
        <StatCard icon={Package} label="Sản phẩm" value={products.length} color="text-primary" />
        <StatCard icon={ShoppingCart} label="Đơn chờ xử lý" value={pendingOrders} color="text-amber-500" />
        <StatCard icon={DollarSign} label="Doanh thu" value={`${fmt(totalRevenue)}đ`} color="text-emerald-500" />
        <StatCard icon={AlertTriangle} label="Sắp hết hàng" value={lowStock} color="text-destructive" />
      </div>

      <div className="grid grid-cols-1 xl:grid-cols-2 gap-6">
        <Card>
          <CardHeader><CardTitle className="text-base">Đơn hàng gần đây</CardTitle></CardHeader>
          <CardContent><RecentOrders orders={orders.slice(0, 8)} /></CardContent>
        </Card>
        <Card>
          <CardHeader><CardTitle className="text-base">Sản phẩm sắp hết hàng</CardTitle></CardHeader>
          <CardContent>
            {products.filter(p => p.stock <= 5).length === 0 ? (
              <p className="text-sm text-muted-foreground py-6 text-center">Tất cả sản phẩm đều còn hàng</p>
            ) : (
              <div className="space-y-3">
                {products.filter(p => p.stock <= 5).slice(0, 6).map(p => (
                  <div key={p.id} className="flex items-center justify-between py-2 border-b border-border last:border-0">
                    <div>
                      <p className="text-sm font-medium">{p.name}</p>
                      <p className="text-xs text-muted-foreground">{p.brand}</p>
                    </div>
                    <span className="text-xs font-semibold text-destructive bg-destructive/10 px-2 py-1 rounded-full">
                      Còn {p.stock}
                    </span>
                  </div>
                ))}
              </div>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
}