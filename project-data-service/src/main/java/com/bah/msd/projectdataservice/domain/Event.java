package com.bah.msd.projectdataservice.domain;

public class Event {
    private String location;
    private String name;

    public Event(String name, String location) {
        this.location = location;
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    

    
}
