

-- Создание таблицы "banks" для банков
CREATE TABLE banks (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       office_count INT DEFAULT 0,
                       atm_count INT DEFAULT 0,
                       employee_count INT DEFAULT 0,
                       client_count INT DEFAULT 0,
                       rating INT CHECK (rating >= 0 AND rating <= 100),
                       total_money NUMERIC(15, 2),
                       interest_rate NUMERIC(4, 2)
);

-- Создание таблицы "bank_offices" для офисов банков
CREATE TABLE bank_offices (
                              id SERIAL PRIMARY KEY,
                              name VARCHAR(100) NOT NULL,
                              address VARCHAR(255) NOT NULL,
                              status VARCHAR(50),
                              can_place_atm BOOLEAN DEFAULT TRUE,
                              atm_count INT DEFAULT 0,
                              can_issue_loans BOOLEAN DEFAULT TRUE,
                              is_cash_withdrawal_available BOOLEAN DEFAULT TRUE,
                              is_deposit_available BOOLEAN DEFAULT TRUE,
                              money_available NUMERIC(15, 2),
                              rental_cost NUMERIC(15, 2),
                              bank_id INT REFERENCES banks(id) ON DELETE CASCADE
);

-- Создание таблицы "employees" для сотрудников
CREATE TABLE employees (
                           id SERIAL PRIMARY KEY,
                           full_name VARCHAR(100) NOT NULL,
                           birth_date DATE,
                           position VARCHAR(50),
                           is_remote BOOLEAN DEFAULT FALSE,
                           can_issue_loans BOOLEAN DEFAULT FALSE,
                           salary NUMERIC(15, 2),
                           bank_id INT REFERENCES banks(id) ON DELETE SET NULL,
                           bank_office_id INT REFERENCES bank_offices(id) ON DELETE SET NULL
);

-- Создание таблицы "users" для клиентов
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       full_name VARCHAR(100) NOT NULL,
                       birth_date DATE,
                       place_of_work VARCHAR(100),
                       monthly_income NUMERIC(15, 2),
                       credit_rating INT CHECK (credit_rating >= 0 AND credit_rating <= 10000)
);

-- Создание таблицы "payment_accounts" для платежных счетов
CREATE TABLE payment_accounts (
                                  id SERIAL PRIMARY KEY,
                                  balance NUMERIC(15, 2) DEFAULT 0.0,
                                  user_id INT REFERENCES users(id) ON DELETE CASCADE,
                                  bank_id INT REFERENCES banks(id) ON DELETE CASCADE
);

-- Создание таблицы "credit_accounts" для кредитных счетов
CREATE TABLE credit_accounts (
                                 id SERIAL PRIMARY KEY,
                                 start_date DATE,
                                 end_date DATE,
                                 months INT,
                                 credit_amount NUMERIC(15, 2),
                                 monthly_payment NUMERIC(15, 2),
                                 interest_rate NUMERIC(4, 2),
                                 user_id INT REFERENCES users(id) ON DELETE CASCADE,
                                 bank_id INT REFERENCES banks(id) ON DELETE CASCADE,
                                 employee_id INT REFERENCES employees(id) ON DELETE SET NULL,
                                 payment_account_id INT REFERENCES payment_accounts(id) ON DELETE SET NULL
);

-- Создание таблицы "bank_atms" для банкоматов
CREATE TABLE bank_atms (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           address VARCHAR(255) NOT NULL,
                           status VARCHAR(50),
                           is_cash_withdrawal_available BOOLEAN DEFAULT TRUE,
                           is_deposit_available BOOLEAN DEFAULT TRUE,
                           money_available NUMERIC(15, 2),
                           maintenance_cost NUMERIC(15, 2),
                           bank_id INT REFERENCES banks(id) ON DELETE CASCADE,
                           office_id INT REFERENCES bank_offices(id) ON DELETE SET NULL,
                           employee_id INT REFERENCES employees(id) ON DELETE SET NULL
);
CREATE TABLE user_banks (
                            user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            bank_id INT NOT NULL REFERENCES banks(id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, bank_id)
);