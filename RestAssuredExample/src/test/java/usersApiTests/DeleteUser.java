package usersApiTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import users.UsersApi;
import users.responseModels.ErrorResponseModel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteUser extends CreateUserBeforeTest {

    @Test
    @Tag("positive")
    public void DeleteUser_ExistingUser() throws JsonProcessingException {
        String email = newUser.getEmail();
        //delete user
        Response deleteUserResponse = UsersApi.deleteUser(email);
        assertEquals(200,  deleteUserResponse.getStatusCode(), "Status Code is not 200 OK");
        //check that user doesn't exist via getUserByEmail method
        Response getUserResponse = UsersApi.getUserByEmail(email);
        ErrorResponseModel responseError = new ObjectMapper().readValue(getUserResponse.getBody().asString(), ErrorResponseModel.class);
        assertEquals(responseError.getType(),"error", "Response is not error");
        assertTrue(responseError.getMessage().contains(email), "Response message does not contain requested email");
    }

    @Test
    @Tag("negative")
    public void DeleteUser_NotExistingUser() throws JsonProcessingException {
        //generate new email and trying to delete user with this email
        String email = faker.internet().safeEmailAddress();
        Response response = UsersApi.deleteUser(email);
        ErrorResponseModel responseError = new ObjectMapper().readValue(response.getBody().asString(), ErrorResponseModel.class);
        System.out.println(responseError.toString());
        assertEquals(200,  response.getStatusCode(), "Status Code is not 200 OK");
        assertEquals(responseError.getType(),"error", "Response is not error");
    }

    @Test
    @Tag("negative")
    public void DeleteUser_WithInvalidEmailSyntax() throws JsonProcessingException {
        String email = newUser.getEmail().split("@")[0];
        Response response = UsersApi.deleteUser(email);
        ErrorResponseModel responseError = new ObjectMapper().readValue(response.getBody().asString(), ErrorResponseModel.class);
        System.out.println(responseError.toString());
        assertEquals(200,  response.getStatusCode(), "Status Code is not 200 OK");
        assertEquals(responseError.getType(),"error", "Response is not error");
    }
}
