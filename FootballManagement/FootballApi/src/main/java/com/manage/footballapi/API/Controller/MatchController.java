package com.manage.footballapi.API.Controller;

import com.manage.footballapi.API.ViewModel.MatchViewModel;
import com.manage.footballapi.Mapping.MappingEntityToViewModel;
import com.manage.footballapi.Mapping.MappingViewModelToEnity;
import com.manage.footballapi.Model.*;
import com.manage.footballapi.Repository.*;
import org.hibernate.ResourceClosedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/matches/api")
@CrossOrigin
public class MatchController {

    private MatchRepository matchRepository;
    private MappingViewModelToEnity mapper;
    private MappingEntityToViewModel mappingEntityToViewModel;
    private TournamentRepository tournamentRepository;
    private TeamInTournamentRepository teamInTournamentRepository;
    private PlayInMatchRepository playInMatchRepository;
    private PlayInTournamentRepository playInTournamentRepository;

    public MatchController(MatchRepository matchRepository, MappingViewModelToEnity mapper, MappingEntityToViewModel mappingEntityToViewModel,
                           TeamInTournamentRepository teamInTournamentRepository,
                           PlayInTournamentRepository playInTournamentRepository,
                           PlayInMatchRepository playInMatchRepository, TournamentRepository tournamentRepository){
        this.matchRepository = matchRepository;
        this.mapper = mapper;
        this.mappingEntityToViewModel = mappingEntityToViewModel;
        this.teamInTournamentRepository = teamInTournamentRepository;
        this.tournamentRepository = tournamentRepository;
        this.playInMatchRepository = playInMatchRepository;
        this.playInTournamentRepository = playInTournamentRepository;
    }

    @GetMapping("/all")
    public List<Match> getAll (){
        return this.matchRepository.findAll();
    }

    @GetMapping("/getById/{id}")
    public MatchViewModel getById (@PathVariable Long id){
        Match match = this.matchRepository.findById(id).get();
        return this.mappingEntityToViewModel.convertToMatchViewModel(match);
    }

    @PostMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public MatchViewModel create (@RequestBody MatchViewModel matchViewModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException();
        }

        Match match = this.mapper.convertFromMatchViewModel(matchViewModel);
        this.matchRepository.save(match);

