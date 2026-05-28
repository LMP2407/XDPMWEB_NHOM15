import { useState } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import productApi from "@/api/productApi";
import { Plus, Search, Pencil, Trash2 } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent } from "@/components/ui/card";
import ProductDialog from "@/components/products/ProductDialog";
import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle } from "@/components/ui/alert-dialog";

const statusLabels = { active: "Đang bán", inactive: "Ngừng bán", out_of_stock: "Hết hàng" };
const statusVariants = { active: "default", inactive: "secondary", out_of_stock: "destructive" };

export default function Products() {
  const [search, setSearch] = useState("");
  const [dialogOpen, setDialogOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [deleting, setDeleting] = useState(null);
  const queryClient = useQueryClient();

  const { data: res, isLoading } = useQuery({ queryKey: ["products"], queryFn: () => productApi.getAll() });
  const products = res?.data?.content || res?.data || [];

  const deleteMutation = useMutation({
    mutationFn: (id) => productApi.delete(id),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ["products"] }); setDeleting(null); },
  });

  const filtered = products.filter(p =>
    p.name?.toLowerCase().includes(search.toLowerCase()) || p.brand?.toLowerCase().includes(search.toLowerCase())
  );

  const fmt = (n) => new Intl.NumberFormat("vi-VN").format(n);

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold tracking-tight">Sản phẩm</h1>
          <p className="text-sm text-muted-foreground mt-1">{products.length} sản phẩm</p>
        </div>
        <Button onClick={() => { setEditing(null); setDialogOpen(true); }}>
          <Plus className="w-4 h-4 mr-2" /> Thêm sản phẩm
        </Button>
      </div>

      <div className="relative max-w-sm">
        <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-muted-foreground" />
        <Input placeholder="Tìm kiếm sản phẩm..." value={search} onChange={e => setSearch(e.target.value)} className="pl-9" />
      </div>

      {isLoading ? (
        <div className="flex justify-center py-12"><div className="w-6 h-6 border-2 border-primary border-t-transparent rounded-full animate-spin" /></div>
      ) : filtered.length === 0 ? (
        <p className="text-center text-muted-foreground py-12">Không tìm thấy sản phẩm nào</p>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-4">
          {filtered.map(p => (
            <Card key={p.id} className="hover:shadow-md transition-shadow group">
              <CardContent className="pt-6">
                <div className="flex gap-4">
                  {p.image_url || p.imageUrl ? (
                    <img src={p.image_url || p.imageUrl} alt={p.name} className="w-16 h-16 rounded-lg object-cover bg-muted" />
                  ) : (
                    <div className="w-16 h-16 rounded-lg bg-muted flex items-center justify-center text-muted-foreground text-xs">No img</div>
                  )}
                  <div className="flex-1 min-w-0">
                    <p className="font-semibold text-sm truncate">{p.name}</p>
                    <p className="text-xs text-muted-foreground">{p.brand} {p.storage && `· ${p.storage}`}</p>
                    <p className="text-sm font-bold text-primary mt-1">{fmt(p.price)}đ</p>
                  </div>
                </div>
                <div className="flex items-center justify-between mt-4">
                  <div className="flex items-center gap-2">
                    <Badge variant={statusVariants[p.status] || "default"}>{statusLabels[p.status] || p.status}</Badge>
                    <span className="text-xs text-muted-foreground">Kho: {p.stock}</span>
                  </div>
                  <div className="flex gap-1 opacity-0 group-hover:opacity-100 transition-opacity">
                    <Button size="icon" variant="ghost" className="h-8 w-8" onClick={() => { setEditing(p); setDialogOpen(true); }}>
                      <Pencil className="w-3.5 h-3.5" />
                    </Button>
                    <Button size="icon" variant="ghost" className="h-8 w-8 text-destructive" onClick={() => setDeleting(p)}>
                      <Trash2 className="w-3.5 h-3.5" />
                    </Button>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}

      <ProductDialog open={dialogOpen} onOpenChange={setDialogOpen} product={editing} />

      <AlertDialog open={!!deleting} onOpenChange={() => setDeleting(null)}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Xóa sản phẩm?</AlertDialogTitle>
            <AlertDialogDescription>Bạn có chắc muốn xóa "{deleting?.name}"? Hành động này không thể hoàn tác.</AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Hủy</AlertDialogCancel>
            <AlertDialogAction onClick={() => deleteMutation.mutate(deleting.id)} className="bg-destructive text-destructive-foreground hover:bg-destructive/90">Xóa</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  );
}