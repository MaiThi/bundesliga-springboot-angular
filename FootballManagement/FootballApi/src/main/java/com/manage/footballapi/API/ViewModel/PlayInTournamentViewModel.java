package com.manage.footballapi.API.ViewModel;

import java.util.Date;

public class PlayInTournamentViewModel {
    private Long id;

    private Long teamTourId;
    private String teamName;
    private String tournamentName;

    private Long playerId;
    private String playerName;
    private int nbClothes;
    private Date dob;
    private String nationality;

    private Long totalRedCards;
    private Long totalYellowCards;
    private Long totalGoals;
    private Long totalMatchesJoined;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeamTourId() {
        return teamTourId;
    }

    public void setTeamTourId(Long teamTourId) {
        this.teamTourId = teamTourId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
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

    public int getNbClothes() {
        return nbClothes;
    }

    public void setNbClothes(int nbClothes) {
        this.nbClothes = nbClothes;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
