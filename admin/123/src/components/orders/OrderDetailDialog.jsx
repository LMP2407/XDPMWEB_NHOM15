import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Badge } from "@/components/ui/badge";

const statusMap = {
  pending: { label: "Chờ xử lý", variant: "outline" },
  confirmed: { label: "Đã xác nhận", variant: "secondary" },
  shipping: { label: "Đang giao", variant: "default" },
  delivered: { label: "Đã giao", variant: "default" },
  cancelled: { label: "Đã hủy", variant: "destructive" },
};

const paymentLabels = { cash: "Tiền mặt", transfer: "Chuyển khoản", card: "Thẻ", momo: "MoMo", zalopay: "ZaloPay" };

export default function OrderDetailDialog({ order, open, onOpenChange }) {
  if (!order) return null;
  const fmt = (n) => new Intl.NumberFormat("vi-VN").format(n);
  const s = statusMap[order.status] || statusMap.pending;

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="max-w-md">
        <DialogHeader>
          <DialogTitle>Đơn hàng {order.order_code}</DialogTitle>
        </DialogHeader>
        <div className="space-y-4">
          <div className="flex items-center gap-2">
            <Badge variant={s.variant}>{s.label}</Badge>
            {order.payment_method && <span className="text-xs text-muted-foreground">· {paymentLabels[order.payment_method] || order.payment_method}</span>}
          </div>

          <div className="space-y-1 text-sm">
            <p><span className="text-muted-foreground">Khách hàng:</span> {order.customer_name}</p>
            <p><span className="text-muted-foreground">SĐT:</span> {order.customer_phone}</p>
            {order.customer_email && <p><span className="text-muted-foreground">Email:</span> {order.customer_email}</p>}
            {order.shipping_address && <p><span className="text-muted-foreground">Địa chỉ:</span> {order.shipping_address}</p>}
          </div>

          {order.items?.length > 0 && (
            <div>
              <p className="text-sm font-medium mb-2">Sản phẩm</p>
              <div className="space-y-2">
                {order.items.map((item, i) => (
                  <div key={i} className="flex justify-between text-sm bg-muted/50 px-3 py-2 rounded-lg">
                    <span>{item.product_name} x{item.quantity}</span>
                    <span className="font-medium">{fmt(item.price * item.quantity)}đ</span>
                  </div>
                ))}
              </div>
            </div>
          )}

          <div className="flex justify-between items-center pt-2 border-t">
            <span className="font-medium">Tổng cộng</span>
            <span className="text-lg font-bold text-primary">{fmt(order.total)}đ</span>
          </div>

          {order.notes && (
            <div className="bg-muted/50 p-3 rounded-lg">
              <p className="text-xs text-muted-foreground mb-1">Ghi chú</p>
              <p className="text-sm">{order.notes}</p>
            </div>
          )}
        </div>
      </DialogContent>
    </Dialog>
  );
}