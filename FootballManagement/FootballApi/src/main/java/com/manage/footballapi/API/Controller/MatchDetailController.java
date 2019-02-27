package com.manage.footballapi.API.Controller;

import com.manage.footballapi.API.ViewModel.MatchDetailViewModel;
import com.manage.footballapi.Mapping.MappingEntityToViewModel;
import com.manage.footballapi.Mapping.MappingViewModelToEnity;
import com.manage.footballapi.Model.Match;
import com.manage.footballapi.Model.MatchDetails;
import com.manage.footballapi.Model.PlayerInMatch;
import com.manage.footballapi.Model.TeamInTournament;
import com.manage.footballapi.Repository.MatchDetailRepositoy;
import com.manage.footballapi.Repository.MatchRepository;
import com.manage.footballapi.Repository.PlayInMatchRepository;
import com.manage.footballapi.Repository.TeamInTournamentRepository;
import org.hibernate.ResourceClosedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/match-details/api")
@CrossOrigin
public class MatchDetailController {
    private MatchDetailRepositoy matchDetailRepositoy;
    private MappingViewModelToEnity mapper;
    private MappingEntityToViewModel mappingEntityToViewModel;
    private PlayInMatchRepository playInMatchRepository;
    private MatchRepository matchRepository;
    private TeamInTournamentRepository teamInTournamentRepository;

    public MatchDetailController(MatchDetailRepositoy matchDetailRepositoy, MatchRepository matchRepository,
            PlayInMatchRepository playInMatchRepository, TeamInTournamentRepository teamInTournamentRepository
            ,MappingEntityToViewModel mappingEntityToViewModel, MappingViewModelToEnity mapper){

        this.matchDetailRepositoy = matchDetailRepositoy;
        this.playInMatchRepository = playInMatchRepository;
        this.matchRepository = matchRepository;
        this.teamInTournamentRepository = teamInTournamentRepository;

        this.mapper = mapper;
        this.mappingEntityToViewModel = mappingEntityToViewModel;
    }

    public List<MatchDetails> getListDetailByMatchId (Long matchId){
        Match match = this.matchRepository.findById(matchId).get();
        List<MatchDetails> matchDetailsList = this.matchDetailRepositoy.findAll().stream()
                                    .filter(matchDetails -> matchDetails.getPlayer().getMatch().equals(match))
                                    .collect(Collectors.toList());
        return matchDetailsList;
    }

    @GetMapping("/all")
    public List<MatchDetails> getAll(){
        return this.matchDetailRepositoy.findAll();
    }

