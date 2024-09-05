package com.kelechitriescoding.dataprivacyvault.service;

import com.kelechitriescoding.dataprivacyvault.model.User;
import com.kelechitriescoding.dataprivacyvault.request.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

public class EncryptionServiceTest {


    private EncryptionService encryptionService;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize the encryption service before each test
        encryptionService = new EncryptionService();
    }

    @Test
    public void testEncryptUser() throws Exception {
        // Arrange: Prepare a sample UserDto object
        UserDto userDto = new UserDto("John Doe", "something@gmail.com", "123456789");
        String password = "password";

        // Act: Encrypt the user data
        UserDto encryptedUser = encryptionService.encryptUser(userDto,password);
        UserDto decryptedUser = encryptionService.decryptUser(encryptedUser,password);

        // Assert: Ensure the encrypted fields are not the same as the original
        assertEquals(userDto.getName(), decryptedUser.getName());
        assertEquals(userDto.getPhoneNumber(), decryptedUser.getPhoneNumber());
        assertEquals(userDto.getEmail(), decryptedUser.getEmail());
    }


}
