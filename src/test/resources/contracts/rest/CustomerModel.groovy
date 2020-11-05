import groovy.json.*
import groovy.lang.*



class Balance{
    Double value =  123.22
}

class Customer {
    Integer id = 1
    String givenName = "Johnny"
    String familyName = "Bravo"
    String accounts = ""
    Balance balance = new Balance()
}





def getJson(){
    Customer customer = new Customer()
    def generator = new JsonGenerator.Options()
            .excludeNulls()
            .build()

    return generator.toJson(customer)
}


print(getJson());