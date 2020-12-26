package com.example.titlegen.dao;

import com.example.titlegen.model.Nouns;
import org.springframework.data.repository.CrudRepository;

public interface NounDao extends CrudRepository<Nouns, Integer> {
}
