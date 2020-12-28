package com.example.titlegen.model;

import javax.persistence.*;

@Entity
@Table(name = "pronouns")
public class Pronouns {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String pronoun;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPronoun(String pronoun) {
        this.pronoun = pronoun;
    }

    public String getPronoun() {
        return this.pronoun;
    }
}
