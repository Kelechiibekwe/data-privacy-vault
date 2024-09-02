package com.kelechitriescoding.dataprivacyvault.service;

import com.kelechitriescoding.dataprivacyvault.request.UserDto;
import com.kelechitriescoding.dataprivacyvault.util.AESUtil;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

@Service
public class EncryptionService {

    public UserDto encryptUser(UserDto user) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, InvalidKeyException, BadPaddingException, ClassNotFoundException {

        // TODO: remove the hardcoded password
        //TODO: Encrypt each property of a POJO
        String password = "password";
        String salt = "12345678";
        String plainText = "www.kelechitriescoding.com";
        IvParameterSpec ivParameterSpec = AESUtil.generateIv();
        SecretKey key = AESUtil.getKeyFromPassword(password,salt);
        String algorithm = "AES/CBC/PKCS5Padding";

        // Use ModelMapper to map the original UserDto to a new instance (shallow copy)
        ModelMapper modelMapper = new ModelMapper();
        UserDto encryptedUser = modelMapper.map(user, UserDto.class);

        encryptFields(user, key, ivParameterSpec, algorithm);

        return user;
    }

    private void encryptFields(UserDto userDto, SecretKey key, IvParameterSpec iv, String algorithm) {
        // Encrypt each String field in the UserDto
        Arrays.stream(userDto.getClass().getDeclaredFields())
                .filter(field -> field.getType().equals(String.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        String originalValue = (String) field.get(userDto);
                        if (originalValue != null) {
                            String encryptedValue = AESUtil.encryptPasswordBased(algorithm, originalValue, key, iv);
                            field.set(userDto, encryptedValue);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Error encrypting field: " + field.getName(), e);
                    }
                });
    }


}
