drop table if exists public.customer cascade;
CREATE TABLE public.customer
(
    name character varying(250),
    id serial,
    identity_key character varying(50),
    PRIMARY KEY (id)
);
ALTER TABLE public.customer OWNER to postgres;

DROP TABLE if exists public.account cascade;
CREATE TABLE public.account
(
    id serial,
    customer_id integer,
    iban character varying(250),
    currency character varying(3),
    PRIMARY KEY (id),
    CONSTRAINT cust_fk FOREIGN KEY (customer_id) REFERENCES public.customer (id)
);
ALTER TABLE public.account  OWNER to postgres;

drop table if exists public."accountTransaction" cascade;
CREATE TABLE public."accountTransaction"
(
    id serial,
    account_id integer,
    value_date date,
    amount float(2),
    currency character varying(3),
    description character varying(250),
    PRIMARY KEY (id),
    CONSTRAINT account_fk FOREIGN KEY (account_id) REFERENCES public.account (id)
);
ALTER TABLE public."accountTransaction" OWNER to postgres;

ALTER TABLE public."accountTransaction" RENAME TO account_transaction;

commit;
