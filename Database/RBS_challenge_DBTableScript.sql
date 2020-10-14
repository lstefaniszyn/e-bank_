drop table if exists public.customer cascade;
CREATE TABLE public.customer
(
    cust_name character varying(250),
    id serial,
    "identityKey" character varying(50),
    PRIMARY KEY (id)
);
ALTER TABLE public.customer OWNER to postgres;

DROP TABLE if exists public.account cascade;
CREATE TABLE public.account
(
    id serial,
    customerId integer,
    "IBAN" character varying(250),
    currency character varying(3),
    PRIMARY KEY (id),
    CONSTRAINT cust_fk FOREIGN KEY (customerId) REFERENCES public.customer (id) 
);
ALTER TABLE public.account  OWNER to postgres;

drop table if exists public."accountTransaction" cascade;
CREATE TABLE public."accountTransaction"
(
    id serial,
    "accountId" integer,
    "valueDate" date,
    amount float(2),
    currency character varying(3),
    description character varying(250),
    PRIMARY KEY (id),
    CONSTRAINT account_fk FOREIGN KEY ("accountId") REFERENCES public.account (id)
);
ALTER TABLE public."accountTransaction" OWNER to postgres;

commit;