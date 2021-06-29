package usersApiTests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import users.UsersApi;
import users.requestModels.RegisterUserRequestModel;

public class CreateUserBeforeTest {

    protected RegisterUserRequestModel newUser;
    protected Faker faker;

    @BeforeEach
    public void SetUp(){
        faker = new Faker();
        newUser = new RegisterUserRequestModel
                (faker.name().fullName(), faker.internet().safeEmailAddress(), faker.internet().password());
        UsersApi.registerUser(newUser);
    }
}
