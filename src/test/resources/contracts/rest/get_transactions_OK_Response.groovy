import org.springframework.cloud.contract.spec.Contract


Contract.make {
	description("""
Represents a successful scenario of get transactions attached to  account

```
given:
	/api/v1/customers/{customerID}/accounts/{accountId}/transactions endpoint
when:
	make GET request
then:
	we'll get list of transactions for given account for E-Bank application
```

""")
	request {
		method('GET')
        url( $(p("api/v1/customers/1/accounts/1/transactions"), c("api/v1/customers/${regex('[0-9]+')}/accounts/${regex('[0-9]+')}/transactions")) ){
            queryParameters {
				parameter('date': value(producer('2019-01'), consumer(matching("[0-9]{4}-[0-1][0-2]")) ) )
                parameter('page': value(producer(0), consumer(matching("[0-9]*")) ) )
                parameter('size': value(producer(2), consumer(matching("[0-9]*")) ) )
			}
        }
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
                    page : [
                        size: 2,
                        totalElements: 6,
                        totalPages: 3,
                        number: 1,
                        ],
                    content: [
                        [
                            'id': 1, 
                            'value.amount': 123.12, 
                            'value.currency.code': "EUR",
                            'iban': "PL10105000997603123456789123",
                            'date': "2119.06.30 08:29:36 PDT",
                            'description': "wow1!",
                        ],
                        [
                            'id': 2, 
                            'value.amount': 1111.1, 
                            'value.currency.code': "EUR",
                            'iban': "GB33BUKB20201555555555",
                            'date': "2133.01.01 03:29:36 PDT",
                            'description': "superb*",
                        ],
                    ]
        ])  
        bodyMatchers {
            //page
            jsonPath('page.size', byRegex(number()).asInteger())
            jsonPath('page.totalElements', byRegex(number()).asInteger())
            jsonPath('page.totalPages', byRegex(number()).asInteger())
            jsonPath('page.number', byRegex(number()).asInteger())
            //content
            jsonPath("\$.['content'][0].['value'].['amount']", byRegex(aDouble()).asDouble())
            jsonPath("\$.['content'][0].['value'].['currency'].['code']", byRegex('[A-Z]{3}').asString())
            jsonPath("\$.['content'][0].['iban']", byRegex('\\w\\w[\\d]{16,24}').asString())
            jsonPath("\$.['content'][0].['date']", byRegex('\\d{4}-\\d{2}-\\d{2}(T|t)\\d{2}:\\d{2}:\\d{2}[\\w]?').asString())
            jsonPath("\$.['content'][0].['description']", byRegex('.+').asString())
            }
    }
}