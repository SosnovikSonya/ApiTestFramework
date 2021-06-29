package usersApiTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import users.UsersApi;
import users.requestModels.UpdateFieldRequestModel;
import users.responseModels.ErrorResponseModel;
import users.responseModels.UserResponseModel;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateUser extends CreateUserBeforeTest {

    @Test
    @Tag("positive")
    public void updateUserField_ExistingUser_NameField() throws JsonProcessingException {
        String email = newUser.getEmail();
        String newName = faker.name().fullName();
        UpdateFieldRequestModel requestModel = new UpdateFieldRequestModel(email, "name", newName);
        Response updateUserFieldResponse = UsersApi.updateUserField(requestModel);
        assertEquals(200,  updateUserFieldResponse.getStatusCode(), "Status Code is not 200 OK");
        //check that user name is new
        Response getUserResponse = UsersApi.getUserByEmail(email);
        UserResponseModel responseUser = new ObjectMapper().readValue(getUserResponse.getBody().asString(), UserResponseModel.class);
        System.out.println(responseUser.toString());
        assertEquals(200,  getUserResponse.getStatusCode(), "Status Code is not 200 OK");
        assertEquals(newName, responseUser.getName(), "User name is not new, it is - " + email);
    }

    @Test
    @Tag("negative")
    public void updateUserField_NotExistingUser() throws JsonProcessingException {
        //generate new email and trying to update user with this email
        String email = faker.internet().safeEmailAddress();
        String newName = faker.name().fullName();
        UpdateFieldRequestModel requestModel = new UpdateFieldRequestModel(email, "name", newName);
        Response updateUserFieldResponse = UsersApi.updateUserField(requestModel);

        ErrorResponseModel responseError = new ObjectMapper().readValue(updateUserFieldResponse.getBody().asString(), ErrorResponseModel.class);
        System.out.println(responseError.toString());
        assertEquals(200,  updateUserFieldResponse.getStatusCode(), "Status Code is not 200 OK");
        assertEquals(responseError.getType(),"error", "Response is not error");
    }
}
