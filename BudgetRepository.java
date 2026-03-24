/* ── Google Fonts ─────────────────────────────────────────── */
@import url('https://fonts.googleapis.com/css2?family=Syne:wght@400;500;600;700;800&family=DM+Sans:ital,opsz,wght@0,9..40,300;0,9..40,400;0,9..40,500;1,9..40,300&display=swap');

/* ── Design Tokens ────────────────────────────────────────── */
:root {
  --bg:        #0c0f1a;
  --bg2:       #131726;
  --bg3:       #1a2035;
  --border:    #252d45;
  --text:      #e2e8f5;
  --muted:     #8493b0;
  --accent:    #6c63ff;
  --accent2:   #a78bfa;
  --green:     #22c55e;
  --red:       #ef4444;
  --orange:    #f97316;
  --card-r:    14px;
  --transition: 0.2s ease;
}

/* ── Reset ────────────────────────────────────────────────── */
*, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
html { scroll-behavior: smooth; }
body {
  font-family: 'DM Sans', sans-serif;
  background: var(--bg);
  color: var(--text);
  min-height: 100vh;
  font-size: 15px;
  line-height: 1.6;
}

/* ── Layout ───────────────────────────────────────────────── */
.app { display: flex; min-height: 100vh; }

/* ── Sidebar ──────────────────────────────────────────────── */
.sidebar {
  width: 240px;
  background: var(--bg2);
  border-right: 1px solid var(--border);
  display: flex;
  flex-direction: column;
  padding: 0;
  position: fixed;
  top: 0; left: 0; bottom: 0;
  z-index: 100;
}
.sidebar__logo {
  padding: 24px 20px 20px;
  border-bottom: 1px solid var(--border);
}
.sidebar__logo h1 {
  font-family: 'Syne', sans-serif;
  font-size: 1.2rem;
  font-weight: 800;
  letter-spacing: -0.02em;
  color: var(--text);
}
.sidebar__logo span { color: var(--accent2); }
.sidebar__logo p { font-size: 0.75rem; color: var(--muted); margin-top: 2px; }

.sidebar__nav { flex: 1; padding: 16px 12px; display: flex; flex-direction: column; gap: 4px; }
.nav-item {
  display: flex; align-items: center; gap: 10px;
  padding: 10px 12px;
  border-radius: 8px;
  cursor: pointer;
  color: var(--muted);
  font-size: 0.875rem;
  font-weight: 500;
  transition: var(--transition);
  border: none; background: none; width: 100%; text-align: left;
  text-decoration: none;
}
.nav-item svg { flex-shrink: 0; }
.nav-item:hover { background: var(--bg3); color: var(--text); }
.nav-item.active { background: rgba(108,99,255,0.15); color: var(--accent2); }
.nav-item.active svg { color: var(--accent); }

.sidebar__footer {
  padding: 16px 20px;
  border-top: 1px solid var(--border);
  font-size: 0.75rem;
  color: var(--muted);
}

/* ── Main Content ─────────────────────────────────────────── */
.main { margin-left: 240px; flex: 1; display: flex; flex-direction: column; min-height: 100vh; }
.topbar {
  padding: 20px 32px;
  border-bottom: 1px solid var(--border);
  background: var(--bg);
  display: flex; align-items: center; justify-content: space-between;
  position: sticky; top: 0; z-index: 50;
}
.topbar__title { font-family: 'Syne', sans-serif; font-size: 1.3rem; font-weight: 700; }
.topbar__actions { display: flex; gap: 10px; align-items: center; }

.content { padding: 28px 32px; flex: 1; }

/* ── Page Sections ────────────────────────────────────────── */
.page { display: none; }
.page.active { display: block; }

/* ── Cards ────────────────────────────────────────────────── */
.card {
  background: var(--bg2);
  border: 1px solid var(--border);
  border-radius: var(--card-r);
  padding: 20px;
}

