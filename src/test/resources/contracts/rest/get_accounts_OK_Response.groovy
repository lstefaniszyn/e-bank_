import org.springframework.cloud.contract.spec.Contract


scriptDir = new File(getClass().protectionDomain.codeSource.location.path).parent
GroovyShell shell = new GroovyShell()
def currency = shell.parse(new File(scriptDir + '/../../models/CustomerModel.groovy'))


Contract.make {
	description("""
Represents a successful scenario of get list of accounts attached to customer

```
given:
	/api/v1/customers/{customerID}/accounts endpoint
when:
	make GET request
then:
	we'll get accounts list for E-Bank application
```

""")
	request {
		method('GET')
        url( $(p("api/v1/customers/1/accounts"), c("api/v1/customers/${regex('[0-9]+')}/accounts")) )
		headers {
			contentType(applicationJson())
		}
	}
	response {
		status 200
		headers {
			contentType(applicationJson())
        }
        body([
            [
                    id: 1,
                    name: "For fun account",
                    iban: "PL10105000997603123456789123",
                    'currency.code': "PLN",
                    'balance.value': 123.12, 
                    'balance.currency.code': "PLN",
            ]
        ])  
        bodyMatchers {
            jsonPath('$', byType {
                // results in verification of size of array (min 1)
                minOccurrence(1)
            })
        }
	}
}