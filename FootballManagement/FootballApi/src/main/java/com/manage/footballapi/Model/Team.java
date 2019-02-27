package com.manage.footballapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logo_image;
    private String name;

    private String color;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TeamInTournament> tournaments;

    @OneToOne(mappedBy = "teamLocation")
    private Location location;


    public Team(){
        this.tournaments = new ArrayList<TeamInTournament>();
    }

    public Team( String name, String logo_image) {
        this();
        this.logo_image = logo_image;
        this.name = name;
    }

    public Team(Long id, String name, String logo_image, String color) {
        this(name, logo_image);
        this.color = color;
        if(id != null){
            this.id = id;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogo_image() {
        return logo_image;
    }

    public void setLogo_image(String logo_image) {
        this.logo_image = logo_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TeamInTournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<TeamInTournament> tournaments) {
        this.tournaments = tournaments;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
