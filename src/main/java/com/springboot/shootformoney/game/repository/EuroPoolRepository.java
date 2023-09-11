package com.springboot.shootformoney.game.repository;

import com.springboot.shootformoney.game.entity.EuroPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuroPoolRepository extends JpaRepository<EuroPool, Long> {
}
