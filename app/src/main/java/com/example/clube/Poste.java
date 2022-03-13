package com.example.clube;


public class Poste {

    private String description;
    private String publisher;
    private String imageURL;
    private String postid;
    public Poste() {

    }

    public Poste(String description, String url,String publisher,String postid) {
        this.postid=postid;
        this.description = description;

        this.imageURL= url;
        this.publisher=publisher;
    }

    public String getdescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }
    public String getpublisher() {
        return publisher;
    }

    public String getpostid() {
        return postid;
    }


}