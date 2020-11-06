import org.springframework.cloud.contract.spec.Contract


scriptDir = new File(getClass().protectionDomain.codeSource.location.path).parent
GroovyShell shell = new GroovyShell()
def currency = shell.parse(new File(scriptDir + '/../../models/CustomerModel.groovy'))


Contract.make {
	description("""
Represents a successful scenario of get one customer

```
given:
	/api/v1/customers/{customerID} endpoint
when:
	make GET request
then:
	we'll get given customer for E-Bank application
```

""")
	request {
		method('GET')
        url( $(p("api/v1/customers/1"), c("api/v1/customers/${regex('[0-9]+')}")) )
		headers {
			contentType(applicationJson())
		}
	}
	response {
		status 200
		headers {
			contentType(applicationJson())
		
        body([
                    id: fromRequest().path(3), // get {custumerId}
                    givenName: "Johnny",
                    familyName: "Bravo",
                    identityKey: "",
                    accounts: [[]],
                    'balance.value': 123.12, 
                    'balance.currency.code': "EUR",
        ])  
        bodyMatchers {
            jsonPath('$[0].id', byRegex(number()).asInteger())
            jsonPath('$[0].givenName', byRegex('[\\w-_\\s\\.]+').asString())
            jsonPath('$[0].familyName', byRegex('[\\w-_\\s\\.]+').asString())
        }
		}
        
        // body(  $( p(currency.getJsonCustomerView()) , c(currency.getJsonCustomerView()) )  )
        // body(  $( p(currency.getJson()) , c(file('one_cutomer_response.json')) )  )
		}
}


// Customer:
//             type: object
//             properties:
//                 id:
//                     type: integer
//                     format: int64
//                 givenName:
//                     type: string
//                     description: https://schema.org/givenName
//                 familyName:
//                     type: string
//                     description: https://schema.org/familyName
//                 identityKey:
//                     type: string
//                 accounts:
//                     type: array
//                     items:
//                         $ref: "#/components/schemas/Account"
//                 balance:
//                     type: object
//                     properties:
//                         value:
//                             type: number
//                             format: double
//                             description: Sum up of money status combined from all client's accounts and exchange to GPB
//                         currency:
//                             description: Sum up of money status combined from all client's accounts and exchange to GPB
//                             $ref: "#/components/schemas/Currency"

