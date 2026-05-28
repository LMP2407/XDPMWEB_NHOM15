import { Navigate, Outlet } from "react-router-dom";

export default function AdminProtectedRoute() {
  const token = localStorage.getItem("accessToken");
  if (!token) return <Navigate to="/login" replace />;
  return <Outlet />;
}