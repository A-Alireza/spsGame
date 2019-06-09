package com.spsgame.message;


public class messageObjectFromClientToServer {

    private String name;
    private String choice;

    public messageObjectFromClientToServer() {
    }



    public messageObjectFromClientToServer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public void setName(String name) { this.name = name; }
}
