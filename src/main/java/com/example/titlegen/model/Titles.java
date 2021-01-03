package com.example.titlegen.model;

import javax.persistence.*;

@Entity
@Table(name = "song_titles_2")
public class Titles {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String titles;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.titles = name;
    }

    public String getName() {
        return this.titles;
    }
}