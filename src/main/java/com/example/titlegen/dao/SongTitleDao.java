package com.example.titlegen.dao;

import com.example.titlegen.model.Titles;
import org.springframework.data.repository.CrudRepository;

public interface SongTitleDao extends CrudRepository<Titles, Integer> {

}
