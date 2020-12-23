package com.example.titlegen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.NotNull;
import org.springframework.lang.NonNull;
import java.util.Set;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Entity
@Table(name = "song_title")
public class SongTitle {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String first_half;
    private String second_half;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_half() {
        return this.first_half;
    }

    public void setFirst_half(String first_half) {
        this.first_half = first_half;
    }

    public String getSecond_half() {
        return this.second_half;
    }

    public void setSecond_half(String second_half) {
        this.second_half = second_half;
    }
}