import org.springframework.cloud.contract.spec.Contract

[
	Contract.make {
		name("get customer not found response")
		request {
			method('GET')
			url( $(p("api/v1/customers/999"), c("api/v1/customers/999")) )
			headers {
				contentType(applicationJson())
			}
		}
		response {
			status 404
		}
	},
	Contract.make {
		name("get customer with invalid customer id")
		request {
			method('GET')
			url( $(p("api/v1/customers/12c"), c("api/v1/customers/${regex('(?!^\\d+$)^.+$')}")) )
			headers {
				contentType(applicationJson())
			}
		}
		response {
			status 400
		}
	}
]