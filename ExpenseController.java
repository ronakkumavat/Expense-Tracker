// ── State ──────────────────────────────────────────────────
let allExpenses = [];
let editingExpenseId = null;
let editingBudgetId = null;
let pieChart = null, barChart = null;

// ── Navigation ─────────────────────────────────────────────
function navigate(page) {
  document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
  document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));
  document.getElementById(`page-${page}`)?.classList.add('active');
  document.querySelector(`[data-page="${page}"]`)?.classList.add('active');
  document.querySelector('.topbar__title').textContent =
    page === 'dashboard' ? 'Dashboard' :
    page === 'expenses'  ? 'Expenses' :
    page === 'budgets'   ? 'Budgets' : 'Reports';

  if (page === 'dashboard') loadDashboard();
  if (page === 'expenses')  loadExpenses();
  if (page === 'budgets')   loadBudgets();
  if (page === 'reports')   loadReports();
}

// ── Dashboard ──────────────────────────────────────────────
async function loadDashboard() {
  const res = await apiFetch(API.expenses.dashboard());
  if (!res.ok) return;

  const { totalAllTime, totalThisMonth, categoryBreakdown, recentExpenses } = res.data;

  document.getElementById('stat-total').textContent = formatCurrency(totalAllTime);
  document.getElementById('stat-month').textContent = formatCurrency(totalThisMonth);
  document.getElementById('stat-count').textContent = allExpenses.length + ' entries';

  // Category breakdown list
  const catEl = document.getElementById('category-breakdown');
  catEl.innerHTML = '';
  const entries = Object.entries(categoryBreakdown);
  const max = Math.max(...entries.map(([,v]) => Number(v)), 1);
  entries.sort(([,a],[,b]) => b - a).forEach(([cat, amount]) => {
    const pct = Math.round((Number(amount) / Number(totalAllTime || 1)) * 100);
    const color = CATEGORY_COLORS[cat] || '#94a3b8';
    catEl.innerHTML += `
      <div style="display:flex;align-items:center;gap:10px;margin-bottom:12px">
        <span class="badge" style="background:${color}22;color:${color};min-width:90px;text-align:center">${cat}</span>
        <div style="flex:1;background:var(--bg3);height:6px;border-radius:4px;overflow:hidden">
          <div style="width:${(Number(amount)/max)*100}%;height:100%;background:${color};border-radius:4px"></div>
        </div>
        <span style="font-size:0.8rem;font-weight:600;min-width:80px;text-align:right">${formatCurrency(amount)}</span>
        <span style="font-size:0.75rem;color:var(--muted);min-width:32px">${pct}%</span>
      </div>`;
  });

  // Recent expenses
  const recentEl = document.getElementById('recent-list');
  recentEl.innerHTML = '';
  if (!recentExpenses?.length) {
    recentEl.innerHTML = '<tr><td colspan="4" class="empty"><p>No recent expenses</p></td></tr>';
    return;
  }
  recentExpenses.forEach(e => {
    const color = CATEGORY_COLORS[e.category] || '#94a3b8';
    recentEl.innerHTML += `
      <tr>
        <td><div style="font-weight:500">${e.title}</div><div style="font-size:0.75rem;color:var(--muted)">${e.description||''}</div></td>
        <td><span class="badge" style="background:${color}22;color:${color}">${e.category}</span></td>
        <td style="color:var(--orange);font-weight:600">${formatCurrency(e.amount)}</td>
        <td style="color:var(--muted)">${formatDate(e.date)}</td>
      </tr>`;
  });
}

// ── Expenses Page ──────────────────────────────────────────
async function loadExpenses(filter = {}) {
  document.getElementById('expense-tbody').innerHTML = '<tr><td colspan="7"><div class="loader"><div class="spinner"></div>Loading...</div></td></tr>';

  let res;
  if (filter.keyword) res = await apiFetch(API.expenses.search(filter.keyword));
  else if (filter.category && filter.category !== 'all') res = await apiFetch(API.expenses.getByCategory(filter.category));
  else res = await apiFetch(API.expenses.getAll());

  if (!res.ok) {
    showToast('Failed to load expenses', 'error');
    return;
  }

  allExpenses = res.data;
  renderExpenseTable(allExpenses);
}

