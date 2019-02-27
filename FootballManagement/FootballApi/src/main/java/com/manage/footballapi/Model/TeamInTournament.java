package com.manage.footballapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team_tournament")
public class TeamInTournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    @JsonIgnore
    private Tournament tournament;

    private Long outward_journey_score;
    private Long homeward_journey_score;
    private Long total;
    private Integer mW;
    private Integer mD;
    private Integer mL;
    private Integer gF;
    private Integer gA;
    private Integer diff;
    private String weekNo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teamtour", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PlayerInTournament> players;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "homeTeam", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Match>matches_as_home;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "visitTeam", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Match>matches_as_vistting;

    public TeamInTournament(){
        this.players = new ArrayList<PlayerInTournament>();
        this.matches_as_home = new ArrayList<Match>();
        this.matches_as_vistting = new ArrayList<Match>();
    }

    public TeamInTournament(Team team, Tournament tournament, Long total_score) {
        this();
        this.team = team;
        this.tournament = tournament;
        this.total = total_score;
    }


    public TeamInTournament(Long id, Team team, Tournament tournament, Long outward_journey_socre,
                            Long homeward_journey_score, Long total_score, Integer mW, Integer mD, Integer mL,
                            Integer gF, Integer gA, Integer diff, String weekNo) {
        this(team, tournament, total_score);
        if(id != null){
            this.id = id;
        }
        this.homeward_journey_score = homeward_journey_score;
        this.outward_journey_score = outward_journey_socre;
        this.total = total_score;
        this.mW = mW;
        this.mD = mD;
        this.mL = mL;
        this.gF = gF;
        this.gA = gA;
        this.diff = diff;
        this.weekNo = weekNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Long getOutward_journey_score() {
        return outward_journey_score;
    }

    public void setOutward_journey_score(Long outward_journey_socre) {
        this.outward_journey_score = outward_journey_socre;
    }

    public Long getHomeward_journey_score() {
        return homeward_journey_score;
    }

    public void setHomeward_journey_score(Long homeward_journey_score) {
        this.homeward_journey_score = homeward_journey_score;
    }

    public Long getTotal_score() {
        return total;
    }

    public void setTotal_score(Long total_score) {
        this.total = total_score;
    }

    public List<PlayerInTournament> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerInTournament> players) {
        this.players = players;
    }

    public List<Match> getMatches_as_home() {
        return matches_as_home;
    }

    public void setMatches_as_home(List<Match> matches_as_home) {
        this.matches_as_home = matches_as_home;
    }

    public List<Match> getMatches_as_vistting() {
        return matches_as_vistting;
    }

    public void setMatches_as_vistting(List<Match> matches_as_vistting) {
        this.matches_as_vistting = matches_as_vistting;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getmW() {
        return mW;
    }

    public void setmW(Integer mW) {
        this.mW = mW;
    }

    public Integer getmD() {
        return mD;
    }

    public void setmD(Integer mD) {
        this.mD = mD;
    }

    public Integer getmL() {
        return mL;
    }

    public void setmL(Integer mL) {
        this.mL = mL;
    }

    public Integer getgF() {
        return gF;
    }

    public void setgF(Integer gF) {
        this.gF = gF;
    }

    public Integer getgA() {
        return gA;
    }

    public void setgA(Integer gA) {
        this.gA = gA;
    }

    public Integer getDiff() {
        return diff;
    }

    public void setDiff(Integer diff) {
        this.diff = diff;
    }

    public String getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(String weekNo) {
        this.weekNo = weekNo;
    }
}
