package com.manage.footballapi.API.ViewModel;

import com.manage.footballapi.Model.Team;

import java.util.Date;

public class MatchDetailViewModel {
    private Long id;
    private Date happenedDate;

    private Long playInMatchId;

    private Long playerId;
    private String playerName;

    private Long teamId;
    private String teamName;

  //  private Long matchId;

    private String cardReceived;
    private Integer action_time;
    private Boolean is_goal_from_11m;
    private Boolean is_the_bad_goal;
    private Boolean is_Goal;
    private Boolean is_MoveIn;
    private Boolean is_MoveOut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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

    public Long getPlayInMatchId() {
        return playInMatchId;
    }

    public void setPlayInMatchId(Long playInMatchId) {
        this.playInMatchId = playInMatchId;
    }

    public Boolean getIs_MoveIn() {
        return is_MoveIn;
    }

    public void setIs_MoveIn(Boolean is_MoveIn) {
        this.is_MoveIn = is_MoveIn;
    }

    public Boolean getIs_MoveOut() {
        return is_MoveOut;
    }

    public void setIs_MoveOut(Boolean is_MoveOut) {
        this.is_MoveOut = is_MoveOut;
    }

    //    public Long getMatchId() {
//        return matchId;
//    }
//
//    public void setMatchId(Long matchId) {
//        this.matchId = matchId;
//    }
}
