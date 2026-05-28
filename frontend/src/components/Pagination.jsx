export default function Pagination({ currentPage, totalPages, onPageChange }) {
  const pages = [];
  for (let i = 0; i < totalPages; i++) pages.push(i);

  return (
    <div style={{ marginTop: '2rem', textAlign: 'center' }}>
      {pages.map(p => (
        <button key={p} onClick={() => onPageChange(p)} style={{ margin: '0 0.25rem', padding: '0.5rem 1rem', background: p === currentPage ? '#333' : '#ddd', color: p === currentPage ? '#fff' : '#000', border: 'none', cursor: 'pointer' }}>
          {p + 1}
        </button>
      ))}
    </div>
  );
}