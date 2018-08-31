create table customer
(
  id int primary key,
  alias varchar(20) unique,
  company_name varchar(70),
  company_id int unique,
  last_name varchar(80),
  first_name varchar(30),
  tax_identifier_number varchar(20) unique not null,
  address varchar(100),
  postal_code varchar(6),
  city varchar(30),
  default_payment_method int
);

create table products
(
  id int primary key,
  product_name varchar(80) not null,
  symbol varchar(10),
  unit varchar(10) not null,
  gross_price numeric(7,2) not null check(gross_price > 0.00),
  vat_rate numeric(4,2) not null check(vat_rate > 0.00),
  is_active boolean not null default true
);

create table templates
(
  id int primary key,
  customer_id int references customer(id) on delete cascade,
  product_id int references products(id) on delete cascade,
  quantity numeric(5,2) not null default 0
);