        return this.mappingEntityToViewModel.convertToMatchViewModel(match);
    }

    @PutMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public MatchViewModel update(@Valid @RequestBody MatchViewModel matchViewModel){
        Match ma =  this.matchRepository.findById(matchViewModel.getId())
                .map(match -> {match.setDescription(matchViewModel.getDescription());
                match.setHappened_date(matchViewModel.getHappenedDate());
                match.setHomeGoals(matchViewModel.getHomeGoals());
                match.setVisitGoals(matchViewModel.getVisitGoals());
                match.setDayNo(matchViewModel.getDayNo());
                return this.matchRepository.save(match);})
                .orElseThrow(()-> new ResourceClosedException("Can not find the match with id " + matchViewModel.getId()));
        return  this.mappingEntityToViewModel.convertToMatchViewModel(ma);
    }

    @DeleteMapping("/auth/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete (@PathVariable Long id){
        this.matchRepository.deleteById(id);
    }


    @GetMapping("/auth/initialize/{tournamentId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<MatchViewModel> initializeMatchInSeason (@PathVariable Long tournamentId){
        Tournament tournament = this.tournamentRepository.findById(tournamentId).get();
        List<TeamInTournament>list = this.teamInTournamentRepository.findByTournament(tournament);
        List<Match> listMatch = new ArrayList<>();
        List<MatchViewModel> matchViewModelList = new ArrayList<>();
        if(list != null && list.size() != 0 ){
            int maxIndex = list.size()-1;
            for (int i = 0; i <= maxIndex; i++) {
                for (int j = maxIndex; j >= 0; j--) {
                    if(list.get(i).getTeam() != list.get(j).getTeam()){

                        Match match = new Match(null, null, list.get(i), list.get(j),
                                list.get(i).getTeam().getLocation(), " "," ", 999, 999);
                        listMatch.add(this.matchRepository.save(match));
                    }
                }
            }
            matchViewModelList = listMatch.stream()
                    .map(m -> this.mappingEntityToViewModel.convertToMatchViewModel(m))
                    .collect(Collectors.toList());
            return matchViewModelList;
        }
        return null;
    }

    public List<Match>getMatchesByTournament(Long tournamentId){
        Tournament tournament = this.tournamentRepository.findById(tournamentId).get();
        List<TeamInTournament>list = this.teamInTournamentRepository.findByTournament(tournament);
        List<Match> allMatches = new ArrayList<>();
        if(list != null && list.size() != 0 ){
            for(TeamInTournament t: list){
                List<Match> matches = this.matchRepository.findByHomeTeam(t);
                if(matches != null && matches.size() != 0){
                   allMatches.addAll(matches);
                }
            }
        }
        return allMatches;
    }

    @GetMapping("/auth/getListMatchesAlreadyAssignPlayers/{teamTourId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<MatchViewModel> getMatchAlreadyAssignPlayers (@PathVariable Long teamTourId){
        TeamInTournament teamInTournament = this.teamInTournamentRepository.findById(teamTourId).get();
        List<MatchViewModel> matchViewModelList = new ArrayList<>();

        List<Match> matchesHome = this.matchRepository.findByHomeTeamAndFinshAssignPlayer(teamInTournament, "1");
        List<Match> matchesVisit = this.matchRepository.findByVisitTeamAndFinshAssignPlayer(teamInTournament, "1");

        pushAllMatches(matchViewModelList, matchesHome, matchesVisit);
        return matchViewModelList;
    }

    @GetMapping("/getListMatchesByTeamTour/{teamTourId}")
    public List<MatchViewModel> getMatchByHomeTeamAndVisitTeam (@PathVariable Long teamTourId) {
        TeamInTournament teamInTournament = this.teamInTournamentRepository.findById(teamTourId).get();
        List<MatchViewModel> matchViewModelList = new ArrayList<>();

        List<Match> matchesHome = this.matchRepository.findByHomeTeam(teamInTournament);
        List<Match> matchesVisit = this.matchRepository.findByVisitTeam(teamInTournament);
        pushAllMatches(matchViewModelList, matchesHome, matchesVisit);
        matchViewModelList.stream()
                .sorted((matchViewModel1, matchViewModel2) -> matchViewModel1.getHappenedDate().compareTo(matchViewModel2.getHappenedDate()));
        return matchViewModelList;
    }

    public List<MatchViewModel> pushAllMatches(List<MatchViewModel> results, List<Match>matchesHome, List<Match> matchVisits){
        if(matchesHome != null && matchesHome.size() != 0){
            results.addAll(matchesHome.stream()
                    .map(match -> this.mappingEntityToViewModel.convertToMatchViewModel(match))
                    .collect(Collectors.toList()));
        }
        if(matchVisits != null && matchVisits.size() != 0){
            results.addAll(matchVisits.stream()
                    .map(match -> this.mappingEntityToViewModel.convertToMatchViewModel(match))
                    .collect(Collectors.toList()));
        }
        return results;
    }

    @GetMapping("/checkedExistingMatch/{tournamentId}")
    public int checkAlreadyCreatedMatches(@PathVariable Long tournamentId) {
        List<Match> matches = getMatchesByTournament(tournamentId);
        if(matches != null && matches.size() != 0){
            return 1;
        }else {
            return 0;
        }
    }

    @GetMapping("getMatchesByTournamentId/{tournamentId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<MatchViewModel> getMatchesByTournamentId (@PathVariable Long tournamentId){
        List<Match> allMatches = getMatchesByTournament(tournamentId);
        List<MatchViewModel> matchViewModelList = new ArrayList<>();
            if(allMatches != null && allMatches.size() != 0) {
                matchViewModelList = allMatches.stream()
                        .map(m -> this.mappingEntityToViewModel.convertToMatchViewModel(m))
                        .collect(Collectors.toList());
            }
        return matchViewModelList;
    }

    @GetMapping("/getVisitTeamsByHomeTeam/{homeTeamId}/{type}")
    public List<MatchViewModel> getListVisitTeamNotRegister (@PathVariable Long homeTeamId, @PathVariable int type){
        TeamInTournament homeTeam = this.teamInTournamentRepository.findById(homeTeamId).get();
        List<Match>matches = this.matchRepository.findByHomeTeam(homeTeam);
        List<MatchViewModel> matchViewModelList = new ArrayList<>();
        if(type == 0) {
            matchViewModelList = matches.stream()
                    .filter(match -> match.getHappened_date() == null)
                    .map(match -> this.mappingEntityToViewModel.convertToMatchViewModel(match))
                    .collect(Collectors.toList());
        }else {
            matchViewModelList = matches.stream()
                    .map(match -> this.mappingEntityToViewModel.convertToMatchViewModel(match))
                    .collect(Collectors.toList());
        }
        return matchViewModelList;
    }



    @GetMapping("/getMatchesByTournamentAndDayNo/{tournamentId}/{dayNo}")
    public List<MatchViewModel> getMatchesByTournamentAndDayNo (@PathVariable Long tournamentId, @PathVariable String dayNo){
        List<Match> allMatches = getMatchesByTournament(tournamentId);
        List<MatchViewModel> matchViewModelList = new ArrayList<>();
        matchViewModelList = allMatches.stream()
                .filter(match ->(match.getDayNo() != null) ? match.getDayNo().equals(dayNo) : false)
                .map(match -> this.mappingEntityToViewModel.convertToMatchViewModel(match))
                .collect(Collectors.toList());
        return matchViewModelList;
    }

    @GetMapping("/getMatchByHomeAndVisitTeam/{homeTeamId}/{visitTeamId}")
    public MatchViewModel getMatchesByHomeAndVisitTeam(@PathVariable Long homeTeamId, @PathVariable Long visitTeamId){
        TeamInTournament homeTeam = this.teamInTournamentRepository.findById(homeTeamId).get();
        TeamInTournament visitTeam = this.teamInTournamentRepository.findById(visitTeamId).get();
        Match currentMatch = this.matchRepository.findByHomeTeamAndVisitTeam(homeTeam, visitTeam);
        return this.mappingEntityToViewModel.convertToMatchViewModel(currentMatch);
    }

    @GetMapping("/updateScoreTeamsInMatch/{id}")
    public void updateScoreForTeamInMatch (@PathVariable Long id) {
        Match match = this.matchRepository.findById(id).get();
        match.setFinishMatch("1");
        if(match.getVisitGoals() == 999){
            match.setVisitGoals(0);
        }
        if(match.getHomeGoals() == 999){
            match.setHomeGoals(0);
        }
        Match updateMatch = this.matchRepository.save(match);
        updateMatch.getHomeTeam().setWeekNo(updateMatch.getDayNo());
        updateMatch.getVisitTeam().setWeekNo(updateMatch.getDayNo());
        if(updateMatch.getHomeGoals() == updateMatch.getVisitGoals()){
            updateMatch.getHomeTeam().setHomeward_journey_score(updateMatch.getHomeTeam().getHomeward_journey_score() + 1);
            updateMatch.getHomeTeam().setTotal_score(updateMatch.getHomeTeam().getTotal_score() + 1);
            updateMatch.getVisitTeam().setOutward_journey_score(updateMatch.getVisitTeam().getOutward_journey_score() + 1);
            updateMatch.getVisitTeam().setTotal_score(updateMatch.getVisitTeam().getTotal_score() + 1);

            //set cho gia tri cua MD cua 2 doi + 1
            updateMatch.getHomeTeam().setmD(updateMatch.getHomeTeam().getmD() + 1);
            updateMatch.getVisitTeam().setmD(updateMatch.getVisitTeam().getmD() + 1);

        }else if(updateMatch.getHomeGoals() > updateMatch.getVisitGoals()){
            updateMatch.getHomeTeam().setHomeward_journey_score(updateMatch.getHomeTeam().getHomeward_journey_score() + 3);
            updateMatch.getHomeTeam().setTotal_score(updateMatch.getHomeTeam().getTotal_score() + 3);

            //set gia tri cua MW cua Home +1
            updateMatch.getHomeTeam().setmW(updateMatch.getHomeTeam().getmW() + 1);
            //set gia tri cua ML cua visit +1
            updateMatch.getVisitTeam().setmL(updateMatch.getVisitTeam().getmL() + 1);
        }else{
            updateMatch.getVisitTeam().setOutward_journey_score(updateMatch.getVisitTeam().getOutward_journey_score() + 3);
            updateMatch.getVisitTeam().setTotal_score(updateMatch.getVisitTeam().getTotal_score() + 3);

            //set gia tri cua MW cua visit +1
            updateMatch.getVisitTeam().setmW(updateMatch.getVisitTeam().getmW() + 1);
            //set gia tri cua ML cua home +1
            updateMatch.getHomeTeam().setmL(updateMatch.getHomeTeam().getmL() + 1);
        }
        updateMatch.getHomeTeam().setDiff(updateMatch.getHomeTeam().getgF() - updateMatch.getHomeTeam().getgA());
        updateMatch.getVisitTeam().setDiff(updateMatch.getVisitTeam().getgF() - updateMatch.getVisitTeam().getgA());
        this.teamInTournamentRepository.save(updateMatch.getHomeTeam());
        this.teamInTournamentRepository.save(updateMatch.getVisitTeam());

        // Update Info for player
        List<PlayerInMatch> playerInMatches = this.playInMatchRepository.findByMatch(match);
        for(PlayerInMatch p : playerInMatches) {
            p.getPlayersInTour().setTotalMatchesJoined(p.getPlayersInTour().getTotalMatchesJoined() + 1);
            p.getPlayersInTour().setTotalGoals(p.getPlayersInTour().getTotalGoals() + p.getSum_goals_in_match());
            p.getPlayersInTour().setTotalRedCards(p.getPlayersInTour().getTotalRedCards() + p.getSum_red_card_in_match());
            p.getPlayersInTour().setTotalYellowCards(p.getPlayersInTour().getTotalYellowCards() + p.getSum_yellow_card_in_match());
            this.playInTournamentRepository.save(p.getPlayersInTour());
        }
    }
}
