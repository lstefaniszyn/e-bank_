package contracts.rest


import org.springframework.cloud.contract.spec.Contract

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
		url('/api')
		headers {
			contentType(applicationJson())
		}
	}
	response {
		status 200
		headers {
			contentType(applicationJson())
		body(
            'app-version': $(producer(regex('[0-9]+\\.[0-9]+\\.[0-9]+')), consumer('1.0.0'))
        )
		}
	}
}
