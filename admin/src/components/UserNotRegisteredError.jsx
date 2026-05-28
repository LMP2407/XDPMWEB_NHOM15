import React from 'react';

function UserNotRegisteredError({ message = 'Your account is not registered.' }) {
  return (
    <div className="rounded-3xl border border-rose-900 bg-rose-950/80 p-6 text-rose-200 shadow-lg shadow-rose-950/20">
      <h2 className="text-xl font-semibold">Account not registered</h2>
      <p className="mt-3 text-slate-300">{message}</p>
    </div>
  );
}

export default UserNotRegisteredError;
