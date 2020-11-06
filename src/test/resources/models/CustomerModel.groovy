import groovy.json.*
import groovy.lang.*


/* Customer model */

class Customer {
    Integer id = 1
    String givenName = "Johnny"
    String familyName = "Bravo"
    ArrayList<AccountCustomerView> accounts = [
        new AccountCustomerView(
            id: 1,
            name: "This is for fun account",
            iban: "PL10105000997603123456789123",
            currency: new Currency(name: "Zloty", code: "PLN")
        ), 
        new AccountCustomerView(
            id: 2,
            name: "For life account",
            iban: "PT50002700000001234567833",
            currency: new Currency(name: "Euro", code: "EUR")
        )
        ]
    Balance balance = new Balance(
        value: 1111, 
        currency: new Currency(code: "GBP")
        )
}

/* Account model view only for customer model */

class AccountCustomerView {
    Integer id = 1
    String name = "This is for fun account"
    String iban = "PL10105000997603123456789123"
    Currency currency = new Currency(name: "Zloty", code: "PLN")
}


class Balance{
    Double value =  123.22
    Currency currency = new Currency()

}


class Currency{
    String name = "Euro"
    String code = "EUR"
}

/* Account model with transactions */

class AccountTransactionsView{
    Integer id = 1
    String name = "This is for fun account"
    String iban = "PL10105000997603123456789123"
    Currency currency = new Currency(name: "Zloty", code: "PLN")
    ArrayList<Transaction> transactions = [
        new Transaction(
            id: 2, 
            value: new TransactionValue(amount: 321.1, currency: new Currency(code: "GBP")), 
            iban: "PT50002700000001234567833", 
            date: "2119.06.30 08:29:36 PDT", 
            description: "wow!"), 
        new Transaction(
            id: 3, 
            value: new TransactionValue(amount: 1111.1, currency: new Currency(code: "PT")), 
            iban: "GB33BUKB20201555555555", 
            date: "2133.01.01 03:29:36 PDT", 
            description: "superb!"
        ), 
        new Transaction(
            id: 4, 
            value: new TransactionValue(amount: 1, currency: new Currency(code: "PLN")), 
            iban: "PL10105000997603123456789123", 
            date: "2133.01.01 03:29:36 PDT", 
            description: "superb!"
        ) 
        ]
}

class Transaction{
    Integer id = 1
    TransactionValue value = new TransactionValue()
    String iban = "PL10105000997603123456789123"
    String date = "2009.06.30 08:29:36 PDT"
    String description = "relaxing stuff"
}

class TransactionValue{
    Double amount = 123.45
    Currency currency = new Currency()
}



def getJsonCustomerView(){
    Customer customer = new Customer()
    def generator = new JsonGenerator.Options()
            .excludeNulls()
            .build()

    return generator.toJson(customer)
}

def getJsonAccountView(){
    AccountTransactionsView accountTransactionView = new AccountTransactionsView()
    def generator = new JsonGenerator.Options()
            .excludeNulls()
            .build()

    return generator.toJson(accountTransactionView)
}



print(getJsonCustomerView() + "\n\n" + getJsonAccountView());