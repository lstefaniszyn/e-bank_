import org.springframework.cloud.contract.spec.Contract

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
		}
		def NAME = '[\\w-_\\s\\.]+'
		body([
			id         : fromRequest().path(3), // get {customerId}
			givenName  : "Johnny",
			familyName : "Bravo",
			identityKey: "",
			accounts   : [[]],
			balance    : [
				value   : 123.12,
				currency: [
					code: "EUR"
				]
			]
		])
		bodyMatchers {
			jsonPath('id', byRegex(number()).asInteger())
			jsonPath('givenName', byRegex(NAME).asString())
			jsonPath('familyName', byRegex(NAME).asString())
			jsonPath('identityKey', byRegex(number()).asInteger())
			jsonPath('accounts', byType {
				// results in verification of size of array (min 1)
				minOccurrence(1)
			})
			jsonPath('balance.value', byRegex(aDouble()).asDouble())
			jsonPath('balance.currency.code', byRegex('[A-Z]{3}').asString())
		}
	}
}
