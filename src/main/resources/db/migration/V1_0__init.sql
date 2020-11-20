CREATE TABLE IF NOT EXISTS public.customer
(
    id serial,
    family_name character varying(250),
    given_name character varying(250),
    identity_key character varying(50),
    PRIMARY KEY (id)
);
--ALTER TABLE public.customer OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.account
(
    id serial,
    customer_id integer,
    iban character varying(250),
    name character varying(250),
    currency character varying(3),
    PRIMARY KEY (id),
    CONSTRAINT cust_fk FOREIGN KEY (customer_id) REFERENCES public.customer (id) 
);
--ALTER TABLE public.account  OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.account_transaction
(
    id serial,
    account_id integer,
    iban character varying(250),
    transaction_date date,
    amount float(2),
    currency character varying(3),
    description character varying(250),
    PRIMARY KEY (id),
    CONSTRAINT account_fk FOREIGN KEY (account_id) REFERENCES public.account (id)
);
--ALTER TABLE public.account_transaction OWNER to postgres;