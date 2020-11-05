
import groovy.json.*
import groovy.lang.*



// import groovy.json.JsonOutput  
// class Example {
//    static void main(String[] args) {
//       def output = JsonOutput.toJson([ new Student(name: 'John',ID:1),
//          new Student(name: 'Mark',ID:2)])
//       println(output);  
//    } 
// }
 
// class Student {
//    String name
//    int ID; 
// }

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