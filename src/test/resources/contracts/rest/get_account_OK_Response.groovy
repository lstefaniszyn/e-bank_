import org.springframework.cloud.contract.spec.Contract


Contract.make {
	description("""
Represents a successful scenario of get account attached to customer

```
given:
	/api/v1/customers/{customerID}/accounts/{accountId} endpoint
when:
	make GET request
then:
	we'll get account for E-Bank application
```

""")
	request {
		method('GET')
        url( $(p("api/v1/customers/1/accounts/1"), c("api/v1/customers/${regex('[0-9]+')}/accounts/${regex('[0-9]+')}")) )
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
                    id: 1,
                    name: "Account 1",
                    iban: "PL01234567890123456789012345",
                    currency: [
                        code: "PLN"
                    ],
                    balance: [
                        value: 123.12,
                        currency: [
                            code: "PLN"
                        ]
                    ]
        ])
        bodyMatchers {
            jsonPath('id', byRegex(number()).asInteger())
            jsonPath('name', byRegex('.+').asString())
            jsonPath('iban', byRegex('[A-Z]{2}\\d{2}[A-Z0-9]{0,30}').asString())
            jsonPath('currency.code', byRegex('([A-Z]|[0-9]){3}').asString())
            jsonPath('balance.value', byRegex(aDouble()).asDouble())
            jsonPath('balance.currency.code', byRegex('([A-Z]|[0-9]){3}').asString())
            }
    }
}
