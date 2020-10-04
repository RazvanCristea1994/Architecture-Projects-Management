package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction extends Entity{

    private int idProject;
    private int clientCard;
    private int numberOfProjects;
    private LocalDateTime dateTime;

    public Transaction(int id, int idProject, int clientCard, int numberOfProjects) {
        super(id);
        this.idProject = idProject;
        this.clientCard = clientCard;
        this.numberOfProjects = numberOfProjects;
        this.dateTime = dataTimeFormat();
    }

    @Override
    public String toString() {
        return "Transaction  {" +
                "idBooking=" + getId() +
                ", idProject=" + idProject +
                ", clientCard=" + clientCard +
                ", numberOfProjects=" + numberOfProjects +
                ", dateTime=" + dateTime +
                "}";
    }


    public int getNumberOfProjects() {
        return numberOfProjects;
    }

    public void setNumberOfProjects(int numberOfProjects) {
        this.numberOfProjects = numberOfProjects;
    }

    public int getIdBooking() {
        return getId();
    }

    public int getIdProject() {
        return idProject;
    }

    public int getClientCard() {
        return clientCard;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    private LocalDateTime dataTimeFormat(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = dateTime.format(myFormat);
        LocalDateTime formattedDateTime = LocalDateTime.parse(formattedDate);
        return formattedDateTime;
    }
}
