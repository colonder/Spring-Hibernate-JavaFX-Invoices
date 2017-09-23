drop table if exists bought_products;
drop table if exists issued_invoices;
drop table if exists customer;
drop table if exists products;
drop table if exists warehouse;
drop table if exists sellers;
drop table if exists settings;

create table sellers
(
	id serial primary key,
	alias varchar(20) unique,
	last_name varchar(80) not null,
	first_name varchar(30) not null,
	tax_identifier_number varchar(20) unique not null,
	address varchar(100),
	postal_code varchar(6),
	city varchar(30),
	account_number varchar(31),
	bank varchar(50),
	country varchar(30),
	email varchar(30) unique,
	telephone int unique,
	fax_number int unique,
	creation_date date not null default CURRENT_DATE,
	last_modified date not null default CURRENT_DATE
);

create table settings
(
	id serial primary key,
	default_currency varchar(3),
	default_vat_rate numeric(3,2) default 0.00 check(default_vat_rate >= 0.00),
	default_language varchar(30)
);

create table customer
(
	id serial primary key,
	alias varchar(20) unique,
	company_name varchar(70),
	last_name varchar(80),
	first_name varchar(30),
	personal_id bigint unique not null,
	tax_identifier_number varchar(20) unique not null,
	email varchar(30) unique,
	address varchar(100),
	postal_code varchar(6),
	city varchar(30),
	telephone int unique,
	cell_phone int unique,
	fax_number int unique,
	tag varchar(20),
	default_payment_method varchar(15),
	creation_date date not null default CURRENT_DATE,
	last_purchase_date date,
	country varchar(30),
	customer_type varchar(20) not null default 'person',
	company_special_number int,
	default_discount numeric(3,2) default 0.00 check(default_discount >= 0.00),
	default_payment_date_days int check(default_payment_date_days >= 0.00),
	last_modified date not null default CURRENT_DATE
);

create table issued_invoices
(
	id serial primary key,
	customer_id int references customer(id),
	invoice_number varchar(20) not null,
	invoice_type varchar(15) not null default 'ordinary',
	seller_id int references sellers(id),
	issue_date date not null,
	sale_date date not null,
	location varchar(100) not null,
	net_value numeric(7,2) not null check(net_value > 0.00),
	vat_value numeric(7,2) not null check(vat_value > 0.00),
	discount_value numeric(7,2),
	gross_value numeric(7,2) not null check(gross_value > 0.00),
	paid_amount numeric(7,2) check(paid_amount > 0.00),
	payment_method varchar(15) not null,
	paid_date date,
	payment_date date not null check(payment_date >= CURRENT_DATE),
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
	last_modified date not null default CURRENT_DATE,
	last_sale_date date	
);

create table products
(
	id serial primary key,
	product_name varchar(80) not null,
	symbol varchar(10),
	unit varchar(10) not null,
	net_price numeric(7,2) not null check(net_price > 0.00),
	vat_rate numeric(4,2) not null check(vat_rate > 0.00),
	online_sale boolean not null default '0',
	is_service boolean not null default '0',
	is_active boolean not null default '1',
	warehouse_item_id int references warehouse(id),
	creation_date date not null default CURRENT_DATE,
	last_modified date not null default CURRENT_DATE
);

create table bought_products
(
	id serial primary key,
	product_name varchar(80) not null,
	symbol varchar(10),
	unit varchar(10) not null,
	price numeric(7,2) not null,
	vat_rate numeric(4,2) not null,
	quantity int not null default 0 check(quantity >= 0),
	net_value numeric(7,2) not null check(net_value > 0.00),
	vat_value numeric(7,2) not null check(vat_value > 0.00),
	discount_percents int,
	gross_value numeric(7,2) not null check(gross_value > 0.00),	
	invoice_id int references issued_invoices(id) on delete cascade
);