package com.example.titlegen.model;

import javax.persistence.*;

@Entity
@Table(name = "verbs")
public class Verbs {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String verb;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getVerb() {
        return this.verb;
    }
}
