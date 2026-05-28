import React from 'react';

function ProtectedRoute({ isAuthenticated, children }) {
  if (!isAuthenticated) {
    return (
      <div className="mx-auto max-w-3xl rounded-3xl bg-slate-900 p-8 text-center text-slate-300">
        <h1 className="text-xl font-semibold">Login required</h1>
        <p className="mt-2 text-slate-500">Please sign in to access this content.</p>
      </div>
    );
  }

  return <>{children}</>;
}

export default ProtectedRoute;
