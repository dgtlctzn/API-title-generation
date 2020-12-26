package com.example.titlegen.model;

import javax.persistence.*;

@Entity
@Table(name = "nouns")
public class Nouns {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String noun;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNoun(String noun) {
        this.noun = noun;
    }

    public String getSongTitle() {
        return this.noun;
    }
}