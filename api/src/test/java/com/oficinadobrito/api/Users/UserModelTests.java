package com.oficinadobrito.api.Users;

import com.oficinadobrito.api.Entities.UserRole;
import com.oficinadobrito.api.Entities.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Field;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class UserModelTests {

    private Usuario user;
    private String novo;

    @BeforeEach
    void setUp(){
        user = new Usuario();
        user.setUsuarioId(UUID.randomUUID().toString());
        novo = "Caleby";
    };

    @DisplayName("User must be an implementation and userdetail")
    @Test
    void testUserMustBeAnImplementationAndUserdetail() {
        //Given - Arrange / When  - Act
        //Then  - Assert
        Assertions.assertTrue(this.user instanceof UserDetails, ()-> "User is not implementing userDetail, as it should");
    }

    @DisplayName("When a user is created, the usuarioId attribute must exist")
    @Test
    void testWhenAUserIsCreatedTheIdAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        Field id = Usuario.class.getDeclaredField("usuarioId");

        //When  - Act
        this.novo = UUID.randomUUID().toString();
        this.user.setUsuarioId(novo);

        //Then  - Assert
        Assertions.assertNotNull(id, ()->"The id attribute does not exist in user");
        Assertions.assertEquals(String.class,id.getType(), ()-> "the type of the user ID attribute must be one UUID");
        Assertions.assertEquals(novo,this.user.getUsuarioId(), () -> "Error in methods setId or getId");
    }

    @DisplayName("When a user is created, the username attribute must exist")
    @Test
    void testWhenAUserIsCreatedTheUsernameAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        Field username = Usuario.class.getDeclaredField("username");

        //When  - Act
        this.user.setUsername(this.novo);

        //Then  - Assert
        Assertions.assertNotNull(username, ()->"The username attribute does not exist in user");
        Assertions.assertEquals(String.class,username.getType(), ()-> "the type of the user username attribute must be one String");
        Assertions.assertEquals(novo.toUpperCase(),this.user.getUsername().toUpperCase(), () -> "Error in methods setUsername or getUsername");
    }

    @DisplayName("When a user is created, the email attribute must exist")
    @Test
    void testWhenAUserIsCreatedTheEmailAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        Field email = Usuario.class.getDeclaredField("email");

        //When  - Act
        this.novo = "Caleby@gmail.com";
        this.user.setEmail(novo);

        //Then  - Assert
        Assertions.assertNotNull(email, ()->"The email attribute does not exist in user");
        Assertions.assertEquals(String.class,email.getType(), ()-> "the type of the user email attribute must be one String");
        Assertions.assertEquals(novo.toUpperCase(),this.user.getEmail().toUpperCase(), () -> "Error in methods setEmail or getEMail");
    }

    @DisplayName("When a user is created, the username attribute must exist")
    @Test
    void testWhenAUserIsCreatedThePasswordAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        Field password = Usuario.class.getDeclaredField("password");

        //When  - Act
        this.user.setPassword(novo);

        //Then  - Assert
        Assertions.assertNotNull(password, ()->"The password attribute does not exist in user");
        Assertions.assertEquals(String.class,password.getType(), ()-> "the type of the user password attribute must be one String");
        Assertions.assertEquals(novo.toUpperCase(),this.user.getPassword().toUpperCase(), () -> "Error in methods setPassword or getPassword");
    }

    @DisplayName("When a user is created, the role attribute must exist and can be defined with the types, Admin, user, vendedor e gerente")
    @Test
    void testWhenAUserIsCreatedTheRoleAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        Field role = Usuario.class.getDeclaredField("role");
        //When  - Act
      
        //Then  - Assert
        Assertions.assertEquals(4, UserRole.values().length, ()-> "error the total number of values the UserRole enumeration must be 4: ADMIN, USER, VENDEDOR, GERENTE");
        Assertions.assertDoesNotThrow(()->UserRole.valueOf("ADMIN"), () -> "UserRole does not contain ADMIN");
        Assertions.assertDoesNotThrow(()->UserRole.valueOf("USER"), () -> "UserRole does not contain USER");
        Assertions.assertDoesNotThrow(()->UserRole.valueOf("VENDEDOR"),() -> "UserRole does not contain VENDEDOR");
        Assertions.assertDoesNotThrow(()->UserRole.valueOf("GERENTE"), () -> "UserRole does not contain GERENTE");
        Assertions.assertNotNull(role, () -> "The role attribute does not exist in user");
        Assertions.assertEquals(UserRole.class, role.getType(), ()-> "The type of the user role attribute must be one UserRole");
    }
}
