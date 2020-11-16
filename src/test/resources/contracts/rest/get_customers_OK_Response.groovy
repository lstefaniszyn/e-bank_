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
        }
		body([
            [
                id        : $(consumer(1), producer(regex(number()))),
                givenName : $(consumer("Johnny"), producer(regex('[\\w-_\\s\\.]+'))),
                familyName: $(consumer("Bravo"), producer(regex('[\\w-_\\s\\.]+')))
            ], [
                id        : $(consumer(2), producer(regex(number()))),
                givenName : $(consumer("Audrey"), producer(regex('[\\w-_\\s\\.]+'))),
                familyName: $(consumer("Hepburn"), producer(regex('[\\w-_\\s\\.]+')))
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
