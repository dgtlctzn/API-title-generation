package com.example.titlegen.dao;

import com.example.titlegen.model.Verbs;
import org.springframework.data.repository.CrudRepository;

public interface VerbDao extends CrudRepository<Verbs, Integer> {
}
