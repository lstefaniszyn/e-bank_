package contracts.rest


import org.springframework.cloud.contract.spec.Contract

[
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
			def CURRENCY_CODE = '[A-Z]{3}'
			def IBAN = '[A-Z]{2}\\d{2}[A-Z0-9]{4}\\d{0,26}'
			body([
				page   : [
					size         : 2,
					totalElements: 6,
					totalPages   : 3,
					number       : 1,
				],
				content: [
					[
						id                 : 1,
						value      : [
							amount  : 123.12,
							currency: "EUR"
						],
						iban       : "PL10105009976312345678913",
						date       : "2119-06-30",
						description: "wow1!",
					],
					[
						id         : 2,
						value      : [
							amount  : 1111.1,
							currency: "EUR"
						],
						iban       : "GB33943720201555555555",
						date       : "2133-01-01",
						description: "superb*",
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
				jsonPath('$.content', byType {
					minOccurrence(1)
				})
				jsonPath("\$.['content'][0].['id']", byRegex(number()).asInteger())
				jsonPath("\$.['content'][0].['value'].['amount']", byRegex(aDouble()).asDouble())
				jsonPath("\$.['content'][0].['value'].['currency'].['code']", byRegex(CURRENCY_CODE).asString())
				jsonPath("\$.['content'][0].['iban']", byRegex(IBAN).asString())
				jsonPath("\$.['content'][0].['date']", byDate())
				jsonPath("\$.['content'][0].['description']", byRegex('.+').asString())
			}
		}
	},
	Contract.make {
		name("get transactions with invalid customer id")
		request {
			method('GET')
			url( $(p("api/v1/customers/12c/accounts/1/transactions"), c("api/v1/customers/${regex('(?!^\\d+$)^.+$')}/accounts/${regex('[0-9]+')}/transactions")) )
			headers {
				contentType(applicationJson())
			}
		}
		response {
			status 400
		}
	},
	Contract.make {
		name("get transactions with invalid account id")
		request {
			method('GET')
			url( $(p("api/v1/customers/1/accounts/12c/transactions"), c("api/v1/customers/${regex('[0-9]+')}/accounts/${regex('(?!^\\d+$)^.+$')}/transactions")) ){
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
			status 400
		}
	},
	Contract.make {
		name("get transactions with missing date param")
		request {
			method('GET')
			url( $(p("api/v1/customers/1/accounts/12c/transactions"), c("api/v1/customers/${regex('[0-9]+')}/accounts/${regex('(?!^\\d+$)^.+$')}/transactions")) ){
				queryParameters {
					parameter('page': value(producer(0), consumer(matching("[0-9]*")) ) )
					parameter('size': value(producer(2), consumer(matching("[0-9]*")) ) )
				}
			}
			headers {
				contentType(applicationJson())
			}
		}
		response {
			status 400
		}
	},
	Contract.make {
		name("get transactions with invalid date param")
		request {
			method('GET')
			url( $(p("api/v1/customers/1/accounts/12c/transactions"), c("api/v1/customers/${regex('[0-9]+')}/accounts/${regex('(?!^\\d+$)^.+$')}/transactions")) ){
				queryParameters {
					parameter('date': value(producer('2019-21'), consumer(matching("[0-9]{4}-[2-9][0-9]")) ) )
					parameter('page': value(producer(0), consumer(matching("[0-9]*")) ) )
					parameter('size': value(producer(2), consumer(matching("[0-9]*")) ) )
				}
			}
			headers {
				contentType(applicationJson())
			}
		}
		response {
			status 400
		}
	},
	Contract.make {
		name("get not autorized response while requesting for transactions")
		request {
			method('GET')
			url( $(p("api/v1/customers/1/accounts/2/transactions"), c("api/v1/customers/1/accounts/2/transactions")) ){
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
			status 403
		}
	},
	Contract.make {
		name("get customer not found response while requesting for transactions")
		request {
			method('GET')
			url( $(p("api/v1/customers/999/accounts/1/transactions"), c("api/v1/customers/999/accounts/${regex('[0-9]+')}/transactions")) ){
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
			status 404
		}
	},
	Contract.make {
		name("get account not found response while requesting for transactions")
		request {
			method('GET')
			url( $(p("api/v1/customers/1/accounts/999/transactions"), c("api/v1/customers/${regex('[0-9]+')}/accounts/999/transactions")) ){
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
			status 404
		}
	}
]