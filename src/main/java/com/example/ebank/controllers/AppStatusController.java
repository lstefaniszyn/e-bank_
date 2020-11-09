package com.example.ebank.controllers;

import com.example.ebank.generated.api.AppApi;
import com.example.ebank.generated.dto.AppStatusDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "app")
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
