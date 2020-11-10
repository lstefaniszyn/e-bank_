to generate the sample data set, usa the following postgreSQL call, by specifying the number of test customers to create
											 
CALL public.generatesampledata({nbOfCustomers});

For each customer, the procedure will  
- randomly generate, one or two bank account(s)
- randomly assign a currency to the bank account (GBP, CHF or EUR)

For each bank account, the procedure will
- generate 800, 900 or 1000 monthly transactions
- for 2018 (12 months), 2019 (12 months), 2020 (9 months)

