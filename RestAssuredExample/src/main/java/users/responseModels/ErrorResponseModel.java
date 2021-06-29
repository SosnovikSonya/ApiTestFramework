package users.responseModels;

public class ErrorResponseModel {
    private String type;
    private String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponseModel{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
