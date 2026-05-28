import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import productApi from "@/api/productApi";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";

const brands = ["Apple", "Samsung", "Xiaomi", "OPPO", "Vivo", "Realme", "OnePlus", "Google", "Huawei", "Khác"];
const storages = ["32GB", "64GB", "128GB", "256GB", "512GB", "1TB"];
const statuses = [
  { value: "active", label: "Đang bán" },
  { value: "inactive", label: "Ngừng bán" },
  { value: "out_of_stock", label: "Hết hàng" },
];

export default function ProductDialog({ open, onOpenChange, product }) {
  const queryClient = useQueryClient();
  const { register, handleSubmit, reset, setValue, watch, formState: { isSubmitting } } = useForm();

  useEffect(() => {
    if (open) reset(product || { status: "active", stock: 0, price: 0, cost_price: 0 });
  }, [open, product]);

  const mutation = useMutation({
    mutationFn: (data) => product ? productApi.update(product.id, data) : productApi.create(data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ["products"] }); onOpenChange(false); },
  });

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="max-w-lg max-h-[90vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle>{product ? "Sửa sản phẩm" : "Thêm sản phẩm mới"}</DialogTitle>
        </DialogHeader>
        <form onSubmit={handleSubmit(d => mutation.mutate(d))} className="space-y-4">
          <div>
            <Label>Tên sản phẩm *</Label>
            <Input {...register("name", { required: true })} placeholder="iPhone 15 Pro Max" />
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label>Thương hiệu *</Label>
              <Select value={watch("brand") || ""} onValueChange={v => setValue("brand", v)}>
                <SelectTrigger><SelectValue placeholder="Chọn" /></SelectTrigger>
                <SelectContent>{brands.map(b => <SelectItem key={b} value={b}>{b}</SelectItem>)}</SelectContent>
              </Select>
            </div>
            <div>
              <Label>Dung lượng</Label>
              <Select value={watch("storage") || ""} onValueChange={v => setValue("storage", v)}>
                <SelectTrigger><SelectValue placeholder="Chọn" /></SelectTrigger>
                <SelectContent>{storages.map(s => <SelectItem key={s} value={s}>{s}</SelectItem>)}</SelectContent>
              </Select>
            </div>
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label>Giá bán (VNĐ) *</Label>
              <Input type="number" {...register("price", { required: true, valueAsNumber: true })} />
            </div>
            <div>
              <Label>Giá nhập (VNĐ)</Label>
              <Input type="number" {...register("cost_price", { valueAsNumber: true })} />
            </div>
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label>Tồn kho *</Label>
              <Input type="number" {...register("stock", { required: true, valueAsNumber: true })} />
            </div>
            <div>
              <Label>Trạng thái</Label>
              <Select value={watch("status") || "active"} onValueChange={v => setValue("status", v)}>
                <SelectTrigger><SelectValue /></SelectTrigger>
                <SelectContent>{statuses.map(s => <SelectItem key={s.value} value={s.value}>{s.label}</SelectItem>)}</SelectContent>
              </Select>
            </div>
          </div>
          <div>
            <Label>Màu sắc</Label>
            <Input {...register("color")} placeholder="Đen, Trắng, Xanh..." />
          </div>
          <div>
            <Label>Link ảnh</Label>
            <Input {...register("image_url")} placeholder="https://..." />
          </div>
          <div>
            <Label>Thông số kỹ thuật</Label>
            <Textarea {...register("specs")} rows={3} placeholder="Chip A17 Pro, RAM 8GB..." />
          </div>
          <div className="flex justify-end gap-2 pt-2">
            <Button type="button" variant="outline" onClick={() => onOpenChange(false)}>Hủy</Button>
            <Button type="submit" disabled={isSubmitting || mutation.isPending}>{product ? "Cập nhật" : "Thêm mới"}</Button>
          </div>
        </form>
      </DialogContent>
    </Dialog>
  );
}