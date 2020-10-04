package service.validators;

import model.Client;

public class ClientServiceValidator {

    public String validate(Client client){

        StringBuilder messages = new StringBuilder();
        if (client == null){
            messages.append("ID client not found.");
        }
        return messages.toString();
    }
}
