package usersApiTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import users.UsersApi;
import users.requestModels.RegisterUserRequestModel;
import users.responseModels.ErrorResponseModel;
import users.responseModels.UserResponseModel;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterUser {

    private static Faker faker;
    private RegisterUserRequestModel newUser;

    @BeforeAll
    public static void SetUp(){
        faker = new Faker();
    }

    @Test
    @Tag("positive")
    public void RegisterUser_ValidData() throws JsonProcessingException {
        newUser = new RegisterUserRequestModel
                (faker.name().fullName(), faker.internet().safeEmailAddress(), faker.internet().password());
        Response response = UsersApi.registerUser(newUser);
        UserResponseModel responseUser = new ObjectMapper().readValue(response.getBody().asString(), UserResponseModel.class);
        System.out.println(responseUser.toString());
        assertEquals(200,  response.getStatusCode(), "Status Code is not 200 OK");
        assertEquals(newUser.getEmail(), responseUser.getEmail(), "User email is not " + newUser.getEmail());
        assertEquals(newUser.getName(), responseUser.getName(), "User name is not " + newUser.getName());
    }

    @Test
    @Tag("negative")
    public void RegisterUser_WithoutEmail() throws JsonProcessingException {
        RegisterUserRequestModel newUser = new RegisterUserRequestModel
                (faker.name().fullName(), faker.internet().password());
        Response response = UsersApi.registerUser(newUser);
        ErrorResponseModel responseError = new ObjectMapper().readValue(response.getBody().asString(), ErrorResponseModel.class);
        System.out.println(responseError.toString());
        assertEquals(200,  response.getStatusCode(), "Status Code is not 200 OK");
        assertEquals(responseError.getType(),"error", "Response is not error");
    }

    @Test
    @Tag("negative")
    public void RegisterUser_WithInvalidEmailSyntax() throws JsonProcessingException {
        RegisterUserRequestModel newUser = new RegisterUserRequestModel
                (faker.name().fullName(), faker.internet().safeEmailAddress().split("@")[0], faker.internet().password());
        Response response = UsersApi.registerUser(newUser);
        ErrorResponseModel responseError = new ObjectMapper().readValue(response.getBody().asString(), ErrorResponseModel.class);
        System.out.println(responseError.toString());
        assertEquals(200,  response.getStatusCode(), "Status Code is not 200 OK");
        assertEquals(responseError.getType(),"error", "Response is not error");
    }
}
