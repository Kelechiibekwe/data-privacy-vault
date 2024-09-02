package com.kelechitriescoding.dataprivacyvault.request;

import java.io.Serializable;

public record UserDto(String name, int age, String phoneNumber) implements Serializable {
}
