package com.example.titlegen.model;

import javax.persistence.*;

@Entity
@Table(name = "book_nouns")
public class BookNouns {

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

    public String getNoun() {
        return this.noun;
    }

}
