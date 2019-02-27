package com.manage.footballapi.API.ViewModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TeamsInTournamentViewModel {
    private Long id;

    @NotNull
    @Min(3)
    private String teamName;

    private Long teamId;
    private String logoTeam;
    private String color;
    private String locationName;

    @NotNull
    @Min(5)
    private String tournamentName;

    private Long tournamentId;

    private Long outJScore;
    private Long homeJScore;
    private Long totalScore;
    private Integer mW;
    private Integer mD;
    private  Integer mL;
    private Integer gF;
    private Integer gA;
    private Integer diff;
    private String weekNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLogoTeam() {
        return logoTeam;
    }

    public void setLogoTeam(String logoTeam) {
        this.logoTeam = logoTeam;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Long getOutJScore() {
        return outJScore;
    }

    public void setOutJScore(Long outJScore) {
        this.outJScore = outJScore;
    }

    public Long getHomeJScore() {
        return homeJScore;
    }

    public void setHomeJScore(Long homeJScore) {
        this.homeJScore = homeJScore;
    }

    public Long getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Long totalScore) {
        this.totalScore = totalScore;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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