function renderExpenseTable(expenses) {
  const tbody = document.getElementById('expense-tbody');
  if (!expenses.length) {
    tbody.innerHTML = `<tr><td colspan="7"><div class="empty"><svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1"><path d="M3 3h18l-2 13H5L3 3z"/><path d="M16 16a4 4 0 0 1-8 0"/></svg><p>No expenses found</p></div></td></tr>`;
    return;
  }
  tbody.innerHTML = expenses.map(e => {
    const color = CATEGORY_COLORS[e.category] || '#94a3b8';
    return `
      <tr>
        <td><div style="font-weight:500">${e.title}</div></td>
        <td style="color:var(--muted);font-size:0.82rem">${e.description || '-'}</td>
        <td style="font-weight:600;color:var(--orange)">${formatCurrency(e.amount)}</td>
        <td>${formatDate(e.date)}</td>
        <td><span class="badge" style="background:${color}22;color:${color}">${e.category}</span></td>
        <td style="color:var(--muted)">${e.paymentMethod || '-'}</td>
        <td>
          <div style="display:flex;gap:6px">
            <button class="btn btn--icon btn--sm" onclick="openEditExpense(${e.id})" title="Edit">
              <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg>
            </button>
            <button class="btn btn--icon btn--sm" onclick="deleteExpense(${e.id})" title="Delete" style="color:var(--red)">
              <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14H6L5 6"/><path d="M10 11v6M14 11v6"/></svg>
            </button>
          </div>
        </td>
      </tr>`;
  }).join('');
}

// ── Expense Modal ──────────────────────────────────────────
function openAddExpense() {
  editingExpenseId = null;
  document.getElementById('expense-modal-title').textContent = 'Add Expense';
  document.getElementById('expense-form').reset();
  document.getElementById('expense-date').value = new Date().toISOString().split('T')[0];
  document.getElementById('expense-modal').classList.add('open');
}

async function openEditExpense(id) {
  editingExpenseId = id;
  const expense = allExpenses.find(e => e.id === id);
  if (!expense) return;
  document.getElementById('expense-modal-title').textContent = 'Edit Expense';
  document.getElementById('expense-title').value = expense.title;
  document.getElementById('expense-desc').value = expense.description || '';
  document.getElementById('expense-amount').value = expense.amount;
  document.getElementById('expense-date').value = expense.date;
  document.getElementById('expense-category').value = expense.category;
  document.getElementById('expense-payment').value = expense.paymentMethod || '';
  document.getElementById('expense-tags').value = expense.tags || '';
  document.getElementById('expense-modal').classList.add('open');
}

function closeExpenseModal() {
  document.getElementById('expense-modal').classList.remove('open');
}

async function saveExpense() {
  const data = {
    title:         document.getElementById('expense-title').value.trim(),
    description:   document.getElementById('expense-desc').value.trim(),
    amount:        parseFloat(document.getElementById('expense-amount').value),
    date:          document.getElementById('expense-date').value,
    category:      document.getElementById('expense-category').value,
    paymentMethod: document.getElementById('expense-payment').value,
    tags:          document.getElementById('expense-tags').value.trim(),
  };

  if (!data.title || !data.amount || !data.date || !data.category) {
    showToast('Please fill in all required fields', 'error'); return;
  }

  const promise = editingExpenseId
    ? API.expenses.update(editingExpenseId, data)
    : API.expenses.create(data);

  const res = await apiFetch(promise);
  if (!res.ok) { showToast(res.error || 'Failed to save expense', 'error'); return; }
  showToast(editingExpenseId ? 'Expense updated!' : 'Expense added!');
  closeExpenseModal();
  loadExpenses();
}

async function deleteExpense(id) {
  if (!confirm('Delete this expense?')) return;
  const res = await apiFetch(API.expenses.delete(id));
  if (!res.ok) { showToast('Failed to delete', 'error'); return; }
  showToast('Expense deleted');
  loadExpenses();
}

// ── Budget Page ────────────────────────────────────────────
async function loadBudgets() {
  const [budgRes, expRes] = await Promise.all([
    apiFetch(API.budgets.getAll()),
    apiFetch(API.expenses.byCategory()),
  ]);
  if (!budgRes.ok) return;

  const budgets = budgRes.data;
  const spent   = expRes.ok ? expRes.data : {};

  const list = document.getElementById('budget-list');
  if (!budgets.length) {
    list.innerHTML = `<div class="empty"><p>No budgets set. Add one to start tracking!</p></div>`;
    return;
  }

  list.innerHTML = budgets.map(b => {
    const spentAmt = Number(spent[b.category] || 0);
    const limit    = Number(b.budgetLimit);
    const pct      = Math.min((spentAmt / limit) * 100, 100).toFixed(1);
    const color    = CATEGORY_COLORS[b.category] || '#94a3b8';
    const barColor = pct >= 100 ? 'var(--red)' : pct >= 80 ? 'var(--orange)' : color;
    return `
      <div class="budget-item">
        <div class="budget-item__header">
          <div>
            <span class="badge" style="background:${color}22;color:${color};margin-right:8px">${b.category}</span>
            <span class="budget-item__name">${b.category} Budget</span>
          </div>
          <div style="display:flex;align-items:center;gap:8px">
            <span class="budget-item__amounts">${formatCurrency(spentAmt)} / ${formatCurrency(limit)}</span>
            <button class="btn btn--icon btn--sm" onclick="deleteBudget(${b.id})" style="color:var(--red)">
              <svg xmlns="http://www.w3.org/2000/svg" width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14H6L5 6"/></svg>
            </button>
          </div>
        </div>
        <div class="progress-bar">
          <div class="progress-bar__fill" style="width:${pct}%;background:${barColor}"></div>
        </div>
        <div style="display:flex;justify-content:space-between;margin-top:6px;font-size:0.78rem;color:var(--muted)">
          <span>${pct}% used</span>
          <span>${pct >= 100 ? '⚠ Over budget!' : `${formatCurrency(limit - spentAmt)} remaining`}</span>
        </div>
      </div>`;
  }).join('');
}

