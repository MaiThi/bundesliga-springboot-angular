package com.manage.footballapi.Mapping;

import com.manage.footballapi.API.ViewModel.*;
import com.manage.footballapi.Model.*;
import com.manage.footballapi.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MappingViewModelToEnity {
    //--------------
    //Convert From View Model to Entity
    private TeamRepository teamRepository;
    private TournamentRepository tournamentRepository;
    private LocationRepository locationRepository;
    private TeamInTournamentRepository teamInTournamentRepository;
    private PlayerRepository playerRepository;
    private PlayInTournamentRepository playInTournamentRepository;
    private PlayInMatchRepository playInMatchRepository;
    private PositionRepository positionRepository;
    private MatchRepository matchRepository;

    @Autowired
    public MappingViewModelToEnity(TeamRepository teamRepository, TournamentRepository tournamentRepository, LocationRepository locationRepository,
                                   TeamInTournamentRepository teamInTournamentRepository, PlayerRepository playerRepository,
                                   PlayInMatchRepository playInMatchRepository, PlayInTournamentRepository playInTournamentRepository,
                                   PositionRepository positionRepository, MatchRepository matchRepository){

        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;
        this.locationRepository = locationRepository;
        this.teamInTournamentRepository = teamInTournamentRepository;

        this.playerRepository = playerRepository;
        this.playInMatchRepository = playInMatchRepository;
        this.playInTournamentRepository = playInTournamentRepository;

        this.positionRepository = positionRepository;
        this.matchRepository = matchRepository;
    }

    public Location convertFromLocationViewModel (LocationViewModel locationViewModel){
        Team team = this.teamRepository.findById(locationViewModel.getTeamId()).get();
        Location location = new Location(locationViewModel.getId(), locationViewModel.getName(), locationViewModel.getAddress(),
                locationViewModel.getDescription(), locationViewModel.getImage(), locationViewModel.getNbPeopleHolded(), team);
        return location;
    }

    public GamePosition convertFromPositionViewModel (GamePositionViewModel positionViewModel){

        GamePosition gamePosition = new GamePosition(positionViewModel.getId(),positionViewModel.getName(), positionViewModel.getShortName());

        return gamePosition;
    }

    public Match convertFromMatchViewModel (MatchViewModel matchViewModel){

        Location location = this.locationRepository.findById(matchViewModel.getLocationId()).get();
        Tournament tournament = this.tournamentRepository.findById(matchViewModel.getTournamentId()).get();

        Team homeTeam = this.teamRepository.findById(matchViewModel.getHomeTeamId()).get();
        Team visitTeam = this.teamRepository.findById(matchViewModel.getVisitTeamId()).get();

        TeamInTournament homeTeamTournament = this.teamInTournamentRepository.findByTeamAndTournament(homeTeam, tournament);
        TeamInTournament visitTeamTournament = this.teamInTournamentRepository.findByTeamAndTournament(visitTeam, tournament);

        Match match = new Match(matchViewModel.getId(),matchViewModel.getHappenedDate(), homeTeamTournament, visitTeamTournament,
                location, matchViewModel.getDescription(), matchViewModel.getDayNo(), matchViewModel.getHomeGoals(), matchViewModel.getVisitGoals());
        return  match;
    }

    public MatchDetails convertFromMatchDetailsViewModel (MatchDetailViewModel matchDetailViewModel){
        PlayerInMatch playerInMatch = this.playInMatchRepository.findById(matchDetailViewModel.getPlayInMatchId()).get();

        MatchDetails matchDetails = new MatchDetails(matchDetailViewModel.getId(),playerInMatch, matchDetailViewModel.getCardReceived(), matchDetailViewModel.getAction_time(),
                matchDetailViewModel.getIs_goal_from_11m(), matchDetailViewModel.getIs_the_bad_goal(), matchDetailViewModel.getIs_Goal(),
                matchDetailViewModel.getIs_MoveOut(), matchDetailViewModel.getIs_MoveIn());

        return matchDetails;
    }


    public Player convertFromPlayerViewModel(PlayerViewModel playerViewModel){

        Player player = new Player(playerViewModel.getFirstName(),playerViewModel.getLastName(),playerViewModel.getDateOfBirth()
                , playerViewModel.getNationality(), playerViewModel.getHeight(), playerViewModel.getWeight(), playerViewModel.getImage());

        return player;
    }

    public PlayerInMatch convertFromPlayInMatchViewModel (PlayInMatchViewModel playInMatchViewModel){

        PlayerInTournament playerInTournament = this.playInTournamentRepository.findById(playInMatchViewModel.getPlayerInSSId()).get();
        GamePosition position = this.positionRepository.findById(playInMatchViewModel.getPositionId()).get();
        Match match = this.matchRepository.findById(playInMatchViewModel.getMatchId()).get();

        PlayerInMatch playerInMatch = new PlayerInMatch(playerInTournament.getId(),playerInTournament, match, position, playInMatchViewModel.getGoals(), playInMatchViewModel.getYellowCards(),
                playInMatchViewModel.getRedCards());

        return playerInMatch;
    }

    public PlayerInTournament convertFromPlayInTournamentViewModel (PlayInTournamentViewModel playInTournamentViewModel){
        Player player = this.playerRepository.findById(playInTournamentViewModel.getPlayerId()).get();
        TeamInTournament teamInTournament = this.teamInTournamentRepository.findById(playInTournamentViewModel.getTeamTourId()).get();

        PlayerInTournament playerInTournament = new PlayerInTournament(playInTournamentViewModel.getId(), player,
                teamInTournament, playInTournamentViewModel.getNbClothes(), playInTournamentViewModel.getTotalGoals(),
                playInTournamentViewModel.getTotalRedCards(), playInTournamentViewModel.getTotalYellowCards(), playInTournamentViewModel.getTotalMatchesJoined());

        return playerInTournament;
    }



    public Team convertFromTeamViewModel (TeamViewModel teamViewModel){

        Team team = new Team(teamViewModel.getId(), teamViewModel.getName(), teamViewModel.getLogoTeam(), teamViewModel.getColor());

        return team;
    }

    public Tournament convertFromTourViewModel(TournamentViewModel tournamentViewModel){

        Tournament tournament = new Tournament(tournamentViewModel.getId(), tournamentViewModel.getName(), tournamentViewModel.getStartedDate(),
                tournamentViewModel.getEndedDate(), tournamentViewModel.getNbTeams());

        return  tournament;
    }

    public TeamInTournament convertFromTeamInSeasonViewModel (TeamsInTournamentViewModel tInSeasonViewModel){

        Team team = this.teamRepository.findById(tInSeasonViewModel.getTeamId()).get();
        Tournament tournament = this.tournamentRepository.findById(tInSeasonViewModel.getTournamentId()).get();

        TeamInTournament teamInTournament = new TeamInTournament(tInSeasonViewModel.getId(), team, tournament, tInSeasonViewModel.getOutJScore(),
                tInSeasonViewModel.getHomeJScore(), tInSeasonViewModel.getTotalScore(), tInSeasonViewModel.getmW(), tInSeasonViewModel.getmD()
        , tInSeasonViewModel.getmL(), tInSeasonViewModel.getgF(), tInSeasonViewModel.getgA(), tInSeasonViewModel.getDiff(), tInSeasonViewModel.getWeekNo());

        return  teamInTournament;
    }
}
