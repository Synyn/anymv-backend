package com.anymv.dao;

import com.anymv.entity.Synonym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SynonymDao extends JpaRepository<Synonym, Long> {
}
