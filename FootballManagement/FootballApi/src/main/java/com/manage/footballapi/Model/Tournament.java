package com.manage.footballapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tournament")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date started_date;
    private Date ended_date;
    private Long number_of_teams;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tournament", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TeamInTournament> teamsInTournament;

    public Tournament(){
        this.teamsInTournament = new ArrayList<TeamInTournament>();
    }

    public Tournament(String name, Date started_date, Date ended_date, Long number_of_teams) {
        this();
        this.name = name;
        this.started_date = started_date;
        this.ended_date = ended_date;
        this.number_of_teams = number_of_teams;
    }

    public Tournament(Long id, String name, Date started_date, Date ended_date, Long number_of_teams) {
        this(name, started_date, ended_date, number_of_teams);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStarted_date() {
        return started_date;
    }

    public void setStarted_date(Date started_date) {
        this.started_date = started_date;
    }

    public Date getEnded_date() {
        return ended_date;
    }

    public void setEnded_date(Date ended_date) {
        this.ended_date = ended_date;
    }

    public Long getNumber_of_teams() {
        return number_of_teams;
    }

    public void setNumber_of_teams(Long number_of_teams) {
        this.number_of_teams = number_of_teams;
    }

    public List<TeamInTournament> getTeamsInTournament() {
        return teamsInTournament;
    }

    public void setTeamsInTournament(List<TeamInTournament> teamsInTournament) {
        this.teamsInTournament = teamsInTournament;
    }
}
