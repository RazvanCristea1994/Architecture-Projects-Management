package model.validators;

public class ClientValidator {

    public String validate(String firstName, String lastName){

        StringBuilder messages = new StringBuilder();

        if (firstName.length() < 2) {
            messages.append("\nFirst name cannot be shorter than 2 characters.");
        }
        if (lastName.length() < 2) {
            messages.append("\nLast name cannot be shorter than 2 characters.");
        }
        if (firstName.length() > 15) {
            messages.append("\nFirst name cannot be longer than 15 characters.");
        }
        if (lastName.length() > 15) {
            messages.append("\nLast name cannot be longer than 15 characters.");
        }
        return messages.toString();
    }
}
