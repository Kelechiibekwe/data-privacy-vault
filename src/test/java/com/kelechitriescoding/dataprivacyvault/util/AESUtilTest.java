package com.kelechitriescoding.dataprivacyvault.util;

import com.kelechitriescoding.dataprivacyvault.request.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AESUtilTest {

    @Test
    void givenPassword_whenEncrypt_thenSuccess()
            throws InvalidKeySpecException, NoSuchAlgorithmException,
            IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
            InvalidAlgorithmParameterException, NoSuchPaddingException {

        String plainText = "www.kelechitriescoding.com";
        String password = "password";
        String salt = "12345678";
        IvParameterSpec ivParameterSpec = AESUtil.generateIv();
        SecretKey key = AESUtil.getKeyFromPassword(password,salt);
        String algorithm = "AES/CBC/PKCS5Padding";
        String cipherText = AESUtil.encryptPasswordBased(algorithm, plainText, key, ivParameterSpec);
        String decryptedCipherText = AESUtil.decryptPasswordBased(algorithm,
                cipherText, key, ivParameterSpec);
        Assertions.assertEquals(plainText, decryptedCipherText);
    }

}
