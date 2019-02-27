package com.manage.footballapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "players_in_tournament")
public class PlayerInTournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_season_id")
    @JsonIgnore
    private TeamInTournament teamtour;

    private int nbClothe;

    private Long totalRedCards;
    private Long totalYellowCards;
    private Long totalGoals;
    private Long totalMatchesJoined;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playersInTour", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PlayerInMatch> matches_of_player;

    public PlayerInTournament(){
        this.matches_of_player = new ArrayList<PlayerInMatch>();
    }

    public PlayerInTournament(Long id, Player player, TeamInTournament teamtour, int nbClothe, Long totalGoals,
                              Long totalRedCards, Long totalYellowCards, Long totalMatchesJoined) {
        this();
        this.player = player;
        this.teamtour = teamtour;
        this.nbClothe = nbClothe;
        this.totalGoals = totalGoals;
        this.totalMatchesJoined = totalMatchesJoined;
        this.totalRedCards = totalRedCards;
        this.totalYellowCards = totalYellowCards;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public TeamInTournament getTeamtour() {
        return teamtour;
    }

    public void setTeamtour(TeamInTournament teamtour) {
        this.teamtour = teamtour;
    }

    public int getNbClothe() {
        return nbClothe;
    }

    public void setNbClothe(int nbClothe) {
        this.nbClothe = nbClothe;
    }

    public List<PlayerInMatch> getMatches_of_player() {
        return matches_of_player;
    }

    public void setMatches_of_player(List<PlayerInMatch> matches_of_player) {
        this.matches_of_player = matches_of_player;
    }

    public Long getTotalRedCards() {
        return totalRedCards;
    }

    public void setTotalRedCards(Long totalRedCards) {
        this.totalRedCards = totalRedCards;
    }

    public Long getTotalYellowCards() {
        return totalYellowCards;
    }

    public void setTotalYellowCards(Long totalYellowCards) {
        this.totalYellowCards = totalYellowCards;
    }

    public Long getTotalGoals() {
        return totalGoals;
    }

    public void setTotalGoals(Long totalGoals) {
        this.totalGoals = totalGoals;
    }

    public Long getTotalMatchesJoined() {
        return totalMatchesJoined;
    }

    public void setTotalMatchesJoined(Long totalMatchesJoined) {
        this.totalMatchesJoined = totalMatchesJoined;
    }
}
