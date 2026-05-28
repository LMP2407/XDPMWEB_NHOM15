import { useState } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import orderApi from "@/api/orderApi";
import { Search, Eye } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Badge } from "@/components/ui/badge";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import OrderDetailDialog from "@/components/orders/OrderDetailDialog";

const statusMap = {
  pending: { label: "Chờ xử lý", variant: "outline" },
  confirmed: { label: "Đã xác nhận", variant: "secondary" },
  shipping: { label: "Đang giao", variant: "default" },
  delivered: { label: "Đã giao", variant: "default" },
  cancelled: { label: "Đã hủy", variant: "destructive" },
};

export default function Orders() {
  const [search, setSearch] = useState("");
  const [statusFilter, setStatusFilter] = useState("all");
  const [selected, setSelected] = useState(null);
  const queryClient = useQueryClient();

  const { data: res, isLoading } = useQuery({ queryKey: ["orders"], queryFn: () => orderApi.getAll() });
  const orders = res?.data?.content || res?.data || [];

  const updateStatus = useMutation({
    mutationFn: ({ id, status }) => orderApi.updateStatus(id, status),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ["orders"] }),
  });

  const filtered = orders.filter(o => {
    const matchSearch = o.customer_name?.toLowerCase().includes(search.toLowerCase()) ||
      o.customerName?.toLowerCase().includes(search.toLowerCase()) ||
      o.order_code?.toLowerCase().includes(search.toLowerCase()) ||
      o.orderCode?.toLowerCase().includes(search.toLowerCase());
    const matchStatus = statusFilter === "all" || o.status === statusFilter;
    return matchSearch && matchStatus;
  });

  const fmt = (n) => new Intl.NumberFormat("vi-VN").format(n);

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold tracking-tight">Đơn hàng</h1>
        <p className="text-sm text-muted-foreground mt-1">{orders.length} đơn hàng</p>
      </div>

      <div className="flex flex-col sm:flex-row gap-3">
        <div className="relative flex-1 max-w-sm">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-muted-foreground" />
          <Input placeholder="Tìm theo tên, mã đơn..." value={search} onChange={e => setSearch(e.target.value)} className="pl-9" />
        </div>
        <Select value={statusFilter} onValueChange={setStatusFilter}>
          <SelectTrigger className="w-40"><SelectValue /></SelectTrigger>
          <SelectContent>
            <SelectItem value="all">Tất cả</SelectItem>
            {Object.entries(statusMap).map(([k, v]) => <SelectItem key={k} value={k}>{v.label}</SelectItem>)}
          </SelectContent>
        </Select>
      </div>

      {isLoading ? (
        <div className="flex justify-center py-12"><div className="w-6 h-6 border-2 border-primary border-t-transparent rounded-full animate-spin" /></div>
      ) : (
        <div className="border rounded-lg overflow-x-auto bg-card">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Mã đơn</TableHead>
                <TableHead>Khách hàng</TableHead>
                <TableHead>Tổng tiền</TableHead>
                <TableHead>Trạng thái</TableHead>
                <TableHead className="text-right">Thao tác</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {filtered.length === 0 ? (
                <TableRow><TableCell colSpan={5} className="text-center text-muted-foreground py-8">Không có đơn hàng</TableCell></TableRow>
              ) : filtered.map(o => {
                const s = statusMap[o.status] || statusMap.pending;
                const name = o.customer_name || o.customerName;
                const phone = o.customer_phone || o.customerPhone;
                const code = o.order_code || o.orderCode;
                return (
                  <TableRow key={o.id}>
                    <TableCell className="font-mono text-sm">{code}</TableCell>
                    <TableCell>
                      <div>
                        <p className="font-medium text-sm">{name}</p>
                        <p className="text-xs text-muted-foreground">{phone}</p>
                      </div>
                    </TableCell>
                    <TableCell className="font-semibold">{fmt(o.total)}đ</TableCell>
                    <TableCell>
                      <Select value={o.status} onValueChange={status => updateStatus.mutate({ id: o.id, status })}>
                        <SelectTrigger className="w-32 h-8 text-xs">
                          <Badge variant={s.variant} className="text-xs">{s.label}</Badge>
                        </SelectTrigger>
                        <SelectContent>
                          {Object.entries(statusMap).map(([k, v]) => <SelectItem key={k} value={k}>{v.label}</SelectItem>)}
                        </SelectContent>
                      </Select>
                    </TableCell>
                    <TableCell className="text-right">
                      <Button size="icon" variant="ghost" className="h-8 w-8" onClick={() => setSelected(o)}>
                        <Eye className="w-4 h-4" />
                      </Button>
                    </TableCell>
                  </TableRow>
                );
              })}
            </TableBody>
          </Table>
        </div>
      )}

      <OrderDetailDialog order={selected} open={!!selected} onOpenChange={() => setSelected(null)} />
    </div>
  );
}