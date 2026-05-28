import { Badge } from "@/components/ui/badge";

const statusMap = {
  pending: { label: "Chờ xử lý", variant: "outline" },
  confirmed: { label: "Đã xác nhận", variant: "secondary" },
  shipping: { label: "Đang giao", variant: "default" },
  delivered: { label: "Đã giao", variant: "default" },
  cancelled: { label: "Đã hủy", variant: "destructive" },
};

export default function RecentOrders({ orders }) {
  const fmt = (n) => new Intl.NumberFormat("vi-VN").format(n);

  if (!orders.length) return <p className="text-sm text-muted-foreground py-6 text-center">Chưa có đơn hàng nào</p>;

  return (
    <div className="space-y-3">
      {orders.map(o => {
        const s = statusMap[o.status] || statusMap.pending;
        return (
          <div key={o.id} className="flex items-center justify-between py-2 border-b border-border last:border-0">
            <div className="min-w-0">
              <p className="text-sm font-medium truncate">{o.customer_name}</p>
              <p className="text-xs text-muted-foreground">{o.order_code}</p>
            </div>
            <div className="flex items-center gap-3 shrink-0">
              <span className="text-sm font-semibold">{fmt(o.total)}đ</span>
              <Badge variant={s.variant} className="text-xs">{s.label}</Badge>
            </div>
          </div>
        );
      })}
    </div>
  );
}