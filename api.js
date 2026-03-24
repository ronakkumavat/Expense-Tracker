<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>ExpenseTracker — Smart Money Management</title>
  <link rel="stylesheet" href="css/style.css"/>
  <!-- Chart.js -->
  <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js"></script>
</head>
<body>
<div class="app">

  <!-- ── Sidebar ──────────────────────────────────────────── -->
  <aside class="sidebar">
    <div class="sidebar__logo">
      <h1>Expense<span>Tracker</span></h1>
      <p>Smart money management</p>
    </div>

    <nav class="sidebar__nav">
      <button class="nav-item active" data-page="dashboard">
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/>
          <rect x="14" y="14" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/>
        </svg>
        Dashboard
      </button>
      <button class="nav-item" data-page="expenses">
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <line x1="12" y1="1" x2="12" y2="23"/><path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>
        </svg>
        Expenses
      </button>
      <button class="nav-item" data-page="budgets">
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M12 2L2 7l10 5 10-5-10-5z"/><path d="M2 17l10 5 10-5"/><path d="M2 12l10 5 10-5"/>
        </svg>
        Budgets
      </button>
      <button class="nav-item" data-page="reports">
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <line x1="18" y1="20" x2="18" y2="10"/><line x1="12" y1="20" x2="12" y2="4"/>
          <line x1="6" y1="20" x2="6" y2="14"/>
        </svg>
        Reports
      </button>
    </nav>

    <div class="sidebar__footer">
      Backend: <span style="color:var(--green)">●</span> localhost:8080<br/>
      Java Spring Boot + MySQL
    </div>
  </aside>

  <!-- ── Main ─────────────────────────────────────────────── -->
  <div class="main">
    <!-- Topbar -->
    <header class="topbar">
      <span class="topbar__title">Dashboard</span>
      <div class="topbar__actions">
        <button class="btn btn--primary" onclick="openAddExpense()">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
            <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
          Add Expense
        </button>
      </div>
    </header>

    <div class="content">

      <!-- ══ Dashboard Page ══════════════════════════════════ -->
      <section id="page-dashboard" class="page active">
        <div class="stats-grid">
          <div class="stat-card">
            <div class="stat-card__label">Total All Time</div>
            <div class="stat-card__value" id="stat-total">—</div>
            <div class="stat-card__sub">Cumulative expenses</div>
          </div>
          <div class="stat-card stat-card--green">
            <div class="stat-card__label">This Month</div>
            <div class="stat-card__value" id="stat-month">—</div>
            <div class="stat-card__sub">Current month spending</div>
          </div>
          <div class="stat-card stat-card--orange">
            <div class="stat-card__label">Transactions</div>
            <div class="stat-card__value" id="stat-count">—</div>
            <div class="stat-card__sub">Total entries</div>
          </div>
        </div>

        <div style="display:grid;grid-template-columns:1fr 1.4fr;gap:20px">
          <div class="card">
            <h3 style="font-family:'Syne',sans-serif;font-weight:700;margin-bottom:16px">Category Breakdown</h3>
            <div id="category-breakdown"><div class="loader"><div class="spinner"></div></div></div>
          </div>
          <div class="card">
            <h3 style="font-family:'Syne',sans-serif;font-weight:700;margin-bottom:16px">Recent Transactions</h3>
            <div class="table-wrap">
              <table>
                <thead><tr><th>Title</th><th>Category</th><th>Amount</th><th>Date</th></tr></thead>
                <tbody id="recent-list"><tr><td colspan="4"><div class="loader"><div class="spinner"></div></div></td></tr></tbody>
              </table>
            </div>
          </div>
        </div>
      </section>

      <!-- ══ Expenses Page ════════════════════════════════════ -->
      <section id="page-expenses" class="page">
        <div class="filters">
          <div class="search-bar">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>
            <input type="text" id="search-input" placeholder="Search expenses…"/>
          </div>
          <select id="filter-category">
            <option value="all">All Categories</option>
          </select>
        </div>

        <div class="card">
          <div class="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>Title</th><th>Description</th><th>Amount</th>
                  <th>Date</th><th>Category</th><th>Payment</th><th>Actions</th>
                </tr>
              </thead>
              <tbody id="expense-tbody">
                <tr><td colspan="7"><div class="loader"><div class="spinner"></div>Loading...</div></td></tr>
              </tbody>
            </table>
          </div>
        </div>
      </section>

      <!-- ══ Budgets Page ════════════════════════════════════ -->
      <section id="page-budgets" class="page">
        <div style="display:flex;justify-content:flex-end;margin-bottom:20px">
          <button class="btn btn--primary" onclick="openAddBudget()">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
            Set Budget
          </button>
        </div>
        <div id="budget-list" class="budget-list">
          <div class="loader"><div class="spinner"></div>Loading budgets...</div>
        </div>
      </section>

      <!-- ══ Reports Page ════════════════════════════════════ -->
      <section id="page-reports" class="page">
        <div class="chart-grid">
          <div class="chart-card">
            <h3>Spending by Category</h3>
            <canvas id="pie-chart"></canvas>
          </div>
          <div class="chart-card">
            <h3>Monthly Spending Trend</h3>
            <canvas id="bar-chart"></canvas>
          </div>
        </div>
      </section>

    </div><!-- /content -->
  </div><!-- /main -->
