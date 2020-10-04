package model.validators;

import java.time.LocalDateTime;

public class ProjectValidator {

    public String validate(String title, int releaseYear, int ticketPrice){

        StringBuilder messages = new StringBuilder();

        if(title.length() <= 2){
            messages.append("\nThe name of the movie should be longer than 2 characters.");
        }
        if(releaseYear > LocalDateTime.now().getYear()){
            messages.append("\nThe year is too big.");
        }
        if(releaseYear < 1900){
            messages.append("\nThe year is to small.");
        }
        if(ticketPrice <= 0){
            messages.append("\nThe ticket price cannot be smaller/equal than/with 0");
        }
        return messages.toString();
    }
}
