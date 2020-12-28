package com.example.titlegen.dao;

import com.example.titlegen.model.Pronouns;
import org.springframework.data.repository.CrudRepository;

public interface PronounDao extends CrudRepository<Pronouns, Integer> {
}
