INSERT INTO public.customer(cust_name) VALUES ('JohnDoo');
INSERT INTO public.customer(cust_name) VALUES ('JaneDoo');

INSERT INTO public.account(customerid, "IBAN", currency) VALUES (1, 'CH93-0000-9999-8888-6666-1', 'GBP');
INSERT INTO public.account(customerid, "IBAN", currency) VALUES (1, 'CH93-0000-9999-8888-6666-2', 'CHF');
INSERT INTO public.account(customerid, "IBAN", currency) VALUES (2, 'CH93-0000-9999-7777-5555-1', 'EUR');

INSERT INTO public."accountTransaction"("accountId", "valueDate", amount, currency, description)
	VALUES (1, '2020-10-14', 100.00, 'CHF', 'test CHF');
INSERT INTO public."accountTransaction"("accountId", "valueDate", amount, currency, description)
	VALUES (2, '2020-10-14', 110.10, 'CHF', 'Online payment CHF');
INSERT INTO public."accountTransaction"("accountId", "valueDate", amount, currency, description)
	VALUES (3, '2020-10-14', 10.00, 'EUR', 'withdraw money');