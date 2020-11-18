import org.springframework.cloud.contract.spec.Contract

[
	Contract.make {
		name("get account with invalid customer id")
		request {
			method('GET')
			url( $(p("api/v1/customers/12c/accounts/1"), c("api/v1/customers/${regex('(?!^\\d+$)^.+$')}/accounts/${regex('[0-9]+')}")) )
			headers {
				contentType(applicationJson())
			}
		}
		response {
			status 400
		}
	},
	Contract.make {
		name("get account not autorized response")
		request {
			method('GET')
			url( $(p("api/v1/customers/1/accounts/2"), c("api/v1/customers/1/accounts/2")) )
			headers {
				contentType(applicationJson())
			}
		}
		response {
			status 403
		}
	},
	Contract.make {
		name("get customer not found response while requesting for accounts")
		request {
			method('GET')
			url( $(p("api/v1/customers/999/accounts/1"), c("api/v1/customers/999/accounts/${regex('[0-9]+')}")) )
			headers {
				contentType(applicationJson())
			}
		}
		response {
			status 404
		}
	},
	Contract.make {
		name("get account not found response")
		request {
			method('GET')
			url( $(p("api/v1/customers/1/accounts/999"), c("api/v1/customers/${regex('[0-9]+')}/accounts/999")) )
			headers {
				contentType(applicationJson())
			}
		}
		response {
			status 404
		}
	}
]