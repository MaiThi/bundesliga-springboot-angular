package com.manage.footballapi.Repository;

import com.manage.footballapi.Model.Player;
import com.manage.footballapi.Model.PlayerInTournament;
import com.manage.footballapi.Model.TeamInTournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayInTournamentRepository extends JpaRepository<PlayerInTournament, Long> {
    List<PlayerInTournament> findPlayerInTournamentByTeamtour (TeamInTournament teamInTournament);
    PlayerInTournament findByTeamtourAndPlayer (TeamInTournament teamInTournament, Player player);
}
