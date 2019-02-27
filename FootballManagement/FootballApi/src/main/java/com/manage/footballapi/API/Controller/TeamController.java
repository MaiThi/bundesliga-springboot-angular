package com.manage.footballapi.API.Controller;

import com.manage.footballapi.Mapping.MappingEntityToViewModel;
import com.manage.footballapi.Mapping.MappingViewModelToEnity;
import com.manage.footballapi.API.ViewModel.TeamViewModel;
import com.manage.footballapi.Repository.TeamRepository;
import com.manage.footballapi.Model.Team;
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
@RequestMapping("/teams/api")
@CrossOrigin
public class TeamController {
    private TeamRepository teamRepository;
    private MappingViewModelToEnity mapperToEntity;
    private MappingEntityToViewModel mapperToViewModel;

    @Autowired
    public TeamController(TeamRepository teamRepository, MappingViewModelToEnity mapperToEntity, MappingEntityToViewModel mapperToViewModel){
        this.teamRepository = teamRepository;
        this.mapperToViewModel = mapperToViewModel;
        this.mapperToEntity = mapperToEntity;
    }

    @GetMapping("/auth/all")

    public List<TeamViewModel> all(){

        List<Team> teams = this.teamRepository.findAll();
        List<TeamViewModel> teamViewModels =teams.stream()
                            .map(team -> this.mapperToViewModel.convertToTeamViewModel(team))
                            .collect(Collectors.toList());

        return teamViewModels;
    }

    @GetMapping("/byContainName/{name}")
    public List<TeamViewModel> getTeamsByContainName(@PathVariable String name) {
        List<Team> teams = this.teamRepository.findByNameContainingIgnoreCase(name);
        List<TeamViewModel> teamViewModels = new ArrayList<TeamViewModel>();
        if(teams != null && teams.size() != 0) {
             teamViewModels = teams.stream()
                    .map(team -> this.mapperToViewModel.convertToTeamViewModel(team))
                    .collect(Collectors.toList());
        }
        return teamViewModels;
    }

    @PostMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public TeamViewModel save(@RequestBody TeamViewModel teamViewModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException();
        }

        Team team = this.mapperToEntity.convertFromTeamViewModel(teamViewModel);

        return this.mapperToViewModel.convertToTeamViewModel(this.teamRepository.save(team));
    }

    @PutMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public TeamViewModel update(@Valid @RequestBody TeamViewModel teamViewModel) {
        Team te = this.teamRepository.findById(teamViewModel.getId())
                .map(team -> {team.setLogo_image(teamViewModel.getLogoTeam());
                team.setName(teamViewModel.getName());
                return this.teamRepository.save(team);})
                .orElseThrow(()-> new ResourceClosedException("Can not find Team with id" + teamViewModel.getId()));
        return this.mapperToViewModel.convertToTeamViewModel(te);
    }

    @DeleteMapping("/auth/{id}")
    public void delete(@PathVariable Long id){
        this.teamRepository.deleteById(id);
    }
}
