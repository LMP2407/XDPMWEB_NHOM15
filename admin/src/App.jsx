const cards = [
  { title: 'Products', description: 'View and manage products.' },
  { title: 'Categories', description: 'Browse category metadata.' },
  { title: 'Orders', description: 'Track your orders and status.' }
];

function App() {
  return (
    <div className="min-h-screen bg-slate-950 text-slate-100">
      <main className="mx-auto max-w-5xl p-6">
        <header className="mb-10 border-b border-slate-700 pb-6">
          <p className="text-sm uppercase tracking-[0.3em] text-slate-500">Vite + React + Tailwind</p>
          <h1 className="mt-4 text-4xl font-semibold">Starter project structure</h1>
          <p className="mt-2 max-w-2xl text-slate-400">
            This project includes the app shell, an entities folder, and the standard Vite React source layout.
          </p>
        </header>

        <section className="grid gap-4 md:grid-cols-3">
          {cards.map((card) => (
            <article key={card.title} className="rounded-3xl border border-slate-800 bg-slate-900 p-6 shadow-xl shadow-slate-900/20">
              <h2 className="text-xl font-semibold text-white">{card.title}</h2>
              <p className="mt-3 text-slate-400">{card.description}</p>
            </article>
          ))}
        </section>
      </main>
    </div>
  );
}

export default App;
