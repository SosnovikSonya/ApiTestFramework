package users;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.Routes;

public class UsersApi {

    public static Response getUserByEmail(String email)
    {
        RestAssured.baseURI = Routes.baseUrl;
        Response response = RestAssured.given().queryParam("email", email).when().get(Routes.getUserUrl);
        return response;
    }

    public static Response registerUser(Object newUser)
    {
        RestAssured.baseURI = Routes.baseUrl;
        Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON).body(newUser)
                           .when().post(Routes.createUserUrl);
        return response;
    }

    public static Response deleteUser(String email)
    {
        RestAssured.baseURI = Routes.baseUrl;
        Response response = RestAssured.given().queryParam("email", email).when().delete(Routes.deleteUserUrl);
        return response;
    }

    public static Response updateUserField(Object request)
    {
        RestAssured.baseURI = Routes.baseUrl;
        Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON).body(request)
                           .when().put(Routes.updateUserFieldUrl);
        return response;
    }
}
