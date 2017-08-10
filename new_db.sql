﻿create table customer
(
	id serial primary key,
	alias varchar(20) unique,
	last_name varchar(80),
	first_name varchar(30),
	personal_id int unique not null,
	tax_identifier_number varchar(20) unique not null,
	email varchar(30) unique,
	street_house_no varchar(100),
	postal_code varchar(6),
	city varchar(30),
	telephone int unique,
	cell_phone int unique,
	fax_number int unique,
	tag varchar(20),
	default_payment_method varchar(15),
	creation_date date not null default CURRENT_DATE,
	last_purchase_date date,
	country varchar(30) not null,
	client_type varchar(20) not null default 'company',
	company_special_number int,
	bank_name varchar(40),
	default_currency varchar(3),
	bank_account_number int,
	default_discount numeric(3,2) default 0.00 check(default_discount >= 0.00),
	default_payment_date_days int check(default_payment_date_days >= 0.00)
);

create table issued_invoices
(
	id serial primary key,
	customer_id int references customer(id),
	invoice_number varchar(20) not null,
	invoice_type varchar(15) not null default 'ordinary',
	seller varchar(40) not null,
	issue_date date not null,
	charge numeric(7,2) not null check(charge > 0.00),
	paid_amount numeric(7,2) check(paid_amount > 0.00),
	payment_method varchar(15) not null,
	payment_date date not null,
	payment_expiration_date date check(payment_expiration_date >= CURRENT_DATE),
	currency varchar(3) not null,
	status varchar(10) not null,
	creation_date date not null,
	last_modified date not null default CURRENT_DATE,
	sent_date date,
	notes varchar(200)
);

create table warehouse
(
	id serial primary key,
	sold int not null default 0,
	available int not null default 0,
	product_code varchar(20) default null,
	creation_date date not null default CURRENT_DATE,
	last_sell_date date
);

create table products
(
	id serial primary key,
	product_name varchar(80) not null,
	net_price numeric(7,2) not null check(net_price > 0.00),
	tax_rate numeric(3,2) not null check(tax_rate > 0.00),
	online_sell boolean not null default '0',
	is_service boolean not null default '0',
	is_active boolean not null default '1',
	warehouse_item_id int references warehouse(id)
);

create table bought_products
(
	id serial primary key,
	quantity int not null default 0 check(quantity >= 0),
	net_value numeric(7,2) not null check(net_value > 0.00),
	tax_value numeric(7,2) not null check(tax_value > 0.00),
	gross_value numeric(7,2) not null check(gross_value > 0.00),	
	invoice_id int references issued_invoices(id),
	product_id int references products(id),
	sell_date date not null
);