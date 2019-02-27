package com.manage.footballapi.Repository;

import com.manage.footballapi.Model.Match;
import com.manage.footballapi.Model.TeamInTournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByDayNo(String dayNo);
    List<Match> findByHomeTeam (TeamInTournament teamInTournament);
    List<Match> findByVisitTeam (TeamInTournament teamInTournament);
    Match findByHomeTeamAndVisitTeam (TeamInTournament homeTeam, TeamInTournament visitTeam);
    List<Match> findByHomeTeamAndFinshAssignPlayer(TeamInTournament homeTeam, String signal);
    List<Match> findByVisitTeamAndFinshAssignPlayer (TeamInTournament visitTeam, String signal);
}
