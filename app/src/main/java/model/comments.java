package model;

public class comments {
    private String comment;
    private String publisher;


    public comments(String comment, String publisher) {
        this.comment = comment;
        this.publisher = publisher;
    }

    public comments() {

    }

    public String getComment() {
        return comment;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
