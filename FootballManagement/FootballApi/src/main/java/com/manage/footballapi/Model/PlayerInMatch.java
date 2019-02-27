package com.manage.footballapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "players_in_match")
public class PlayerInMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_in_tour_id")
    @JsonIgnore
    private PlayerInTournament playersInTour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    @JsonIgnore
    private GamePosition position;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MatchDetails> player_match_details;

    private int sum_goals_in_match;
    private int sum_yellow_card_in_match;
    private int sum_red_card_in_match;

    public PlayerInMatch(){
        this.player_match_details = new ArrayList<>();
        this.sum_goals_in_match = 0;
        this.sum_red_card_in_match = 0;
        this.sum_yellow_card_in_match = 0;
    }

    public PlayerInMatch(PlayerInTournament playersInTour, Match match, GamePosition position) {
        this();
        this.playersInTour = playersInTour;
        this.match = match;
        this.position = position;
    }

    public PlayerInMatch(Long id,PlayerInTournament playersInTour, Match match, GamePosition position,
                         int sum_goals_in_match, int sum_yellow_card_in_match, int sum_red_card_in_match) {
        this.playersInTour = playersInTour;
        this.match = match;
        this.position = position;
        this.sum_goals_in_match = sum_goals_in_match;
        this.sum_yellow_card_in_match = sum_yellow_card_in_match;
        this.sum_red_card_in_match = sum_red_card_in_match;
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

    public PlayerInTournament getPlayersInTour() {
        return playersInTour;
    }

    public void setPlayersInTour(PlayerInTournament playersInTour) {
        this.playersInTour = playersInTour;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public GamePosition getPosition() {
        return position;
    }

    public void setPosition(GamePosition position) {
        this.position = position;
    }

    public int getSum_goals_in_match() {
        return sum_goals_in_match;
    }

    public void setSum_goals_in_match(int sum_goals_in_match) {
        this.sum_goals_in_match = sum_goals_in_match;
    }

    public int getSum_yellow_card_in_match() {
        return sum_yellow_card_in_match;
    }

    public void setSum_yellow_card_in_match(int sum_yellow_card_in_match) {
        this.sum_yellow_card_in_match = sum_yellow_card_in_match;
    }

    public int getSum_red_card_in_match() {
        return sum_red_card_in_match;
    }

    public void setSum_red_card_in_match(int sum_red_card_in_match) {
        this.sum_red_card_in_match = sum_red_card_in_match;
    }

    public List<MatchDetails> getPlayer_match_details() {
        return player_match_details;
    }

    public void setPlayer_match_details(List<MatchDetails> player_match_details) {
        this.player_match_details = player_match_details;
    }
}
