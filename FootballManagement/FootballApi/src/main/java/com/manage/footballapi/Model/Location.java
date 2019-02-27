package com.manage.footballapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private  String desciption;
    private Long number_holding_people;
    private String image;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Match>matches;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Team teamLocation;

    public Location(){
        matches = new ArrayList<>();
    }

    public Location(String name, String address, String desciption) {
        this();
        this.name = name;
        this.address = address;
        this.desciption = desciption;
    }

    public Location(Long id, String name, String address, String desciption, String image, Long number_holding_people, Team team) {
        this(name, address, desciption);
        this.number_holding_people = number_holding_people;
        this.image = image;
        this.teamLocation = team;
        if(this.id != null){
            this.id = id;
        }
    }

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

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Long getNumber_holding_people() {
        return number_holding_people;
    }

    public void setNumber_holding_people(Long number_holding_people) {
        this.number_holding_people = number_holding_people;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Team getTeamLocation() {
        return teamLocation;
    }

    public void setTeamLocation(Team teamLocation) {
        this.teamLocation = teamLocation;
    }
}