</div><!-- /app -->

<!-- ── Toast ────────────────────────────────────────────── -->
<div id="toast" class="toast"></div>

<!-- ── Expense Modal ─────────────────────────────────────── -->
<div id="expense-modal" class="modal-overlay">
  <div class="modal">
    <div class="modal__header">
      <span class="modal__title" id="expense-modal-title">Add Expense</span>
      <button class="btn btn--icon" onclick="closeExpenseModal()">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg>
      </button>
    </div>
    <div class="modal__body">
      <form id="expense-form" onsubmit="return false">
        <div class="form-grid">
          <div class="form-group form-group--full">
            <label>Title *</label>
            <input id="expense-title" type="text" placeholder="e.g. Grocery Shopping" required/>
          </div>
          <div class="form-group">
            <label>Amount (₹) *</label>
            <input id="expense-amount" type="number" min="0.01" step="0.01" placeholder="0.00" required/>
          </div>
          <div class="form-group">
            <label>Date *</label>
            <input id="expense-date" type="date" required/>
          </div>
          <div class="form-group">
            <label>Category *</label>
            <select id="expense-category" class="cat-select" required>
              <option value="">Select category</option>
            </select>
          </div>
          <div class="form-group">
            <label>Payment Method</label>
            <select id="expense-payment" class="pay-select">
              <option value="">Select method</option>
            </select>
          </div>
          <div class="form-group form-group--full">
            <label>Description</label>
            <textarea id="expense-desc" placeholder="Optional description…"></textarea>
          </div>
          <div class="form-group form-group--full">
            <label>Tags (comma separated)</label>
            <input id="expense-tags" type="text" placeholder="e.g. groceries, weekly"/>
          </div>
        </div>
      </form>
    </div>
    <div class="modal__footer">
      <button class="btn btn--outline" onclick="closeExpenseModal()">Cancel</button>
      <button class="btn btn--primary" onclick="saveExpense()">Save Expense</button>
    </div>
  </div>
</div>

<!-- ── Budget Modal ──────────────────────────────────────── -->
<div id="budget-modal" class="modal-overlay">
  <div class="modal">
    <div class="modal__header">
      <span class="modal__title">Set Budget</span>
      <button class="btn btn--icon" onclick="closeBudgetModal()">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg>
      </button>
    </div>
    <div class="modal__body">
      <form id="budget-form" onsubmit="return false">
        <div class="form-grid">
          <div class="form-group">
            <label>Category *</label>
            <select id="budget-category" class="cat-select" required>
              <option value="">Select category</option>
            </select>
          </div>
          <div class="form-group">
            <label>Budget Limit (₹) *</label>
            <input id="budget-limit" type="number" min="1" step="1" placeholder="e.g. 5000" required/>
          </div>
          <div class="form-group">
            <label>Month (YYYY-MM)</label>
            <input id="budget-month" type="month"/>
          </div>
        </div>
      </form>
    </div>
    <div class="modal__footer">
      <button class="btn btn--outline" onclick="closeBudgetModal()">Cancel</button>
      <button class="btn btn--primary" onclick="saveBudget()">Save Budget</button>
    </div>
  </div>
</div>

<script src="js/api.js"></script>
<script src="js/app.js"></script>
</body>
</html>
