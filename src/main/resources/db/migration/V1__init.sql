CREATE TABLE users (
 id BIGSERIAL PRIMARY KEY, name VARCHAR(150) NOT NULL, email VARCHAR(255) NOT NULL UNIQUE,
 password VARCHAR(255) NOT NULL, role VARCHAR(20) NOT NULL, created_at TIMESTAMP NOT NULL
);
CREATE TABLE categories (
 id BIGSERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL, user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
 CONSTRAINT uq_category_user_name UNIQUE(user_id,name)
);
CREATE TABLE expenses (
 id BIGSERIAL PRIMARY KEY, amount NUMERIC(12,2) NOT NULL CHECK(amount>0), description VARCHAR(255) NOT NULL,
 expense_date DATE NOT NULL, payment_method VARCHAR(50) NOT NULL,
 category_id BIGINT NOT NULL REFERENCES categories(id), user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);
CREATE INDEX idx_expenses_user_date ON expenses(user_id,expense_date);
CREATE TABLE income (
 id BIGSERIAL PRIMARY KEY, amount NUMERIC(12,2) NOT NULL CHECK(amount>0), source VARCHAR(150) NOT NULL,
 income_date DATE NOT NULL, user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);
CREATE TABLE budgets (
 id BIGSERIAL PRIMARY KEY, amount NUMERIC(12,2) NOT NULL CHECK(amount>0), month INT NOT NULL CHECK(month BETWEEN 1 AND 12),
 year INT NOT NULL, alert_threshold INT NOT NULL DEFAULT 80 CHECK(alert_threshold BETWEEN 1 AND 100),
 category_id BIGINT NOT NULL REFERENCES categories(id), user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
 CONSTRAINT uq_budget UNIQUE(user_id,category_id,month,year)
);
CREATE TABLE goals (
 id BIGSERIAL PRIMARY KEY, name VARCHAR(150) NOT NULL, target_amount NUMERIC(12,2) NOT NULL CHECK(target_amount>0),
 current_amount NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK(current_amount>=0), target_date DATE NOT NULL,
 user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);
CREATE TABLE goal_contributions (
 id BIGSERIAL PRIMARY KEY, goal_id BIGINT NOT NULL REFERENCES goals(id) ON DELETE CASCADE,
 amount NUMERIC(12,2) NOT NULL CHECK(amount>0), contribution_date DATE NOT NULL
);
CREATE TABLE recurring_expenses (
 id BIGSERIAL PRIMARY KEY, amount NUMERIC(12,2) NOT NULL CHECK(amount>0), description VARCHAR(255) NOT NULL,
 interval_days INT NOT NULL CHECK(interval_days>0), next_run_date DATE NOT NULL, active BOOLEAN NOT NULL DEFAULT TRUE,
 category_id BIGINT NOT NULL REFERENCES categories(id), user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);
CREATE TABLE notifications (
 id BIGSERIAL PRIMARY KEY, title VARCHAR(255) NOT NULL, message VARCHAR(1000) NOT NULL,
 read_flag BOOLEAN NOT NULL DEFAULT FALSE, created_at TIMESTAMP NOT NULL,
 user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);