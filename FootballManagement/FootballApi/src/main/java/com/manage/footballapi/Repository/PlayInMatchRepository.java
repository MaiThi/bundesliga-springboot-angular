package com.manage.footballapi.Repository;

import com.manage.footballapi.Model.Match;
import com.manage.footballapi.Model.PlayerInMatch;
import com.manage.footballapi.Model.PlayerInTournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayInMatchRepository extends JpaRepository<PlayerInMatch, Long> {
    List<PlayerInMatch> findByMatch (Match match);
    List<PlayerInMatch> findByPlayersInTour (PlayerInTournament playerInTournament);
}
