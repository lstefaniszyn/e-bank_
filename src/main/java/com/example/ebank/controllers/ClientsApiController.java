package com.example.ebank.api;

import com.example.ebank.api.ClientsApi;
import com.example.ebank.models.Account;
import com.example.ebank.models.Client;
import com.example.ebank.models.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class ClientsApiController implements ClientsApi {

    private static final Logger log = LoggerFactory.getLogger(ClientsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ClientsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Transaction>> getAccountTransactions(
            @ApiParam(value = "The id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable("id") String id,
            @ApiParam(value = "The idAccount that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable("idAccount") String idAccount) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Transaction>>(objectMapper.readValue(
                        "[ {\n  \"date\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"iban\" : \"iban\",\n  \"description\" : \"description\",\n  \"id\" : 0,\n  \"value\" : {\n    \"amount\" : 6,\n    \"currency\" : {\n      \"code\" : \"code\",\n      \"name\" : \"name\"\n    }\n  }\n}, {\n  \"date\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"iban\" : \"iban\",\n  \"description\" : \"description\",\n  \"id\" : 0,\n  \"value\" : {\n    \"amount\" : 6,\n    \"currency\" : {\n      \"code\" : \"code\",\n      \"name\" : \"name\"\n    }\n  }\n} ]",
                        List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Transaction>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Account>> getClientAccounts(
            @ApiParam(value = "The id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Account>>(objectMapper.readValue(
                        "[ {\n  \"name\" : \"name\",\n  \"currency\" : {\n    \"code\" : \"code\",\n    \"name\" : \"name\"\n  },\n  \"id\" : 6\n}, {\n  \"name\" : \"name\",\n  \"currency\" : {\n    \"code\" : \"code\",\n    \"name\" : \"name\"\n  },\n  \"id\" : 6\n} ]",
                        List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Account>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Account>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Client> getClientById(
            @ApiParam(value = "The id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Client>(objectMapper.readValue(
                        "{\n  \"givenName\" : \"givenName\",\n  \"familyName\" : \"familyName\",\n  \"id\" : 0,\n  \"accounts\" : [ {\n    \"name\" : \"name\",\n    \"currency\" : {\n      \"code\" : \"code\",\n      \"name\" : \"name\"\n    },\n    \"id\" : 6\n  }, {\n    \"name\" : \"name\",\n    \"currency\" : {\n      \"code\" : \"code\",\n      \"name\" : \"name\"\n    },\n    \"id\" : 6\n  } ]\n}",
                        Client.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Client>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Client>> getClients() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Client>>(objectMapper.readValue(
                        "[ {\n  \"givenName\" : \"givenName\",\n  \"familyName\" : \"familyName\",\n  \"id\" : 0,\n  \"accounts\" : [ {\n    \"name\" : \"name\",\n    \"currency\" : {\n      \"code\" : \"code\",\n      \"name\" : \"name\"\n    },\n    \"id\" : 6\n  }, {\n    \"name\" : \"name\",\n    \"currency\" : {\n      \"code\" : \"code\",\n      \"name\" : \"name\"\n    },\n    \"id\" : 6\n  } ]\n}, {\n  \"givenName\" : \"givenName\",\n  \"familyName\" : \"familyName\",\n  \"id\" : 0,\n  \"accounts\" : [ {\n    \"name\" : \"name\",\n    \"currency\" : {\n      \"code\" : \"code\",\n      \"name\" : \"name\"\n    },\n    \"id\" : 6\n  }, {\n    \"name\" : \"name\",\n    \"currency\" : {\n      \"code\" : \"code\",\n      \"name\" : \"name\"\n    },\n    \"id\" : 6\n  } ]\n} ]",
                        List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Client>>(new ArrayList<Client>(Arrays.asList(new Client())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getTest() {
        return new ResponseEntity<String>("HEllo", HttpStatus.OK);
    }

}
