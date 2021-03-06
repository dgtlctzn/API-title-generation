package com.example.titlegen.model;

import javax.persistence.*;

@Entity
@Table(name = "startup_verbs")
public class StartupVerbs {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String verb;
    private String type;

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

    public void setType(String type) {this.type = type;}

    public String getType() { return this.type;}
}
