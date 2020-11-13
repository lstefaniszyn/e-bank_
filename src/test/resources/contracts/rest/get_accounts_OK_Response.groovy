import org.springframework.cloud.contract.spec.Contract


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
                [
                    id:             $(consumer(1), producer(regex(number()))),
                    name:           $(consumer("Account 1"), producer(regex('.+'))),
                    iban:           $(consumer("PL01234567890123456789012345"), producer(regex('[A-Z]{2}\\d{2}[A-Z0-9]{0,30}'))),
                    currency: [
                        code:       $(consumer("EUR"), producer(regex('([A-Z]|[0-9]){3}')))
                    ],
                    balance: [
                        value:      $(consumer(123.45), producer(regex(number()))),
                        currency: [
                            code:   $(consumer("EUR"), producer(regex('([A-Z]|[0-9]){3}')))
                        ]
                    ]
                ],
                [
                    id:             $(consumer(2), producer(regex(number()))),
                    name:           $(consumer("Account 2"), producer(regex('.+'))),
                    iban:           $(consumer("CH5604835012345678009"), producer(regex('[A-Z]{2}\\d{2}[A-Z0-9]{0,30}'))),
                    currency: [
                        code:       $(consumer("CHF"), producer(regex('([A-Z]|[0-9]){3}')))
                    ],
                    balance: [
                        value:      $(consumer(5000.00), producer(regex(number()))),
                        currency: [
                            code:   $(consumer("CHF"), producer(regex('([A-Z]|[0-9]){3}')))
                        ]
                    ]
                ]
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
