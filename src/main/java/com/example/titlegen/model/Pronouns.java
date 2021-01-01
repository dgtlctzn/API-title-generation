package com.example.titlegen.model;

import javax.persistence.*;

@Entity
@Table(name = "pronouns")
public class Pronouns {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String pronoun;
    private String type;

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

    public void setType(String type) {this.type = type;}

    public String getType() { return this.type;}
}
