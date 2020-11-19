package contracts.rest

import org.springframework.cloud.contract.spec.Contract

[
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
