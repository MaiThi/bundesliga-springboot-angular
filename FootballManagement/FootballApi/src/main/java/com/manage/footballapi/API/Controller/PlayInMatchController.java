package com.manage.footballapi.API.Controller;

import com.manage.footballapi.API.ViewModel.PlayInMatchViewModel;
import com.manage.footballapi.Mapping.MappingEntityToViewModel;
import com.manage.footballapi.Mapping.MappingViewModelToEnity;
import com.manage.footballapi.Model.*;
import com.manage.footballapi.Repository.*;
import org.hibernate.ResourceClosedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/play-in-matches/api")
@CrossOrigin
public class PlayInMatchController {

    private PlayInMatchRepository playInMatchRepository;
    private MappingViewModelToEnity mapper;
    private MappingEntityToViewModel mappingEntityToViewModel;
    private MatchRepository matchRepository;
    private TeamInTournamentRepository teamInTournamentRepository;
    private PlayInTournamentRepository playInTournamentRepository;
    private PositionRepository positionRepository;

    public PlayInMatchController(PlayInMatchRepository playInMatchRepository, MatchRepository matchRepository,
                                 TeamInTournamentRepository teamInTournamentRepository, PlayInTournamentRepository playInTournamentRepository,
                                PositionRepository positionRepository, MappingViewModelToEnity mapper, MappingEntityToViewModel mappingEntityToViewModel){
        this.mapper = mapper;
        this.mappingEntityToViewModel = mappingEntityToViewModel;
        this.playInMatchRepository = playInMatchRepository;
        this.matchRepository = matchRepository;
        this.teamInTournamentRepository = teamInTournamentRepository;
        this.playInTournamentRepository = playInTournamentRepository;
        this.positionRepository = positionRepository;
    }

    public List<PlayerInMatch> getByMatchId(Long matchId){
        Match match = this.matchRepository.findById(matchId).get();
        List<PlayerInMatch> playerInMatches = this.playInMatchRepository.findByMatch(match);
        return playerInMatches;
    }

    @GetMapping("/all")
    public List<PlayerInMatch> getAll (){
        return this.playInMatchRepository.findAll();
    }

    @PostMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PlayInMatchViewModel create (@RequestBody PlayInMatchViewModel playInMatchViewModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException();
        }
        PlayerInMatch playerInMatch = this.mapper.convertFromPlayInMatchViewModel(playInMatchViewModel);
        return this.mappingEntityToViewModel.convertToPlayInMatchViewModel(this.playInMatchRepository.save(playerInMatch));
    }

    @PutMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PlayInMatchViewModel update (@Validated  @RequestBody PlayInMatchViewModel playInMatchViewModel){
        GamePosition position = this.positionRepository.findByShortName(playInMatchViewModel.getPositionName().toUpperCase());
        PlayerInMatch playerInMatch = this.playInMatchRepository.findById(playInMatchViewModel.getId())
                .map(player -> {
                    player.setPosition(position);
                    player.setSum_goals_in_match(playInMatchViewModel.getGoals());
                    player.setSum_red_card_in_match(playInMatchViewModel.getRedCards());
                    player.setSum_yellow_card_in_match(playInMatchViewModel.getYellowCards());
                return this.playInMatchRepository.save(player);})
                .orElseThrow(()-> new ResourceClosedException("Can not find the players in the match with id " + playInMatchViewModel.getId()));
        return this.mappingEntityToViewModel.convertToPlayInMatchViewModel(playerInMatch);
    }

    @GetMapping("/getByMatchAndTeam/{matchId}/{teamSSId}")
    public List<PlayInMatchViewModel> getByMatchAndTeamId (@PathVariable Long matchId,@PathVariable Long teamSSId){

        TeamInTournament teamInTournament = this.teamInTournamentRepository.findById(teamSSId).get();
        List<PlayerInMatch> playerInMatches = getByMatchId(matchId);
        List<PlayInMatchViewModel> playInMatchViewModels = new ArrayList<>();
        if(playerInMatches != null && playerInMatches.size() != 0){
            playInMatchViewModels = playerInMatches.stream()
                    .filter(playerInMatch -> playerInMatch.getPlayersInTour().getTeamtour().equals(teamInTournament))
                    .map(m -> this.mappingEntityToViewModel.convertToPlayInMatchViewModel(m))
                    .collect(Collectors.toList());
        }
        return playInMatchViewModels;
    }

    public void checkAndUpdateFlagForMatch (Match match){
        List<PlayerInMatch> playerInMatches = getByMatchId(match.getId());
        boolean homeCheck = playerInMatches.stream()
                .anyMatch(playerInMatch -> playerInMatch.getPlayersInTour().getTeamtour().equals(match.getHomeTeam()));
        boolean visitCheck = playerInMatches.stream()
                .anyMatch(playerInMatch -> playerInMatch.getPlayersInTour().getTeamtour().equals(match.getVisitTeam()));
        if(homeCheck == true && visitCheck == true){
            match.setFinshAssignPlayer("1");
            this.matchRepository.save(match);
        }
    }

    @GetMapping("/auth/addPlayersIntoMatchByTeamId/{matchId}/{teamSSId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<PlayInMatchViewModel> createPlayerInMatchByTeamId (@PathVariable Long matchId,@PathVariable Long teamSSId){
        Match match = this.matchRepository.findById(matchId).get();
        TeamInTournament teamInTournament = this.teamInTournamentRepository.findById(teamSSId).get();
        GamePosition position = this.positionRepository.findById(Long.parseLong("12")).get();

        List<PlayerInTournament> playerInTournamentList = this.playInTournamentRepository.findPlayerInTournamentByTeamtour(teamInTournament);
        List<PlayInMatchViewModel> playInMatchViewModels = new ArrayList<>();

        if(playerInTournamentList != null && playerInTournamentList.size() != 0){
            playInMatchViewModels = playerInTournamentList.stream()
                    .map(player -> this.mappingEntityToViewModel.convertToPlayInMatchViewModel(
                            this.playInMatchRepository.save(new PlayerInMatch
                                    (null, player,match, position, 0,0,0 ))))
                    .collect(Collectors.toList());
        }
        checkAndUpdateFlagForMatch(match);
        return playInMatchViewModels;
    }

    @GetMapping("/auth/addPlayersBasedOnPreviousMatch/{matchId}/{currentMatchId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<PlayInMatchViewModel> createPlayersByMatchBefore (@PathVariable Long matchId, @PathVariable Long currentMatchId){
        Match match = this.matchRepository.findById(matchId).get();
        Match currentMatch = this.matchRepository.findById(currentMatchId).get();
        List<PlayerInMatch> playerInMatches = this.playInMatchRepository.findByMatch(match);
        List<PlayInMatchViewModel> playInMatchViewModels = new ArrayList<>();
        if(playerInMatches != null && playerInMatches.size() != 0){
            playInMatchViewModels = playerInMatches.stream()
                    .map(player -> this.mappingEntityToViewModel.convertToPlayInMatchViewModel(
                            this.playInMatchRepository.save(new PlayerInMatch
                                    (null, player.getPlayersInTour(), currentMatch, player.getPosition(), 0,0,0 ))))
                    .collect(Collectors.toList());
        }
        checkAndUpdateFlagForMatch(currentMatch);
        return playInMatchViewModels;
    }

    @DeleteMapping("/auth/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete(@PathVariable Long id){
        this.playInMatchRepository.deleteById(id);
    }
}
