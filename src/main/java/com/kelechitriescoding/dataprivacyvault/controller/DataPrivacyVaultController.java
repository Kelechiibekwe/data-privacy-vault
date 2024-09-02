package com.kelechitriescoding.dataprivacyvault.controller;

import com.kelechitriescoding.dataprivacyvault.request.UserDto;
import com.kelechitriescoding.dataprivacyvault.service.EncryptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class DataPrivacyVaultController {

    private EncryptionService encryptionService;

    @PostMapping("/encrypt")
    public ResponseEntity<?> encryptUser(@RequestBody UserDto userDto) {
        try {
            return new ResponseEntity<>(encryptionService.encryptUser(userDto), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
