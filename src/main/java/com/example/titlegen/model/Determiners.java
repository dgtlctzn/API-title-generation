package com.example.titlegen.model;

import javax.persistence.*;

@Entity
@Table(name = "determiners")
public class Determiners {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String determiner;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDeterminer(String determiner) {
        this.determiner = determiner;
    }

    public String getDeterminer() {
        return this.determiner;
    }

}
