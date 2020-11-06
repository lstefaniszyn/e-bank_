import org.springframework.cloud.contract.spec.Contract


scriptDir = new File(getClass().protectionDomain.codeSource.location.path).parent
GroovyShell shell = new GroovyShell()
def currency = shell.parse(new File(scriptDir + '/../../models/CustomerModel.groovy'))


Contract.make {
	description("""
Represents a successful scenario of app status

```
given:
	/api endpoint
when:
	make GET request
then:
	we'll get app-status
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
		body(  $( p(currency.getJson()) , c(currency.getJson()) )  )
        // body(  $( p(currency.getJson()) , c(file('one_cutomer_response.json')) )  )
		}
	}
}
