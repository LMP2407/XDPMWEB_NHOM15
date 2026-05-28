import React from 'react';

function RecentOrders({ orders = [] }) {
  return (
    <section className="rounded-3xl bg-white/5 p-6 shadow-sm shadow-slate-900/20 text-slate-100">
      <h2 className="text-xl font-semibold">Recent Orders</h2>
      <div className="mt-4 space-y-3">
        {orders.length === 0 ? (
          <p className="text-sm text-slate-400">No orders available.</p>
        ) : (
          orders.map((order) => (
            <div key={order.id} className="rounded-2xl border border-slate-800 p-4">
              <p className="font-medium">Order #{order.id}</p>
              <p className="text-sm text-slate-400">Status: {order.status}</p>
            </div>
          ))
        )}
      </div>
    </section>
  );
}

export default RecentOrders;
