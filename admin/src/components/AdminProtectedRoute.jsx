import React from 'react';

function AdminProtectedRoute({ isAdmin, children }) {
  if (!isAdmin) {
    return (
      <div className="mx-auto max-w-3xl rounded-3xl bg-rose-950/80 p-8 text-center text-rose-300">
        <h1 className="text-xl font-semibold">Access denied</h1>
        <p className="mt-2 text-slate-400">You must be an administrator to view this page.</p>
      </div>
    );
  }

  return <>{children}</>;
}

export default AdminProtectedRoute;
