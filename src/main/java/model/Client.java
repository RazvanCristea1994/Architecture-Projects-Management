package model;

import java.util.ArrayList;
import java.util.List;

public class Client extends Entity{

    private String firstName;
    private String lastName;
    private List<Integer> projectsBought;

    public Client(int id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.projectsBought = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Client  {" +
                "ID=" + getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", projectsBought=" + appendProjectsBought(projectsBought) +
                '}';
    }

    private String appendProjectsBought(List projectsBought){

        StringBuilder stringBuilder = new StringBuilder();
        projectsBought.forEach(stringBuilder::append);
        String listOfMovies = stringBuilder.toString();
        return listOfMovies;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getClientCard() {
        return getId();
    }

    public List<Integer> getProjectsBought() {
        return projectsBought;
    }

    public void setMovieSeen(Integer projectId) {
        this.projectsBought.add(projectId);
    }

    public void setMovieSeen(List<Integer> projectList) {
        for(Integer projectId : projectList){
            this.projectsBought.add(projectId);
        }
    }
}
