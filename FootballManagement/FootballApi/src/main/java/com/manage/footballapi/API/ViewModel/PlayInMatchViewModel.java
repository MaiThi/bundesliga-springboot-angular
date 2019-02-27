package com.manage.footballapi.API.ViewModel;

public class PlayInMatchViewModel {
    private Long id;
    private Long playerInSSId;

    private Long playerId;
    private String playerName;
    private int nbClothes;

    private Long matchId;
    private Long positionId;
    private String positionName;
    private int goals;
    private int yellowCards;
    private int redCards;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerInSSId() {
        return playerInSSId;
    }

    public void setPlayerInSSId(Long playerInSSId) {
        this.playerInSSId = playerInSSId;
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

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public int getNbClothes() {
        return nbClothes;
    }

    public void setNbClothes(int nbClothes) {
        this.nbClothes = nbClothes;
    }
}
