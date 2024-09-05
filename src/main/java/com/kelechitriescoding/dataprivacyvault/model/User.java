package com.kelechitriescoding.dataprivacyvault.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.IvParameterSpec;

@Data
@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String name;
    String email;
    String phoneNumber;
    String salt;
    IvParameterSpec ivParameterSpec;
}