/* ── Stat Cards ───────────────────────────────────────────── */
.stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 16px; margin-bottom: 24px; }
.stat-card {
  background: var(--bg2);
  border: 1px solid var(--border);
  border-radius: var(--card-r);
  padding: 20px;
  position: relative;
  overflow: hidden;
}
.stat-card::before {
  content: '';
  position: absolute; top: 0; left: 0; right: 0; height: 3px;
  background: var(--accent);
}
.stat-card--green::before { background: var(--green); }
.stat-card--orange::before { background: var(--orange); }
.stat-card--purple::before { background: var(--accent2); }
.stat-card__label { font-size: 0.75rem; color: var(--muted); font-weight: 500; text-transform: uppercase; letter-spacing: 0.06em; }
.stat-card__value { font-family: 'Syne', sans-serif; font-size: 1.6rem; font-weight: 700; margin-top: 6px; }
.stat-card__sub { font-size: 0.8rem; color: var(--muted); margin-top: 4px; }

/* ── Buttons ──────────────────────────────────────────────── */
.btn {
  display: inline-flex; align-items: center; gap: 6px;
  padding: 8px 16px;
  border-radius: 8px;
  border: none; cursor: pointer;
  font-family: 'DM Sans', sans-serif;
  font-size: 0.875rem;
  font-weight: 500;
  transition: var(--transition);
}
.btn--primary { background: var(--accent); color: #fff; }
.btn--primary:hover { background: #5a52e0; }
.btn--outline { background: transparent; border: 1px solid var(--border); color: var(--text); }
.btn--outline:hover { background: var(--bg3); }
.btn--danger { background: rgba(239,68,68,0.12); color: var(--red); }
.btn--danger:hover { background: rgba(239,68,68,0.2); }
.btn--sm { padding: 5px 10px; font-size: 0.8rem; }
.btn--icon { padding: 7px; border-radius: 6px; background: var(--bg3); border: 1px solid var(--border); color: var(--muted); }
.btn--icon:hover { color: var(--text); }

/* ── Forms ────────────────────────────────────────────────── */
.form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
.form-group { display: flex; flex-direction: column; gap: 6px; }
.form-group--full { grid-column: 1 / -1; }
label { font-size: 0.8rem; font-weight: 500; color: var(--muted); }
input, select, textarea {
  background: var(--bg3);
  border: 1px solid var(--border);
  border-radius: 8px;
  padding: 9px 12px;
  color: var(--text);
  font-family: 'DM Sans', sans-serif;
  font-size: 0.875rem;
  outline: none;
  transition: var(--transition);
  width: 100%;
}
input:focus, select:focus, textarea:focus { border-color: var(--accent); }
textarea { resize: vertical; min-height: 72px; }
select option { background: var(--bg2); }

/* ── Table ────────────────────────────────────────────────── */
.table-wrap { overflow-x: auto; }
table { width: 100%; border-collapse: collapse; font-size: 0.875rem; }
thead th {
  text-align: left; padding: 10px 14px;
  font-size: 0.75rem; font-weight: 600;
  color: var(--muted); text-transform: uppercase; letter-spacing: 0.06em;
  border-bottom: 1px solid var(--border);
  white-space: nowrap;
}
tbody tr { border-bottom: 1px solid rgba(37,45,69,0.5); transition: var(--transition); }
tbody tr:hover { background: rgba(255,255,255,0.03); }
tbody tr:last-child { border-bottom: none; }
td { padding: 12px 14px; vertical-align: middle; }

/* ── Badge ────────────────────────────────────────────────── */
.badge {
  display: inline-block;
  padding: 3px 10px;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
  letter-spacing: 0.02em;
}

/* ── Modal ────────────────────────────────────────────────── */
.modal-overlay {
  position: fixed; inset: 0;
  background: rgba(12,15,26,0.85);
  backdrop-filter: blur(4px);
  z-index: 200;
  display: none;
  align-items: center;
  justify-content: center;
  padding: 20px;
}
.modal-overlay.open { display: flex; }
.modal {
  background: var(--bg2);
  border: 1px solid var(--border);
  border-radius: 16px;
  width: 100%;
  max-width: 560px;
  max-height: 90vh;
  overflow-y: auto;
}
.modal__header {
  padding: 20px 24px 16px;
  border-bottom: 1px solid var(--border);
  display: flex; align-items: center; justify-content: space-between;
}
.modal__title { font-family: 'Syne', sans-serif; font-size: 1.1rem; font-weight: 700; }
.modal__body { padding: 24px; }
.modal__footer {
  padding: 16px 24px;
  border-top: 1px solid var(--border);
  display: flex; gap: 10px; justify-content: flex-end;
}

/* ── Search bar ───────────────────────────────────────────── */
.search-bar {
  display: flex; align-items: center; gap: 8px;
  background: var(--bg3);
  border: 1px solid var(--border);
  border-radius: 8px;
  padding: 0 12px;
}
.search-bar input {
  background: none; border: none; padding: 9px 4px;
  flex: 1; min-width: 180px;
}
.search-bar svg { color: var(--muted); flex-shrink: 0; }

/* ── Chart containers ─────────────────────────────────────── */
.chart-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 24px; }
.chart-card { background: var(--bg2); border: 1px solid var(--border); border-radius: var(--card-r); padding: 20px; }
.chart-card h3 { font-family: 'Syne', sans-serif; font-size: 0.95rem; font-weight: 700; margin-bottom: 16px; }
canvas { max-height: 260px; }

/* ── Budget progress ──────────────────────────────────────── */
.budget-list { display: flex; flex-direction: column; gap: 16px; }
.budget-item { background: var(--bg2); border: 1px solid var(--border); border-radius: var(--card-r); padding: 16px 20px; }
.budget-item__header { display: flex; align-items: center; justify-content: space-between; margin-bottom: 10px; }
.budget-item__name { font-weight: 600; }
.budget-item__amounts { font-size: 0.82rem; color: var(--muted); }
.progress-bar { height: 6px; background: var(--bg3); border-radius: 4px; overflow: hidden; }
.progress-bar__fill { height: 100%; border-radius: 4px; transition: width 0.6s ease; }

/* ── Empty state ──────────────────────────────────────────── */
.empty {
  text-align: center; padding: 52px 20px; color: var(--muted);
}
.empty svg { opacity: 0.3; margin-bottom: 12px; }
.empty p { font-size: 0.9rem; }

/* ── Toast ────────────────────────────────────────────────── */
.toast {
  position: fixed; bottom: 28px; right: 28px;
  padding: 12px 20px;
  border-radius: 10px;
  font-size: 0.875rem; font-weight: 500;
  background: var(--bg3); border: 1px solid var(--border);
  color: var(--text);
  transform: translateY(16px); opacity: 0;
  transition: all 0.3s ease;
  z-index: 999;
  pointer-events: none;
}
.toast.show { transform: translateY(0); opacity: 1; }
.toast--success { background: rgba(34,197,94,0.15); border-color: rgba(34,197,94,0.3); color: var(--green); }
.toast--error { background: rgba(239,68,68,0.15); border-color: rgba(239,68,68,0.3); color: var(--red); }

/* ── Filters row ──────────────────────────────────────────── */
.filters { display: flex; gap: 10px; flex-wrap: wrap; align-items: center; margin-bottom: 20px; }
.filters select, .filters input { width: auto; }

/* ── Loader ───────────────────────────────────────────────── */
.loader {
  display: flex; align-items: center; justify-content: center; padding: 40px;
  color: var(--muted); font-size: 0.9rem; gap: 10px;
}
.spinner {
  width: 20px; height: 20px; border-radius: 50%;
  border: 2px solid var(--border); border-top-color: var(--accent);
  animation: spin 0.7s linear infinite;
}
@keyframes spin { to { transform: rotate(360deg); } }

/* ── Scrollbar ────────────────────────────────────────────── */
::-webkit-scrollbar { width: 6px; height: 6px; }
::-webkit-scrollbar-track { background: var(--bg); }
::-webkit-scrollbar-thumb { background: var(--border); border-radius: 4px; }

/* ── Responsive ───────────────────────────────────────────── */
@media (max-width: 900px) {
  .sidebar { width: 200px; }
  .main { margin-left: 200px; }
  .chart-grid { grid-template-columns: 1fr; }
}
@media (max-width: 680px) {
  .sidebar { display: none; }
  .main { margin-left: 0; }
  .content { padding: 20px 16px; }
  .form-grid { grid-template-columns: 1fr; }
  .stats-grid { grid-template-columns: 1fr 1fr; }
}
