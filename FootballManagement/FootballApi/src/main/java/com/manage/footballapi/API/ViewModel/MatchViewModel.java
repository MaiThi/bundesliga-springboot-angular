package com.manage.footballapi.API.ViewModel;

import java.util.Date;

public class MatchViewModel {
    private Long id;
    private Date happenedDate;

    private Long homeTeamId;
    private String homeTeamName;
    private String logoHome;

    private Long visitTeamId;
    private String visitTeamName;
    private String logoVisit;

    private Long tournamentId;

    private Integer homeGoals;
    private Integer visitGoals;

    private Long locationId;
    private String locationName;
    private String locationImage;

    private String description;
    private String dayNo;
    private String finishPlayer;
    private String finishDetails;

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

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Long homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public Long getVisitTeamId() {
        return visitTeamId;
    }

    public void setVisitTeamId(Long visitTeamId) {
        this.visitTeamId = visitTeamId;
    }

    public String getVisitTeamName() {
        return visitTeamName;
    }

    public void setVisitTeamName(String visitTeamName) {
        this.visitTeamName = visitTeamName;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDayNo() {
        return dayNo;
    }

    public void setDayNo(String dayNo) {
        this.dayNo = dayNo;
    }

    public String getFinishPlayer() {
        return finishPlayer;
    }

    public void setFinishPlayer(String finishPlayer) {
        this.finishPlayer = finishPlayer;
    }

    public String getFinishDetails() {
        return finishDetails;
    }

    public void setFinishDetails(String finishDetails) {
        this.finishDetails = finishDetails;
    }

    public String getLogoHome() {
        return logoHome;
    }

    public void setLogoHome(String logoHome) {
        this.logoHome = logoHome;
    }

    public String getLogoVisit() {
        return logoVisit;
    }

    public void setLogoVisit(String logoVisit) {
        this.logoVisit = logoVisit;
    }

    public String getLocationImage() {
        return locationImage;
    }

    public void setLocationImage(String locationImage) {
        this.locationImage = locationImage;
    }
}
