package com.manage.footballapi.API.Controller;


import com.manage.footballapi.API.ViewModel.TournamentViewModel;
import com.manage.footballapi.Mapping.MappingEntityToViewModel;
import com.manage.footballapi.Mapping.MappingViewModelToEnity;
import com.manage.footballapi.Repository.TournamentRepository;
import com.manage.footballapi.Model.Tournament;
import org.hibernate.ResourceClosedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tournaments/api")
@CrossOrigin
public class TournamentController {
    private TournamentRepository tournamentRepository;
    private MappingViewModelToEnity mapperToEntity;
    private MappingEntityToViewModel mapperToViewModel;
    public TournamentController(TournamentRepository tournamentRepository, MappingViewModelToEnity mapperToEntity, MappingEntityToViewModel mapperToViewModel){
        this.tournamentRepository = tournamentRepository;
        this.mapperToEntity = mapperToEntity;
        this.mapperToViewModel = mapperToViewModel;
    }

    @GetMapping("/all")
    public List<TournamentViewModel> all(){
        List<Tournament> tournaments = this.tournamentRepository.findAll();
        List<TournamentViewModel> tournamentViewModels = tournaments.stream()
                .map(tournament -> this.mapperToViewModel.convertToTournamentViewModel(tournament))
                .collect(Collectors.toList());
        return tournamentViewModels;
    }

    @GetMapping("/byId/{id}")
    public TournamentViewModel getById(@PathVariable Long id){
        Tournament tournament = this.tournamentRepository.findById(id).get();
        return this.mapperToViewModel.convertToTournamentViewModel(tournament);
    }


    @GetMapping("/get-top")
    public List<TournamentViewModel> getTop(){
        List<Tournament> tournaments = this.tournamentRepository.findTop3ByOrderByIdDesc();
        List<TournamentViewModel> tournamentViewModels = tournaments.stream()
                .map(tournament -> this.mapperToViewModel.convertToTournamentViewModel(tournament))
                .collect(Collectors.toList());
        return tournamentViewModels;
    }

    @PostMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public TournamentViewModel save(@RequestBody TournamentViewModel tournamentViewModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException();
        }
        tournamentViewModel.setEndedDate(new Date());
        tournamentViewModel.setStartedDate(new Date());
        Tournament tournament = this.mapperToEntity.convertFromTourViewModel(tournamentViewModel);


        return this.mapperToViewModel.convertToTournamentViewModel(this.tournamentRepository.save(tournament));
    }

    @PutMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public TournamentViewModel update(@Valid @RequestBody TournamentViewModel tournamentViewModel){
        Tournament to = this.tournamentRepository.findById(tournamentViewModel.getId())
                .map(tournament -> {tournament.setEnded_date(tournamentViewModel.getEndedDate());
                tournament.setName(tournamentViewModel.getName());
                tournament.setNumber_of_teams(tournamentViewModel.getNbTeams());
                tournament.setStarted_date(tournamentViewModel.getStartedDate());
                return this.tournamentRepository.save(tournament);})
                .orElseThrow(()-> new ResourceClosedException("Can not find tournament with id" + tournamentViewModel.getId()));
        return this.mapperToViewModel.convertToTournamentViewModel(to);
    }

    @DeleteMapping("/auth/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete (@PathVariable Long id){
        this.tournamentRepository.deleteById(id);
    }
}
