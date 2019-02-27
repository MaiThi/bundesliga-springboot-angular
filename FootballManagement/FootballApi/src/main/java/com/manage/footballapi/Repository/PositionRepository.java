package com.manage.footballapi.Repository;

import com.manage.footballapi.Model.GamePosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<GamePosition, Long> {
    GamePosition findByShortName (String name);
}
