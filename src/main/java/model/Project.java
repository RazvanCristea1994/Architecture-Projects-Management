package model;

public class Project extends Entity{

    private String title;
    private int year;
    private int price;
    private boolean isInProgress;

    public int ticketsSold;

    public Project(int id, String title, int year, int price, boolean isInProgress) {
        super(id);
        this.title = title;
        this.year = year;
        this.price = price;
        this.isInProgress = isInProgress;
    }

    @Override
    public String toString() {
        return "Project   {" +
                "ID=" + getId() +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", isInProgress=" + isInProgress +
                ", ticketsSold=" + ticketsSold +
                '}';
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getIsInProgress() {
        return isInProgress;
    }

    public void setInProgress(boolean inProgress) {
        isInProgress = inProgress;
    }
}
