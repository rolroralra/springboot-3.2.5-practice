-- Drop existing tables if they exist
drop table if exists item cascade;
drop table if exists category cascade;
drop table if exists brand cascade;

-- Drop existing sequences if they exist
drop sequence if exists brand_seq;
drop sequence if exists category_seq;
drop sequence if exists item_seq;

-- Create sequences for each table
create sequence brand_seq start with 1 increment by 1;
create sequence category_seq start with 1 increment by 1;
create sequence item_seq start with 1 increment by 1;

-- Create tables with ID columns generated using the sequences
create table brand (
                       id bigint default next value for brand_seq primary key,
                       name varchar(255),
                       insert_at timestamp(6) default current_timestamp,
                       update_at timestamp(6) default current_timestamp
);

create table category (
                          id bigint default next value for category_seq primary key,
                          name varchar(255),
                          insert_at timestamp(6) default current_timestamp,
                          update_at timestamp(6) default current_timestamp
);

create table item (
                      id bigint default next value for item_seq primary key,
                      brand_id bigint,
                      category_id bigint,
                      price bigint,
                      insert_at timestamp(6) default current_timestamp,
                      update_at timestamp(6) default current_timestamp,
                      name varchar(255),
                      constraint item_fk_1 foreign key (brand_id) references brand(id),
                      constraint item_fk_2 foreign key (category_id) references category(id)
);

create index item_category_price_idx_1 on item (category_id, price);
create index item_brand_idx_2 ON item (brand_id);
