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
