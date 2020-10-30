package com.example.ebank.controllers;

import java.util.HashMap;
import java.util.Map;

import com.example.ebank.models.AppStatus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "app", tags = "app", description = "the API status")
@RequestMapping("/api")
@RestController
public class AppStatusController {
    @Value("${app.version}")
    private String appVersion = "1.0.0";
    
    @ApiOperation(value = "Get API status", nickname = "getStatus", notes = "", response = AppStatus.class, responseContainer = "Object", tags = {
            "app", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = AppStatus.class, responseContainer = "Object"),
            @ApiResponse(code = 400, message = "Error"), @ApiResponse(code = 404, message = "Error") })
    @RequestMapping(value = "", produces = { "application/json" }, method = RequestMethod.GET)
    public Map getStatus() {
        Map map = new HashMap<String, String>();
        map.put("app-version", appVersion);
        return map;
    }
    
}
