package com.example.titlegen.dao;

import com.example.titlegen.model.BookNouns;
import org.springframework.data.repository.CrudRepository;

public interface BookNounDao extends CrudRepository<BookNouns, Integer> {
}
