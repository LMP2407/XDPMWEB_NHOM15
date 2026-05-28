import React from 'react';

function AuthLayout({ children }) {
  return (
    <div className="min-h-screen bg-slate-950 text-slate-100">
      <div className="mx-auto flex min-h-screen max-w-5xl flex-col justify-center px-6 py-12">
        <div className="rounded-3xl border border-slate-800 bg-slate-900/90 p-10 shadow-2xl shadow-slate-950/50">
          {children}
        </div>
      </div>
    </div>
  );
}

export default AuthLayout;
