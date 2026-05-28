import React from 'react';

function StatCard({ label, value, accent }) {
  return (
    <div className="rounded-3xl border border-slate-800 bg-slate-900 p-6 shadow-xl shadow-slate-900/20">
      <p className="text-sm uppercase tracking-[0.2em] text-slate-500">{label}</p>
      <p className={`mt-3 text-3xl font-semibold ${accent || 'text-white'}`}>{value}</p>
    </div>
  );
}

export default StatCard;
