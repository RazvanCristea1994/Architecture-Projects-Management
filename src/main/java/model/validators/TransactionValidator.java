package model.validators;

public class TransactionValidator {

    public String validate(int clientCard, int numberOfTickets){

        StringBuilder messages = new StringBuilder();

        if(clientCard < 1000){
            messages.append("\nThe client card must have at least 4 digits.");
        }
        if(numberOfTickets < 1){
            messages.append("\nIt is not possible to buy less than one project.");
        }
        return messages.toString();
    }
}
