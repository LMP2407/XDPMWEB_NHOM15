import { useState } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import categoryApi from "@/api/categoryApi";
import { Plus, Pencil, Trash2 } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent } from "@/components/ui/card";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle } from "@/components/ui/alert-dialog";
import { Textarea } from "@/components/ui/textarea";

export default function Categories() {
  const [dialogOpen, setDialogOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [deleting, setDeleting] = useState(null);
  const [form, setForm] = useState({ name: "", description: "" });
  const queryClient = useQueryClient();

  const { data: res, isLoading } = useQuery({ queryKey: ["categories"], queryFn: () => categoryApi.getAll() });
  const categories = res?.data?.content || res?.data || [];

  const saveMutation = useMutation({
    mutationFn: (data) => editing ? categoryApi.update(editing.id, data) : categoryApi.create(data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ["categories"] }); setDialogOpen(false); },
  });

  const deleteMutation = useMutation({
    mutationFn: (id) => categoryApi.delete(id),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ["categories"] }); setDeleting(null); },
  });

  const openAdd = () => { setEditing(null); setForm({ name: "", description: "" }); setDialogOpen(true); };
  const openEdit = (c) => { setEditing(c); setForm({ name: c.name, description: c.description || "" }); setDialogOpen(true); };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold tracking-tight">Danh mục</h1>
          <p className="text-sm text-muted-foreground mt-1">{categories.length} danh mục</p>
        </div>
        <Button onClick={openAdd}><Plus className="w-4 h-4 mr-2" /> Thêm danh mục</Button>
      </div>

      {isLoading ? (
        <div className="flex justify-center py-12"><div className="w-6 h-6 border-2 border-primary border-t-transparent rounded-full animate-spin" /></div>
      ) : categories.length === 0 ? (
        <p className="text-center text-muted-foreground py-12">Chưa có danh mục nào</p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
          {categories.map(c => (
            <Card key={c.id} className="group hover:shadow-md transition-shadow">
              <CardContent className="pt-6">
                <div className="flex items-start justify-between">
                  <div>
                    <p className="font-semibold">{c.name}</p>
                    {c.description && <p className="text-sm text-muted-foreground mt-1">{c.description}</p>}
                  </div>
                  <div className="flex gap-1 opacity-0 group-hover:opacity-100 transition-opacity">
                    <Button size="icon" variant="ghost" className="h-8 w-8" onClick={() => openEdit(c)}><Pencil className="w-3.5 h-3.5" /></Button>
                    <Button size="icon" variant="ghost" className="h-8 w-8 text-destructive" onClick={() => setDeleting(c)}><Trash2 className="w-3.5 h-3.5" /></Button>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}

      <Dialog open={dialogOpen} onOpenChange={setDialogOpen}>
        <DialogContent className="max-w-sm">
          <DialogHeader><DialogTitle>{editing ? "Sửa danh mục" : "Thêm danh mục"}</DialogTitle></DialogHeader>
          <form onSubmit={e => { e.preventDefault(); saveMutation.mutate(form); }} className="space-y-4">
            <div><Label>Tên danh mục *</Label><Input value={form.name} onChange={e => setForm(f => ({ ...f, name: e.target.value }))} required /></div>
            <div><Label>Mô tả</Label><Textarea value={form.description} onChange={e => setForm(f => ({ ...f, description: e.target.value }))} rows={2} /></div>
            <div className="flex justify-end gap-2">
              <Button type="button" variant="outline" onClick={() => setDialogOpen(false)}>Hủy</Button>
              <Button type="submit" disabled={saveMutation.isPending}>{editing ? "Cập nhật" : "Thêm"}</Button>
            </div>
          </form>
        </DialogContent>
      </Dialog>

      <AlertDialog open={!!deleting} onOpenChange={() => setDeleting(null)}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Xóa danh mục?</AlertDialogTitle>
            <AlertDialogDescription>Bạn có chắc muốn xóa "{deleting?.name}"?</AlertDialogDescription>
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