package users.requestModels;

public class UpdateFieldRequestModel {

    private String email;
    private String field;
    private String value;

    public UpdateFieldRequestModel(String email, String field, String value) {
        this.email = email;
        this.field = field;
        this.value = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
