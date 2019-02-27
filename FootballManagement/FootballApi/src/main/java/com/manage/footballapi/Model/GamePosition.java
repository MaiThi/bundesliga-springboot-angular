package com.manage.footballapi.Model;

import javax.persistence.*;

@Entity
@Table(name = "game_position")
public class GamePosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "short_name")
    private String shortName;

    public GamePosition(){}

    public GamePosition(Long id, String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
        if(this.id != -1){
            this.id = id;
        }
    }

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
