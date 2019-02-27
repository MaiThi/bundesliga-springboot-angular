package com.manage.footballapi.Mapping;

import com.manage.footballapi.API.ViewModel.*;
import com.manage.footballapi.Model.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MappingEntityToViewModel {
    //Convert from Entity to View Model

    public LocationViewModel convertToLocalViewModel (Location location){
        LocationViewModel locationViewModel = new LocationViewModel();
        locationViewModel.setId(location.getId());
        locationViewModel.setAddress(location.getAddress());
        locationViewModel.setDescription(location.getDesciption());
        locationViewModel.setName(location.getName());
        locationViewModel.setNbPeopleHolded(location.getNumber_holding_people());
        locationViewModel.setImage(location.getImage());
        locationViewModel.setTeamId(location.getTeamLocation().getId());
        return locationViewModel;
    }

    public MatchViewModel convertToMatchViewModel (Match match){
        MatchViewModel matchViewModel = new MatchViewModel();

        matchViewModel.setId(match.getId());
        matchViewModel.setDescription(match.getDescription());
        matchViewModel.setHappenedDate(match.getHappened_date());

        matchViewModel.setHomeGoals(match.getHomeGoals());
        matchViewModel.setHomeTeamId(match.getHomeTeam().getId());
        matchViewModel.setHomeTeamName(match.getHomeTeam().getTeam().getName());
        matchViewModel.setLogoHome(match.getHomeTeam().getTeam().getLogo_image());

        matchViewModel.setVisitGoals(match.getVisitGoals());
        matchViewModel.setVisitTeamId(match.getVisitTeam().getId());
        matchViewModel.setVisitTeamName(match.getVisitTeam().getTeam().getName());
        matchViewModel.setLogoVisit(match.getVisitTeam().getTeam().getLogo_image());

        matchViewModel.setLocationId(match.getLocation().getId());
        matchViewModel.setLocationName(match.getLocation().getName());
        matchViewModel.setLocationImage(match.getLocation().getImage());

        matchViewModel.setTournamentId(match.getHomeTeam().getTournament().getId());
        matchViewModel.setDayNo(match.getDayNo());

        matchViewModel.setFinishDetails(match.getFinishMatch());
        matchViewModel.setFinishPlayer(match.getFinshAssignPlayer());

        return matchViewModel;
    }

    public MatchDetailViewModel convertToMatchDetailViewModel (MatchDetails matchDetails){
        MatchDetailViewModel matchDetailViewModel = new MatchDetailViewModel();

        matchDetailViewModel.setId(matchDetails.getId());
        matchDetailViewModel.setPlayInMatchId(matchDetails.getPlayer().getId());
        matchDetailViewModel.setPlayerId(matchDetails.getPlayer().getPlayersInTour().getPlayer().getId());

        matchDetailViewModel.setPlayerName(matchDetails.getPlayer().getPlayersInTour().getPlayer().getFirst_name() + ' ' +
                matchDetails.getPlayer().getPlayersInTour().getPlayer().getLast_name());

        matchDetailViewModel.setTeamId(matchDetails.getPlayer().getPlayersInTour().getTeamtour().getId());
        matchDetailViewModel.setTeamName(matchDetails.getPlayer().getPlayersInTour().getTeamtour().getTeam().getName());


        matchDetailViewModel.setHappenedDate(matchDetails.getPlayer().getMatch().getHappened_date());
        matchDetailViewModel.setAction_time(matchDetails.getAction_time());

        matchDetailViewModel.setCardReceived(matchDetails.getCardReceived());
        matchDetailViewModel.setIs_Goal(matchDetails.getIs_Goal());
        matchDetailViewModel.setIs_goal_from_11m(matchDetails.getIs_goal_from_11m());
        matchDetailViewModel.setIs_the_bad_goal(matchDetails.getIs_the_bad_goal());
        matchDetailViewModel.setIs_MoveIn(matchDetails.getIs_Move_In());
        matchDetailViewModel.setIs_MoveOut(matchDetails.getIs_Move_Out());
       // matchDetailViewModel.setMatchId(matchDetails.getPlayer().getMatch().getId());

        return matchDetailViewModel;
    }

    public GamePositionViewModel convertToPositionViewModel (GamePosition gamePosition){

        GamePositionViewModel gamePositionViewModel = new GamePositionViewModel();

        gamePositionViewModel.setId(gamePosition.getId());
        gamePositionViewModel.setName(gamePosition.getName());
        gamePositionViewModel.setShortName(gamePosition.getShortName());

        return gamePositionViewModel;
    }

    public PlayerViewModel convertToPlayerViewModel (Player player){
        PlayerViewModel playerViewModel = new PlayerViewModel();

        playerViewModel.setId(player.getId());
        playerViewModel.setFirstName(player.getFirst_name());
        playerViewModel.setLastName(player.getLast_name());
        playerViewModel.setDateOfBirth(player.getDate_of_birth());
        playerViewModel.setHeight(player.getHeight());
        playerViewModel.setWeight(player.getWeight());
        playerViewModel.setNationality(player.getNationality());
        playerViewModel.setImage(player.getPortrait_image());
        return playerViewModel;
    }

    public PlayInMatchViewModel convertToPlayInMatchViewModel (PlayerInMatch playerInMatch){
        PlayInMatchViewModel playInMatchViewModel = new PlayInMatchViewModel();

        playInMatchViewModel.setId(playerInMatch.getId());

        playInMatchViewModel.setMatchId(playerInMatch.getMatch().getId());
        playInMatchViewModel.setPlayerInSSId(playerInMatch.getPlayersInTour().getId());

        playInMatchViewModel.setPlayerId(playerInMatch.getPlayersInTour().getPlayer().getId());
        playInMatchViewModel.setPlayerName(playerInMatch.getPlayersInTour().getPlayer().getFirst_name());

        playInMatchViewModel.setPositionId(playerInMatch.getPosition().getId());
        playInMatchViewModel.setPositionName(playerInMatch.getPosition().getName());

        playInMatchViewModel.setRedCards(playerInMatch.getSum_red_card_in_match());
        playInMatchViewModel.setYellowCards(playerInMatch.getSum_yellow_card_in_match());
        playInMatchViewModel.setGoals(playerInMatch.getSum_goals_in_match());
        playInMatchViewModel.setNbClothes(playerInMatch.getPlayersInTour().getNbClothe());

        return  playInMatchViewModel;
    }

    public PlayInTournamentViewModel convertToPlayInTournamentViewModel (PlayerInTournament playerInTournament){
        PlayInTournamentViewModel playInTournamentViewModel = new PlayInTournamentViewModel();

        playInTournamentViewModel.setId(playerInTournament.getId());

        playInTournamentViewModel.setPlayerId(playerInTournament.getPlayer().getId());
        playInTournamentViewModel.setPlayerName(playerInTournament.getPlayer().getFirst_name() + ' ' + playerInTournament.getPlayer().getLast_name());
        playInTournamentViewModel.setNationality(playerInTournament.getPlayer().getNationality());

        playInTournamentViewModel.setTeamTourId(playerInTournament.getTeamtour().getId());
        playInTournamentViewModel.setTeamName(playerInTournament.getTeamtour().getTeam().getName());
        playInTournamentViewModel.setTournamentName(playerInTournament.getTeamtour().getTournament().getName());
        playInTournamentViewModel.setNbClothes(playerInTournament.getNbClothe());
        playInTournamentViewModel.setTotalGoals(playerInTournament.getTotalGoals());
        playInTournamentViewModel.setTotalMatchesJoined(playerInTournament.getTotalMatchesJoined());
        playInTournamentViewModel.setTotalRedCards(playerInTournament.getTotalRedCards());
        playInTournamentViewModel.setTotalYellowCards(playerInTournament.getTotalYellowCards());

        playInTournamentViewModel.setDob(playerInTournament.getPlayer().getDate_of_birth());
        return  playInTournamentViewModel;
    }

    public TeamViewModel convertToTeamViewModel (Team team){
        TeamViewModel teamViewModel = new TeamViewModel();
        teamViewModel.setId(team.getId());
        teamViewModel.setLogoTeam(team.getLogo_image());
        teamViewModel.setName(team.getName());
        teamViewModel.setColor(team.getColor());
        return  teamViewModel;
    }

    public TournamentViewModel convertToTournamentViewModel (Tournament tournament){
        TournamentViewModel tournamentViewModel = new TournamentViewModel();

        tournamentViewModel.setId(tournament.getId());
        tournamentViewModel.setStartedDate(tournament.getStarted_date());
        tournamentViewModel.setEndedDate(tournament.getEnded_date());
        tournamentViewModel.setName(tournament.getName());
        tournamentViewModel.setNbTeams(tournament.getNumber_of_teams());

        return tournamentViewModel;
    }

    public TeamsInTournamentViewModel convertToTeamsInSeasonViewModel (TeamInTournament teamInTournament){
        TeamsInTournamentViewModel teamsInTournamentViewModel = new TeamsInTournamentViewModel();
        teamsInTournamentViewModel.setId(teamInTournament.getId());
        teamsInTournamentViewModel.setHomeJScore(teamInTournament.getHomeward_journey_score());
        teamsInTournamentViewModel.setLogoTeam(teamInTournament.getTeam().getLogo_image());
        teamsInTournamentViewModel.setOutJScore(teamInTournament.getOutward_journey_score());
        teamsInTournamentViewModel.setTotalScore(teamInTournament.getTotal_score());
        teamsInTournamentViewModel.setTeamId(teamInTournament.getTeam().getId());
        teamsInTournamentViewModel.setTeamName(teamInTournament.getTeam().getName());
        teamsInTournamentViewModel.setTournamentId(teamInTournament.getTournament().getId());
        teamsInTournamentViewModel.setTournamentName(teamInTournament.getTournament().getName());
        teamsInTournamentViewModel.setColor(teamInTournament.getTeam().getColor());
        teamsInTournamentViewModel.setLocationName(teamInTournament.getTeam().getLocation().getName());
        teamsInTournamentViewModel.setmW(teamInTournament.getmW());
        teamsInTournamentViewModel.setmD(teamInTournament.getmD());
        teamsInTournamentViewModel.setmL(teamInTournament.getmL());
        teamsInTournamentViewModel.setgF(teamInTournament.getgF());
        teamsInTournamentViewModel.setgA(teamInTournament.getgA());
        teamsInTournamentViewModel.setDiff(teamInTournament.getDiff());
        teamsInTournamentViewModel.setWeekNo(teamInTournament.getWeekNo());
        return teamsInTournamentViewModel;
    }

}
