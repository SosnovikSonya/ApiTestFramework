package usersApiTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import users.responseModels.ErrorResponseModel;
import users.responseModels.UserResponseModel;
import users.UsersApi;
import static org.junit.jupiter.api.Assertions.*;

public class GetUser extends CreateUserBeforeTest {

    @Test
    @Tag("positive")
    public void GetUserByEmail_ExistingUser() throws JsonProcessingException {
        String email = newUser.getEmail();
        Response response = UsersApi.getUserByEmail(email);
        UserResponseModel responseUser = new ObjectMapper().readValue(response.getBody().asString(), UserResponseModel.class);
        System.out.println(responseUser.toString());
        assertEquals(200,  response.getStatusCode(), "Status Code is not 200 OK");
        assertEquals(email, responseUser.getEmail(), "User email is not " + email);
    }

    @Test
    @Tag("negative")
    public void GetUserByEmail_NotExistingUser() throws JsonProcessingException {
        //generate new email and trying to get user with this email
        String email = faker.internet().safeEmailAddress();
        //get user with not existing email
        Response response = UsersApi.getUserByEmail(email);
        ErrorResponseModel responseError = new ObjectMapper().readValue(response.getBody().asString(), ErrorResponseModel.class);
        System.out.println(responseError.toString());
        assertEquals(200,  response.getStatusCode(), "Status Code is not 200 OK");
        assertEquals(responseError.getType(),"error", "Response is not error");
        assertTrue(responseError.getMessage().contains(email), "Response message does not contain requested email");
    }

    @Test
    @Tag("negative")
    public void GetUserByEmail_InvalidEmailSyntax() throws JsonProcessingException {
        String email = newUser.getEmail().split("@")[0]; //trim string after symbol @ -> invalid email syntax
        Response response = UsersApi.getUserByEmail(email);
        ErrorResponseModel responseError = new ObjectMapper().readValue(response.getBody().asString(), ErrorResponseModel.class);
        System.out.println(responseError.toString());
        assertEquals(200,  response.getStatusCode(), "Status Code is not 200 OK");
        assertEquals(responseError.getType(),"error", "Response is not error");
    }
}
