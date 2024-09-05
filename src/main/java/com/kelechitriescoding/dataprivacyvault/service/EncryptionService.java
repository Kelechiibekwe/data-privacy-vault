package com.kelechitriescoding.dataprivacyvault.service;

import com.kelechitriescoding.dataprivacyvault.model.User;
import com.kelechitriescoding.dataprivacyvault.request.UserDto;
import com.kelechitriescoding.dataprivacyvault.util.AESUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class EncryptionService {

    private final String algorithm = "AES/CBC/PKCS5Padding";
    private static final Map<String, User> userDatabase = new HashMap<>();
    private static final ModelMapper modelMapper = new ModelMapper();

    public UserDto encryptUser(UserDto user, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, InvalidKeyException, BadPaddingException, ClassNotFoundException {
        // Generate a random salt for the user
        String salt = generateRandomSalt();
        // Derive a secret key from the password and salt
        SecretKey secretKey = AESUtil.getKeyFromPassword(password, salt);
        // Generate an IV (Initialization Vector)
        IvParameterSpec ivParameterSpec = AESUtil.generateIv();

        User encryptedUser = new User();
        encryptedUser.setName(AESUtil.encryptPasswordBased(algorithm, user.getName(), secretKey, ivParameterSpec));
        encryptedUser.setEmail(AESUtil.encryptPasswordBased(algorithm, user.getEmail(), secretKey, ivParameterSpec));
        encryptedUser.setPhoneNumber(AESUtil.encryptPasswordBased(algorithm, user.getPhoneNumber(), secretKey, ivParameterSpec));
        encryptedUser.setSalt(salt);
        encryptedUser.setIvParameterSpec(ivParameterSpec);

        userDatabase.put(encryptedUser.getEmail(), encryptedUser);
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(encryptedUser, UserDto.class);
        return userDto;
    }

    public UserDto decryptUser(UserDto user, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, InvalidKeyException, BadPaddingException, ClassNotFoundException {
        String salt = userDatabase.get(user.getEmail()).getSalt();
        SecretKey secretKey = AESUtil.getKeyFromPassword(password, salt);
        IvParameterSpec ivParameterSpec = userDatabase.get(user.getEmail()).getIvParameterSpec();

        User decryptedUser = new User();
        decryptedUser.setName(AESUtil.decryptPasswordBased(algorithm, user.getName(), secretKey, ivParameterSpec));
        decryptedUser.setEmail(AESUtil.decryptPasswordBased(algorithm, user.getEmail(), secretKey, ivParameterSpec));
        decryptedUser.setPhoneNumber(AESUtil.decryptPasswordBased(algorithm, user.getPhoneNumber(), secretKey, ivParameterSpec));
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(decryptedUser, UserDto.class);

        return userDto;
    }


    // Generate a random salt for each encryption process
    private String generateRandomSalt() {
        byte[] salt = new byte[16];  // 16 bytes = 128 bits
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return AESUtil.encodeBase64(salt);  // Use a helper method to convert bytes to a Base64 string
    }

    //

}
