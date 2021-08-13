package com.example.hunger;

public class ScreenItem {

    String Title,Description;
    int ScreeenImg;

    public ScreenItem(String title, String description, int screeenImg) {
        Title = title;
        Description = description;
        ScreeenImg = screeenImg;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setScreeenImg(int screeenImg) {
        ScreeenImg = screeenImg;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public int getScreeenImg() {
        return ScreeenImg;
    }
}
