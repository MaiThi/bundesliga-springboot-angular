package com.manage.footballapi.API.ViewModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class GamePositionViewModel {
    private Long id;

    @NotNull
    @Min(5)
    private String name;

    @NotNull
    private String shortName;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
