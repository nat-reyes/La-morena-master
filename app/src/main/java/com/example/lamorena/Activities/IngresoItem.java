package com.example.lamorena.Activities;

public class IngresoItem  {

    private int imageItem;
    private String title;
    private String subtitle;

    public IngresoItem(int imageItem, String title, String subtitle) {
        this.imageItem = imageItem;
        this.title= title;
        this.subtitle= subtitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageItem() {
        return imageItem;
    }

    public void setImageItem(int imageItem) {
        this.imageItem = imageItem;
    }
}
