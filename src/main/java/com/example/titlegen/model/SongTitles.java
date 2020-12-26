package com.example.titlegen.model;

import javax.persistence.*;

@Entity
@Table(name = "song_titles_2")
public class SongTitles {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String songTitles;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.songTitles = name;
    }

    public String getName() {
        return this.songTitles;
    }
}