package com.manage.footballapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date happened_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id")
    @JsonIgnore
    private TeamInTournament homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_team_id")
    @JsonIgnore
    private TeamInTournament visitTeam;

    private Integer homeGoals;
    private Integer visitGoals;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonIgnore
    private Location location;

    private String dayNo;
    private String description;

    private String finishMatch;
    private String finshAssignPlayer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "match", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PlayerInMatch> players_in_match;

    public Match() {
        this.players_in_match = new ArrayList<PlayerInMatch>();
    }

    public Match(Date happened_date, TeamInTournament homeTeam, TeamInTournament visitTeam,
                 Location location, String dayNo, int homeGoals, int visitGoals) {
        this();
        this.happened_date = happened_date;
        this.homeTeam = homeTeam;
        this.visitTeam = visitTeam;
        this.location = location;
        this.dayNo = dayNo;
        this.homeGoals = homeGoals;
        this.visitGoals = visitGoals;

    }

    public Match(Long id, Date happened_date, TeamInTournament homeTeam, TeamInTournament visitTeam,
                 Location location, String description, String dayNo, int homeGoals, int visitGoals) {
        this(happened_date, homeTeam, visitTeam, location, dayNo, homeGoals, visitGoals);
        this.description = description;
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

    public Date getHappened_date() {
        return happened_date;
    }

    public void setHappened_date(Date happened_date) {
        this.happened_date = happened_date;
    }

    public TeamInTournament getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(TeamInTournament homeTeam) {
        this.homeTeam = homeTeam;
    }

    public TeamInTournament getVisitTeam() {
        return visitTeam;
    }

    public void setVisitTeam(TeamInTournament visitTeam) {
        this.visitTeam = visitTeam;
    }

    public Integer getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
    }

    public Integer getVisitGoals() {
        return visitGoals;
    }

    public void setVisitGoals(Integer visitGoals) {
        this.visitGoals = visitGoals;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDayNo() {
        return dayNo;
    }

    public void setDayNo(String dayNo) {
        this.dayNo = dayNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFinishMatch() {
        return finishMatch;
    }

    public void setFinishMatch(String finishMatch) {
        this.finishMatch = finishMatch;
    }

    public String getFinshAssignPlayer() {
        return finshAssignPlayer;
    }

    public void setFinshAssignPlayer(String finshAssignPlayer) {
        this.finshAssignPlayer = finshAssignPlayer;
    }
}
