package com.manage.footballapi.Repository;

import com.manage.footballapi.Model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByOrderByIdDesc();
    List<Team> findByNameContainingIgnoreCase(String name);
}
