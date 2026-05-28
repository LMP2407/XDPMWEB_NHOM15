import React from 'react';

function OrderDetailDialog({ order, onClose }) {
  if (!order) {
    return null;
  }

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-950/70 p-4">
      <div className="w-full max-w-2xl rounded-3xl bg-slate-900 p-6 shadow-2xl shadow-black/40">
        <div className="flex items-center justify-between">
          <h2 className="text-2xl font-semibold">Order Detail</h2>
          <button onClick={onClose} className="text-slate-400 hover:text-white">
            Close
          </button>
        </div>
        <div className="mt-5 space-y-3 text-slate-300">
          <p>Order ID: {order.id}</p>
          <p>Status: {order.status}</p>
          <p>Created At: {order.createdAt}</p>
        </div>
      </div>
    </div>
  );
}

export default OrderDetailDialog;
