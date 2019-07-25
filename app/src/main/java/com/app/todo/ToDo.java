package com.app.todo;

public class ToDo {
    private String toDoDate;
    private String toDoTitle;
    private String toDoText;
    private int toDoColor;
    private boolean isClicked;
    private String toDoTime;

    public ToDo() {}


    public ToDo(int toDoColor, boolean isClicked) {
        this.toDoColor = toDoColor;
        this.isClicked = isClicked;
    }

    public ToDo(int toDoColor, String toDoTitle, String toDoText, String date, String time){
        this.toDoColor = toDoColor;
        this.toDoTitle = toDoTitle;
        this.toDoText = toDoText;
        this.toDoDate = date;
        toDoTime = time;
    }

    public ToDo(int toDoColor, String toDoTitle, String toDoText) {
        this.toDoColor = toDoColor;
        this.toDoTitle = toDoTitle;
        this.toDoText = toDoText;
    }

    public String getToDoDate() {
        return toDoDate;
    }

    public void setToDoDate(String toDoDate) {
        this.toDoDate = toDoDate;
    }

    public String getToDoTitle() {
        return toDoTitle;
    }

    public void setToDoTitle(String toDoTitle) {
        this.toDoTitle = toDoTitle;
    }

    public String getToDoText() {
        return toDoText;
    }

    public void setToDoText(String toDoText) {
        this.toDoText = toDoText;
    }

    public int getToDoColor() {
        return toDoColor;
    }

    public void setToDoColor(int toDoColor) {
        this.toDoColor = toDoColor;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public String getToDoTime() {
        return toDoTime;
    }

    public void setToDoTime(String toDoTime) {
        this.toDoTime = toDoTime;
    }
}
