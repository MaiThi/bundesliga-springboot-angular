package com.manage.footballapi.API.ViewModel;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TeamViewModel {
    private Long id;
    @NotNull
    @Min(3)
    private String name;
    private String logoTeam;
    private String color;

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

    public String getLogoTeam() {
        return logoTeam;
    }

    public void setLogoTeam(String logoTeam) {
        this.logoTeam = logoTeam;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
