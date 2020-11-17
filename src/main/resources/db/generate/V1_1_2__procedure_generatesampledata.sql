create or replace procedure generateSampleData(nbClient INT, maxMonthlyTransactions INT)
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
 	insert into public.customer(given_name,family_name,identity_key) 
    select CONCAT('John_0',i),CONCAT('cust_Doe_0',i),concat('P-0',i) 
    from generate_series(1,nbClient) i; 
	commit;
	
	-- populate accounts
	for f in select public.customer.id from public.customer 	      
    loop 
		for nb_account in 1..random_between(1,2)
		loop
			INSERT INTO public.account(customer_id, iban, name, currency)
			values (f.id,
				   CONCAT('CH93-',random_between(1000,9999),'-',random_between(1000,9999),'-',random_between(1000,9999),'-',random_between(1000,9999),'-',random_between(1,9)),
				   CONCAT('JDoe_0', f.id, '_account_0', nb_account),
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
				nbTransaction := (random_between(8,10) * floor(maxMonthlyTransactions / 10));
				for tr in 1..nbTransaction
				loop
					dateTransaction := date (concat(y,'-',nb_month,'-',random_between(1,28)));
					INSERT INTO public.account_transaction(account_id, iban, transaction_date, amount, currency, description)
					values(f.id,
						   CONCAT('CH93-',random_between(1000,9999),'-',random_between(1000,9999),'-',random_between(1000,9999),'-',random_between(1000,9999),'-',random_between(1,9)),
                           dateTransaction,
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
