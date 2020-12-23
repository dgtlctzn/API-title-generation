package com.example.titlegen.dao;

import com.example.titlegen.model.SongTitle;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface SongTitleDao extends CrudRepository<SongTitle, Integer> {

}
