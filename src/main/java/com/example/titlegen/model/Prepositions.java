package com.example.titlegen.model;

import javax.persistence.*;

@Entity
@Table(name = "prepositions")
public class Prepositions {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String preposition;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    public String getPreposition() {
        return this.preposition;
    }
}
