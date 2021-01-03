package com.example.titlegen.dao;

import com.example.titlegen.model.SongNouns;
import org.springframework.data.repository.CrudRepository;

public interface SongNounDao extends CrudRepository<SongNouns, Integer> {
}
