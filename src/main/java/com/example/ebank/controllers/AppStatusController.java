package com.example.ebank.controllers;

import org.openapitools.api.AppApi;
import org.openapitools.dto.AppStatusDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppStatusController implements AppApi {

    @Value("${app.version}")
    private final String appVersion = "1.0.0";

    @Override
    public ResponseEntity<AppStatusDto> getStatus() {
        AppStatusDto dto = new AppStatusDto();
        dto.setAppVersion(appVersion);
        return ResponseEntity.ok(dto);
    }

}
