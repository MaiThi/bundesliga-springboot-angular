package com.manage.footballapi.API.ViewModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TournamentViewModel {
    private Long id;

    @NotNull
    @Min(5)
    private String name;
    private Date startedDate;
    private Date endedDate;
    private Long nbTeams;
    //private int nbTeams;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Date getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(Date endedDate) {
        this.endedDate = endedDate;
    }

    public Long getNbTeams() {
        return nbTeams;
    }

    public void setNbTeams(Long nbTeams) {
        this.nbTeams = nbTeams;
    }

}
