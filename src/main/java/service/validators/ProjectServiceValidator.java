package service.validators;

import model.Project;

public class ProjectServiceValidator {

    public String validate(Project project){

        StringBuilder messages = new StringBuilder();
        if (project == null){
            messages.append("ID project not found.");
        }
        return messages.toString();
    }

    public String validateIncrease(int valueLimit, int valueToAdd){

        StringBuilder messages = new StringBuilder();
        if(valueLimit < 0){
            messages.append("Value limit cannot be smaller than 0");
        }
        if(valueToAdd < 0){
            messages.append("ValueToAdd cannot be smaller than 0");
        }
        return messages.toString();
    }
}
