-- DROP FUNCTION public.random_between(integer, integer);

CREATE OR REPLACE FUNCTION public.random_between(low integer, high integer)
    RETURNS integer
    LANGUAGE 'plpgsql'
    
AS $$
BEGIN
   RETURN floor(random()* (high-low + 1) + low);
END;
$$

-- DROP FUNCTION public.random_currency();

CREATE OR REPLACE FUNCTION public.random_currency()
    RETURNS character varying
    LANGUAGE 'plpgsql'
AS $$
declare
   id_currency integer;
   currency varchar(3);
begin
	select random_between(1,3) into id_currency;

  	if id_currency = 1 	then 
		currency := 'GBP';
	elseif id_currency = 2	then 
		currency := 'CHF';
	else
	 	currency := 'EUR';
	end if;
  	return currency;
end;
$$


create or replace procedure generateSampleData(nbClient INT)
language plpgsql
as $$
declare
-- variable declaration
	f record;
	nbTransaction int;
	dateTransaction date;
begin
-- stored procedure body
	-- populate customers
 	insert into public.customer(name,identity_key) 
    select CONCAT('cust_JDoo_0',i),concat('P-0',i) 
    from generate_series(1,nbClient) i; 
	commit;
	
	-- populate accounts
	for f in select public.customer.id from public.customer 	      
    loop 
		for nb_account in 1..random_between(1,2)
		loop
			INSERT INTO public.account(customer_id, iban, currency)
			values (f.id,
				   CONCAT('CH93-',random_between(1000,9999),'-',random_between(1000,9999),'-',random_between(1000,9999),'-',random_between(1000,9999),'-',random_between(1,9)),
				   random_currency());
		end loop;
	end loop;
	commit;
	
	-- populate transactions
	for f in select public.account.id, public.account.currency from public.account
	loop
		for y in 2018..2019
		loop
			for nb_month in 1..12
			loop
				nbTransaction := (random_between(1,9) * 100 + 90);
				for tr in 1..nbTransaction
				loop
					dateTransaction := date (concat(y,'-',nb_month,'-',random_between(1,28)));
					INSERT INTO public.account_transaction(account_id, value_date, amount, currency, description)
					values(f.id,
						   dateTransaction ,
						   (random() * 1000 +1), 
						   f.currency, 
						   concat('payment ',f.currency)
						  );
				end loop;
			end loop;
		end loop;
	end loop;
	commit;
end; $$


-- RE-INIT DATA
delete from public.account_transaction cascade;
delete from public.account cascade;
delete from public.customer cascade;

ALTER SEQUENCE public."accountTransaction_id_seq" RESTART;
ALTER SEQUENCE public."account_id_seq" RESTART;
ALTER SEQUENCE public."customer_id_seq" RESTART;
UPDATE public.account_transaction SET id = DEFAULT;
UPDATE public.account SET id = DEFAULT;
UPDATE public.customer SET id = DEFAULT;

-- GENERATE SAMPLE DATA
CALL public.generatesampledata(2);

