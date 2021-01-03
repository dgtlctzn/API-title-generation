package com.example.titlegen.model;

import javax.persistence.*;

@Entity
@Table(name = "nouns")
public class SongNouns {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String noun;
    private String type;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNoun(String noun) {
        this.noun = noun;
    }

    public String getNoun() {
        return this.noun;
    }

    public void setType(String type) {this.type = type;}

    public String getType() { return this.type;}
}