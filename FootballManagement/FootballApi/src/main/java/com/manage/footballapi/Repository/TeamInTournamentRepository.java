package com.manage.footballapi.Repository;

import com.manage.footballapi.Model.Team;
import com.manage.footballapi.Model.TeamInTournament;
import com.manage.footballapi.Model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamInTournamentRepository extends JpaRepository<TeamInTournament, Long> {
    List<TeamInTournament> findByTeam(Team team);
    List<TeamInTournament>  findByTournamentOrderByTotalDescDiffDesc(Tournament tournament);
    List<TeamInTournament> findByTournament(Tournament tournament);
    TeamInTournament findByTeamAndTournament(Team team, Tournament tournament);
    List<TeamInTournament> findTop2ByTeamOrderByIdDesc(Team team);
}
