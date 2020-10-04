package service.validators;

import model.Transaction;
import model.Project;

public class TransactionServiceValidator {

    public String validate(Project project, Transaction transaction) {

        StringBuilder messages = new StringBuilder();

        if (project == null) {
            messages.append("\nProject ID not found.");
        }
        if (transaction == null){
            messages.append("\nThere is no transaction with this ID.");
        }
        return messages.toString();
    }

    public String validateMovie(Project project){

        StringBuilder messages = new StringBuilder();
        if (project == null) {
            messages.append("\nProject ID not found.");
        }
        return messages.toString();
    }

    public String validateHour(int initialHour, int finalHour){

        StringBuilder messages = new StringBuilder();

        if (initialHour < 1 || initialHour > 24 || finalHour < 1 || initialHour > 24 || initialHour >= finalHour){
            throw new IllegalArgumentException("The hours are not legal.");
        }
        return messages.toString();
    }

    public String validateDay(int initialDay, int finalDay){

        StringBuilder messages = new StringBuilder();

        if (initialDay < 1 || finalDay > 31 || finalDay < 1 || initialDay > 31 || initialDay > finalDay){
            throw new IllegalArgumentException("The days are not legal.");
        }
        return messages.toString();
    }
}