    @PostMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public MatchDetailViewModel create(@RequestBody MatchDetailViewModel matchDetailViewModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException();
        }
        MatchDetails matchDetails = this.mapper.convertFromMatchDetailsViewModel(matchDetailViewModel);
        updateMatchResult(matchDetailViewModel, matchDetails.getPlayer().getMatch());
        return this.mappingEntityToViewModel.convertToMatchDetailViewModel(this.matchDetailRepositoy.save(matchDetails));
    }

    public void updateMatchResult (MatchDetailViewModel matchDetailViewModel, Match match){
        PlayerInMatch playerInMatch = this.playInMatchRepository.findById(matchDetailViewModel.getPlayInMatchId()).get();
        if(matchDetailViewModel.getIs_Goal() == true){


            if(matchDetailViewModel.getIs_the_bad_goal() == false){
                playerInMatch.setSum_goals_in_match(playerInMatch.getSum_goals_in_match() + 1);
                if(match.getHomeTeam().equals(playerInMatch.getPlayersInTour().getTeamtour())){
                    //-- Update GF, GA for home and visit team
                    // +1 GF for home team
                    match.getHomeTeam().setgF(match.getHomeTeam().getgF() + 1);
                    this.teamInTournamentRepository.save(match.getHomeTeam());
                    // +1 GA for visit team
                    match.getVisitTeam().setgA(match.getVisitTeam().getgA() + 1);
                    this.teamInTournamentRepository.save(match.getVisitTeam());

                    if(match.getHomeGoals() == 999){
                        match.setHomeGoals(1);
                    }else {
                        match.setHomeGoals(match.getHomeGoals() + 1);
                    }
                } else {
                    // +1 GF for vist team
                    match.getVisitTeam().setgF(match.getVisitTeam().getgF() + 1);
                    this.teamInTournamentRepository.save(match.getVisitTeam());
                    // +1 GA for home team
                    match.getHomeTeam().setgA(match.getHomeTeam().getgA() + 1);
                    this.teamInTournamentRepository.save(match.getHomeTeam());

                    if(match.getVisitGoals() == 999){
                        match.setVisitGoals(1);
                    }else{
                        match.setVisitGoals(match.getVisitGoals() + 1);
                    }
                }
            }else {
                if(match.getHomeTeam().equals(playerInMatch.getPlayersInTour().getTeamtour())){
                    // +1 GF for vist team
                    match.getVisitTeam().setgF(match.getVisitTeam().getgF() + 1);
                    this.teamInTournamentRepository.save(match.getVisitTeam());
                    // +1 GA for home team
                    match.getHomeTeam().setgA(match.getHomeTeam().getgA() + 1);
                    this.teamInTournamentRepository.save(match.getHomeTeam());

                    if(match.getVisitGoals() == 999){
                        match.setVisitGoals(1);
                    }else {
                        match.setVisitGoals(match.getVisitGoals() + 1);
                    }
                }else {
                    //-- Update GF, GA for home and visit team
                    // +1 GF for home team
                    match.getHomeTeam().setgF(match.getHomeTeam().getgF() + 1);
                    this.teamInTournamentRepository.save(match.getHomeTeam());
                    // +1 GA for visit team
                    match.getVisitTeam().setgA(match.getVisitTeam().getgA() + 1);
                    this.teamInTournamentRepository.save(match.getVisitTeam());


                    if(match.getHomeGoals() == 999){
                        match.setHomeGoals(1);
                    }else {
                        match.setHomeGoals(match.getHomeGoals() + 1);
                    }
                }
            }
        }else if (matchDetailViewModel.getCardReceived().equals("YELLOW")){
                playerInMatch.setSum_yellow_card_in_match(playerInMatch.getSum_yellow_card_in_match() + 1);
        }else if(matchDetailViewModel.getCardReceived().equals("RED")){
            playerInMatch.setSum_red_card_in_match(playerInMatch.getSum_red_card_in_match() + 1);
        }
        this.matchRepository.save(match);
        this.playInMatchRepository.save(playerInMatch);
    }

    @PutMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public MatchDetailViewModel update (@Validated  @RequestBody MatchDetailViewModel matchDetailViewModel) {
        MatchDetails matchDetails = this.matchDetailRepositoy.findById(matchDetailViewModel.getId())
                .map(details -> {
                    details.setAction_time(matchDetailViewModel.getAction_time());
                    details.setCardReceived(matchDetailViewModel.getCardReceived());
                    details.setIs_Goal(matchDetailViewModel.getIs_Goal());
                    details.setIs_goal_from_11m(matchDetailViewModel.getIs_goal_from_11m());
                    details.setIs_the_bad_goal(matchDetailViewModel.getIs_the_bad_goal());
                    return this.matchDetailRepositoy.save(details); })
                .orElseThrow(()-> new ResourceClosedException("Can not find the match in the match with id "));
        return this.mappingEntityToViewModel.convertToMatchDetailViewModel(matchDetails);
    }

    @GetMapping("/getById/{id}")
    public MatchDetailViewModel getById (@PathVariable Long id) {
        return this.mappingEntityToViewModel.convertToMatchDetailViewModel(this.matchDetailRepositoy.findById(id).get());
    }

    @GetMapping("/getByMatch/{matchId}")
    public List<MatchDetailViewModel> getMatchesByMatchId (@PathVariable Long matchId){

        List<MatchDetails> matchDetailsList = getListDetailByMatchId(matchId);

        List<MatchDetailViewModel> matchDetailViewModels = matchDetailsList.stream()
                .map(matchDetails -> this.mappingEntityToViewModel.convertToMatchDetailViewModel(matchDetails))
                .collect(Collectors.toList());
        return matchDetailViewModels;
    }

    @DeleteMapping("/auth/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete (@PathVariable Long id){
        MatchDetails matchDetails = this.matchDetailRepositoy.findById(id).get();
        PlayerInMatch playerInMatch = matchDetails.getPlayer();
        Match match = playerInMatch.getMatch();

        if(matchDetails.getIs_Goal() == true){
            playerInMatch.setSum_goals_in_match(playerInMatch.getSum_goals_in_match() - 1);
            if(matchDetails.getIs_the_bad_goal() == false){
                if(match.getHomeTeam().equals(playerInMatch.getPlayersInTour().getTeamtour())){
                    match.setHomeGoals(match.getHomeGoals() - 1);
                    //-- Update GF, GA for home and visit team
                    // +1 GF for home team
                    match.getHomeTeam().setgF(match.getHomeTeam().getgF() - 1);
                    this.teamInTournamentRepository.save(match.getHomeTeam());
                    // +1 GA for visit team
                    match.getVisitTeam().setgA(match.getVisitTeam().getgA() - 1);
                    this.teamInTournamentRepository.save(match.getVisitTeam());

                }else{
                    // +1 GF for vist team
                    match.getVisitTeam().setgF(match.getVisitTeam().getgF() - 1);
                    this.teamInTournamentRepository.save(match.getVisitTeam());
                    // +1 GA for home team
                    match.getHomeTeam().setgA(match.getHomeTeam().getgA() - 1);
                    this.teamInTournamentRepository.save(match.getHomeTeam());

                    match.setVisitGoals(match.getVisitGoals() - 1);
                }
            }else {
                if(match.getHomeTeam().equals(playerInMatch.getPlayersInTour().getTeamtour())){
                    match.setVisitGoals(match.getVisitGoals() - 1);
                    // +1 GF for vist team
                    match.getVisitTeam().setgF(match.getVisitTeam().getgF() + 1);
                    this.teamInTournamentRepository.save(match.getVisitTeam());
                    // +1 GA for home team
                    match.getHomeTeam().setgA(match.getHomeTeam().getgA() + 1);
                    this.teamInTournamentRepository.save(match.getHomeTeam());

                }else{

                    match.setHomeGoals(match.getHomeGoals() - 1);
                    //-- Update GF, GA for home and visit team
                    // +1 GF for home team
                    match.getHomeTeam().setgF(match.getHomeTeam().getgF() + 1);
                    this.teamInTournamentRepository.save(match.getHomeTeam());
                    // +1 GA for visit team
                    match.getVisitTeam().setgA(match.getVisitTeam().getgA() + 1);
                    this.teamInTournamentRepository.save(match.getVisitTeam());
                }
            }
        }else if(matchDetails.getCardReceived().equals("YELLOW")){
            playerInMatch.setSum_yellow_card_in_match(playerInMatch.getSum_yellow_card_in_match() - 1);
        }else if(matchDetails.getCardReceived().equals("RED")){
            playerInMatch.setSum_red_card_in_match(playerInMatch.getSum_red_card_in_match() - 1);
        }
        this.matchRepository.save(match);
        this.playInMatchRepository.save(playerInMatch);
        this.matchDetailRepositoy.deleteById(id);
    }
}
