package com.manage.footballapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "match_details")
public class MatchDetails {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_in_match_id")
    @JsonIgnore
    private PlayerInMatch player;

    private String cardReceived;
    private Integer action_time;
    private Boolean is_goal_from_11m;
    private Boolean is_the_bad_goal;
    private Boolean is_Goal;
    private Boolean is_Move_Out;
    private Boolean is_Move_In;

    public MatchDetails(){
        this.is_the_bad_goal= false;
        this.is_Goal= false;
        this.is_goal_from_11m = false;
    }

    public MatchDetails(Long id, PlayerInMatch player, String cardReceived,
                        Integer action_time, Boolean is_goal_from_11m, Boolean is_the_bad_goal, Boolean is_Goal,
                        Boolean is_Move_Out, Boolean is_Move_In) {
        this.player = player;
        this.cardReceived = cardReceived;
        this.action_time = action_time;
        this.is_goal_from_11m = is_goal_from_11m;
        this.is_the_bad_goal = is_the_bad_goal;
        this.is_Goal = is_Goal;
        this.is_Move_Out = is_Move_Out;
        this.is_Move_In = is_Move_In;
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

    public PlayerInMatch getPlayer() {
        return player;
    }

    public void setPlayer(PlayerInMatch player) {
        this.player = player;
    }

    public String getCardReceived() {
        return cardReceived;
    }

    public void setCardReceived(String cardReceived) {
        this.cardReceived = cardReceived;
    }

    public Integer getAction_time() {
        return action_time;
    }

    public void setAction_time(Integer action_time) {
        this.action_time = action_time;
    }

    public Boolean getIs_goal_from_11m() {
        return is_goal_from_11m;
    }

    public void setIs_goal_from_11m(Boolean is_goal_from_11m) {
        this.is_goal_from_11m = is_goal_from_11m;
    }

    public Boolean getIs_the_bad_goal() {
        return is_the_bad_goal;
    }

    public void setIs_the_bad_goal(Boolean is_the_bad_goal) {
        this.is_the_bad_goal = is_the_bad_goal;
    }

    public Boolean getIs_Goal() {
        return is_Goal;
    }

    public void setIs_Goal(Boolean is_Goal) {
        this.is_Goal = is_Goal;
    }

    public Boolean getIs_Move_Out() {
        return is_Move_Out;
    }

    public void setIs_Move_Out(Boolean is_Move_Out) {
        this.is_Move_Out = is_Move_Out;
    }

    public Boolean getIs_Move_In() {
        return is_Move_In;
    }

    public void setIs_Move_In(Boolean is_Move_In) {
        this.is_Move_In = is_Move_In;
    }
}
