package com.example.titlegen.dao;

import com.example.titlegen.model.SongTitles;
import org.springframework.data.repository.CrudRepository;

public interface SongTitleDao extends CrudRepository<SongTitles, Integer> {

}
