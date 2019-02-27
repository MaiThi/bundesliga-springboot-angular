package com.manage.footballapi.API.Controller;

import com.manage.footballapi.API.ViewModel.TeamsInTournamentViewModel;
import com.manage.footballapi.Mapping.MappingEntityToViewModel;
import com.manage.footballapi.Mapping.MappingViewModelToEnity;
import com.manage.footballapi.Repository.TeamInTournamentRepository;
import com.manage.footballapi.Repository.TeamRepository;
import com.manage.footballapi.Repository.TournamentRepository;
import com.manage.footballapi.Model.Team;
import com.manage.footballapi.Model.TeamInTournament;
import com.manage.footballapi.Model.Tournament;
import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/teamsinseason/api")
@CrossOrigin
public class TeamsInTournamentController {
    private TeamInTournamentRepository teamInTournamentRepository;
    private TeamRepository teamRepository;
    private TournamentRepository tournamentRepository;
    private MappingEntityToViewModel mapper;
    private MappingViewModelToEnity mappToEntiy;

    @Autowired
    public TeamsInTournamentController(TeamInTournamentRepository teamInTournamentRepository, TeamRepository teamRepository
            , TournamentRepository tournamentRepository, MappingEntityToViewModel mapper, MappingViewModelToEnity mappingViewModelToEnity){
        this.teamInTournamentRepository = teamInTournamentRepository;
        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;
        this.mapper = mapper;
        this.mappToEntiy = mappingViewModelToEnity;
    }

    @GetMapping("/all")
    public List<TeamInTournament>all(){
        return this.teamInTournamentRepository.findAll();
    }

    @GetMapping("/byId/{id}")
    public TeamsInTournamentViewModel byId(@PathVariable Long id){
        return this.mapper.convertToTeamsInSeasonViewModel(this.teamInTournamentRepository.findById(id).get());
    }

    @GetMapping("/byTeam/{id}")
    public List<TeamsInTournamentViewModel>byTeam(@PathVariable Long id){
        List<TeamInTournament>teamInTournaments = new ArrayList<>();
        Team team = this.teamRepository.findById(id).get();

        if(team != null){
          teamInTournaments = this.teamInTournamentRepository.findByTeam(team);
        }
        List<TeamsInTournamentViewModel> teamsInTournamentViewModels = teamInTournaments.stream()
                .map(t -> this.mapper.convertToTeamsInSeasonViewModel(t))
                .collect(Collectors.toList());
        return teamsInTournamentViewModels;
    }

    @GetMapping("/byTournament/{id}")
    public List<TeamsInTournamentViewModel>byTournament (@PathVariable Long id){
        List<TeamInTournament>teamInTournaments = new ArrayList<>();
        Tournament tournament = this.tournamentRepository.findById(id).get();

        if(tournament != null){
            teamInTournaments = this.teamInTournamentRepository.findByTournamentOrderByTotalDescDiffDesc(tournament);

        }
        List<TeamsInTournamentViewModel>teamsInTournamentViewModels = teamInTournaments.stream()
                .map(tour -> this.mapper.convertToTeamsInSeasonViewModel(tour))
                .collect(Collectors.toList());
        return teamsInTournamentViewModels;
    }

    @GetMapping("/byTourIdAndTeamTourId/{tourId}/{teamTourId}")
    public List<TeamsInTournamentViewModel> getListInRangeByTeamAndTour (@PathVariable Long tourId, @PathVariable Long teamTourId){
        List<TeamInTournament>teamInTournaments = new ArrayList<>();
        List<TeamInTournament> result = new ArrayList<>();
        Tournament tournament  = this.tournamentRepository.findById(tourId).get();

        TeamInTournament teamInTournament = this.teamInTournamentRepository.findById(teamTourId).get();

        if(tournament != null && teamInTournament != null) {
                teamInTournaments = this.teamInTournamentRepository.findByTournamentOrderByTotalDescDiffDesc(tournament);
                if(teamInTournaments != null && teamInTournaments.size() != 0){
                    int index = teamInTournaments.indexOf(teamInTournament);
                    //if index = 1 -> get from 1 => 4;
                    if(index == 0){
                        result = teamInTournaments.subList(index, index + 4);
                    }else if(index == 1){
                        result = teamInTournaments.subList(index - 1, index + 4);
                    }else if( index == 2){
                        //if index = 2 -> get from index - 1 -> index + 3;
                        result = teamInTournaments.subList(index - 1, index + 3);
                    }else if (index > 2) {
                        if(index == teamInTournaments.size()){
                            result = teamInTournaments.subList(index - 5, index);
                        }else if(index == teamInTournaments.size() - 1){
                            result = teamInTournaments.subList(index - 4, index + 1);
                        } else if(index == teamInTournaments.size() - 2){
                            result = teamInTournaments.subList(index - 3, index + 2);
                        }else{
                            result = teamInTournaments.subList(index - 2, index + 3);
                        }
                    }
                }
        }

        List<TeamsInTournamentViewModel>teamsInTournamentViewModels = result.stream()
                .map(tour -> this.mapper.convertToTeamsInSeasonViewModel(tour))
                .collect(Collectors.toList());
        return teamsInTournamentViewModels;

    }

    @GetMapping(value = "/byTeamAndTournament/{teamId}/{tournamentId}")
    public TeamsInTournamentViewModel getByTeamAndTournament (@PathVariable Long teamId, @PathVariable Long tournamentId) {
        Team teaam = this.teamRepository.findById(teamId).get();
        Tournament tournament = this.tournamentRepository.findById(tournamentId).get();
        TeamInTournament teamInTournament = this.teamInTournamentRepository.findByTeamAndTournament(teaam, tournament);
        if(teamInTournament != null) {
            return this.mapper.convertToTeamsInSeasonViewModel(teamInTournament);
        }else {
            return null;
        }
    }

    @GetMapping(value = "/byTeamIdTop/{teamId}")
    public List<TeamInTournament>get2TournamentsTeamJoined(@PathVariable Long teamId){
        Team team = this.teamRepository.findById(teamId).get();
        return this.teamInTournamentRepository.findTop2ByTeamOrderByIdDesc(team);
    }
    @PostMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public TeamsInTournamentViewModel save (@RequestBody TeamsInTournamentViewModel teamsInTournamentViewModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException();
        }
        TeamInTournament teamInTournament = this.mappToEntiy.convertFromTeamInSeasonViewModel(teamsInTournamentViewModel);
        this.teamInTournamentRepository.save(teamInTournament);
        return  this.mapper.convertToTeamsInSeasonViewModel(teamInTournament);
    }

    @PutMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public TeamsInTournamentViewModel update (@Valid @RequestBody TeamsInTournamentViewModel teamsInTournamentViewModel){
        TeamInTournament tt= this.teamInTournamentRepository.findById(teamsInTournamentViewModel.getId())
                .map(teamInTournament -> {teamInTournament.setHomeward_journey_score(teamsInTournamentViewModel.getHomeJScore());
                teamInTournament.setOutward_journey_score(teamsInTournamentViewModel.getOutJScore());
                teamInTournament.setTotal_score(teamsInTournamentViewModel.getTotalScore());
                return this.teamInTournamentRepository.save(teamInTournament);})
                .orElseThrow(()-> new ResourceClosedException("Can not find the Team Season with id" + teamsInTournamentViewModel.getId()));
        return this.mapper.convertToTeamsInSeasonViewModel(tt);
    }

    @DeleteMapping("/auth/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete(@PathVariable Long id){
         this.teamInTournamentRepository.deleteById(id);
    }
}
