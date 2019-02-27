package com.manage.footballapi.API.ViewModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class LocationViewModel {

    private Long id;

    @NotNull
    @Min(5)
    private String name;
    private String address;
    private String description;
    private Long nbPeopleHolded;
    private String image;
    private Long teamId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getNbPeopleHolded() {
        return nbPeopleHolded;
    }

    public void setNbPeopleHolded(Long nbPeopleHolded) {
        this.nbPeopleHolded = nbPeopleHolded;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
