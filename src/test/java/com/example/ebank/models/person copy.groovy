import groovy.json.*
import groovy.lang.*


scriptDir = new File(getClass().protectionDomain.codeSource.location.path).parent
// evaluate(new File(scriptDir + "/CurrencyModel.groovy"))

GroovyShell shell = new GroovyShell()
def currency = shell.parse(new File(scriptDir + '/CurrencyModel.groovy'))
print(currency.new Currency());
// Class groovyClass = new GroovyClassLoader(getClass().getClassLoader()).parseClass(sourceFile);
// GroovyObject myObject = (GroovyObject) groovyClass.newInstance();


class Person {
    String name
    String title
    int age
    String password
    // Currency currency
    Date dob
    URL favoriteUrl
}

Person person = new Person(name: 'John', title: null, age: 21, password: 'secret',
                            dob: Date.parse('yyyy-MM-dd', '1984-12-15'),
                            favoriteUrl: new URL('http://groovy-lang.org/'))

def generator = new JsonGenerator.Options()
    .excludeNulls()
    .dateFormat('yyyy@MM')
    .excludeFieldsByName('age', 'password')
    .excludeFieldsByType(URL)
    .build()

print (generator.toJson(person))
// assert generator.toJson(person) == '{"dob":"1984@12","name":"John"}'