function openAddBudget() {
  document.getElementById('budget-form').reset();
  document.getElementById('budget-modal').classList.add('open');
}
function closeBudgetModal() {
  document.getElementById('budget-modal').classList.remove('open');
}
async function saveBudget() {
  const data = {
    category:    document.getElementById('budget-category').value,
    budgetLimit: parseFloat(document.getElementById('budget-limit').value),
    monthYear:   document.getElementById('budget-month').value,
  };
  if (!data.category || !data.budgetLimit) { showToast('Fill required fields', 'error'); return; }
  const res = await apiFetch(API.budgets.create(data));
  if (!res.ok) { showToast(res.error || 'Failed to save budget', 'error'); return; }
  showToast('Budget saved!');
  closeBudgetModal();
  loadBudgets();
}
async function deleteBudget(id) {
  if (!confirm('Delete this budget?')) return;
  const res = await apiFetch(API.budgets.delete(id));
  if (!res.ok) { showToast('Failed to delete', 'error'); return; }
  showToast('Budget deleted');
  loadBudgets();
}

// ── Reports ────────────────────────────────────────────────
async function loadReports() {
  const [catRes, monthRes] = await Promise.all([
    apiFetch(API.expenses.byCategory()),
    apiFetch(API.expenses.byMonth()),
  ]);

  // Pie chart
  if (catRes.ok && pieChart === null) {
    const ctx = document.getElementById('pie-chart').getContext('2d');
    const labels = Object.keys(catRes.data);
    const values = Object.values(catRes.data).map(Number);
    const colors = labels.map(l => CATEGORY_COLORS[l] || '#94a3b8');
    pieChart = new Chart(ctx, {
      type: 'doughnut',
      data: { labels, datasets: [{ data: values, backgroundColor: colors, borderWidth: 0, hoverOffset: 8 }] },
      options: {
        plugins: { legend: { position: 'right', labels: { color: '#8493b0', font: { family: 'DM Sans' } } } },
        cutout: '65%',
      }
    });
  }

  // Bar chart
  if (monthRes.ok && barChart === null) {
    const ctx2 = document.getElementById('bar-chart').getContext('2d');
    const months = monthRes.data.slice(0, 6).reverse();
    const monthNames = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
    barChart = new Chart(ctx2, {
      type: 'bar',
      data: {
        labels: months.map(m => `${monthNames[m.month-1]} ${m.year}`),
        datasets: [{
          label: 'Total Expenses',
          data: months.map(m => Number(m.total)),
          backgroundColor: 'rgba(108,99,255,0.6)',
          borderRadius: 6,
          borderSkipped: false,
        }]
      },
      options: {
        plugins: { legend: { display: false } },
        scales: {
          x: { ticks: { color: '#8493b0' }, grid: { color: 'rgba(37,45,69,0.5)' } },
          y: { ticks: { color: '#8493b0', callback: v => '₹' + (v/1000).toFixed(0) + 'k' }, grid: { color: 'rgba(37,45,69,0.5)' } }
        }
      }
    });
  }
}

// ── Init ───────────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => {
  // Populate category selects
  const catSelects = document.querySelectorAll('.cat-select');
  catSelects.forEach(sel => {
    CATEGORIES.forEach(c => sel.innerHTML += `<option value="${c}">${c}</option>`);
  });

  // Populate payment selects
  const paySelects = document.querySelectorAll('.pay-select');
  paySelects.forEach(sel => {
    sel.innerHTML += PAYMENT_METHODS.map(p => `<option value="${p}">${p}</option>`).join('');
  });

  // Nav clicks
  document.querySelectorAll('.nav-item').forEach(item => {
    item.addEventListener('click', () => navigate(item.dataset.page));
  });

  // Search
  let searchTimer;
  document.getElementById('search-input')?.addEventListener('input', e => {
    clearTimeout(searchTimer);
    searchTimer = setTimeout(() => {
      loadExpenses({ keyword: e.target.value });
    }, 350);
  });

  // Filter
  document.getElementById('filter-category')?.addEventListener('change', e => {
    loadExpenses({ category: e.target.value });
  });

  // Budget month default
  const bm = document.getElementById('budget-month');
  if (bm) bm.value = new Date().toISOString().slice(0, 7);

  navigate('dashboard');
});
