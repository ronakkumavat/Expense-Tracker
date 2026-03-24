// ── API Configuration ──────────────────────────────────────
const API_BASE = 'http://localhost:8080/api';

const API = {
  expenses: {
    getAll:        ()          => fetch(`${API_BASE}/expenses`),
    getById:       (id)        => fetch(`${API_BASE}/expenses/${id}`),
    create:        (data)      => fetch(`${API_BASE}/expenses`, { method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify(data) }),
    update:        (id, data)  => fetch(`${API_BASE}/expenses/${id}`, { method:'PUT', headers:{'Content-Type':'application/json'}, body:JSON.stringify(data) }),
    delete:        (id)        => fetch(`${API_BASE}/expenses/${id}`, { method:'DELETE' }),
    getByCategory: (cat)       => fetch(`${API_BASE}/expenses/category/${cat}`),
    getByMonth:    (m, y)      => fetch(`${API_BASE}/expenses/month?month=${m}&year=${y}`),
    search:        (kw)        => fetch(`${API_BASE}/expenses/search?keyword=${encodeURIComponent(kw)}`),
    dashboard:     ()          => fetch(`${API_BASE}/expenses/stats/dashboard`),
    byCategory:    ()          => fetch(`${API_BASE}/expenses/stats/by-category`),
    byMonth:       ()          => fetch(`${API_BASE}/expenses/stats/by-month`),
  },
  budgets: {
    getAll:   ()         => fetch(`${API_BASE}/budgets`),
    create:   (data)     => fetch(`${API_BASE}/budgets`, { method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify(data) }),
    update:   (id, data) => fetch(`${API_BASE}/budgets/${id}`, { method:'PUT', headers:{'Content-Type':'application/json'}, body:JSON.stringify(data) }),
    delete:   (id)       => fetch(`${API_BASE}/budgets/${id}`, { method:'DELETE' }),
  }
};

// ── Helpers ────────────────────────────────────────────────
async function apiFetch(promise) {
  try {
    const res = await promise;
    const data = await res.json().catch(() => null);
    if (!res.ok) throw new Error(data?.message || `HTTP ${res.status}`);
    return { ok: true, data };
  } catch (err) {
    return { ok: false, error: err.message };
  }
}

function formatCurrency(amount) {
  return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', maximumFractionDigits: 0 }).format(amount);
}

function formatDate(dateStr) {
  if (!dateStr) return '-';
  return new Date(dateStr + 'T00:00:00').toLocaleDateString('en-IN', { day:'2-digit', month:'short', year:'numeric' });
}

function showToast(msg, type = 'success') {
  const toast = document.getElementById('toast');
  if (!toast) return;
  toast.textContent = msg;
  toast.className = `toast toast--${type} show`;
  setTimeout(() => toast.classList.remove('show'), 3000);
}

const CATEGORIES = ['Food', 'Transport', 'Utilities', 'Entertainment', 'Shopping', 'Health', 'Education', 'Travel', 'Other'];
const PAYMENT_METHODS = ['Cash', 'UPI', 'Card', 'Net Banking', 'Wallet'];

const CATEGORY_COLORS = {
  Food: '#f97316', Transport: '#3b82f6', Utilities: '#8b5cf6',
  Entertainment: '#ec4899', Shopping: '#14b8a6', Health: '#22c55e',
  Education: '#eab308', Travel: '#06b6d4', Other: '#94a3b8'
};
