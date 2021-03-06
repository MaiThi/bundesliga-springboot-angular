package com.manage.footballapi.Repository;

import com.manage.footballapi.Model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    List<Tournament> findTop3ByOrderByIdDesc();
}
