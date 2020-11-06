package contracts.rest


import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description("""
Represents a successful scenario of get customers list

```
given:
	/api/v1/customers endpoint
when:
	make GET request
then:
	we'll get list of customers for E-Bank application
```

""")
	request {
		method('GET')
        url( "/api/v1/customers" )
		headers {
			contentType(applicationJson())
		}
	}
	response {
		status 200
		headers {
			contentType(applicationJson())
		body([
                [
                    id: 1,
                    givenName: "Johnny",
                    familyName: "Bravo"
                ], [
                    id: 2,
                    givenName: "Audrey",
                    familyName: "Hepburn"
                ]
        ])
        bodyMatchers {
            jsonPath('$[0].id', byRegex(number()).asInteger())
            jsonPath('$[0].givenName', byRegex('[\\w-_\\s\\.]+').asString())
            jsonPath('$[0].familyName', byRegex('[\\w-_\\s\\.]+').asString())
        }
		}
	}
}
