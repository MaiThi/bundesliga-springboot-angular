package com.manage.footballapi.API.Controller;

import com.manage.footballapi.API.ViewModel.PlayInTournamentViewModel;
import com.manage.footballapi.API.ViewModel.PlayerViewModel;
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
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/play-in-tournaments/api")
@CrossOrigin
public class PlayInTournamentController {

    private PlayInTournamentRepository playInTournamentRepository;
    private PlayerRepository playerRepository;
    private TeamInTournamentRepository teamInTournamentRepository;
    private MappingViewModelToEnity mapper;
    private TeamRepository teamRepository;
    private TournamentRepository tournamentRepository;
    private MappingEntityToViewModel mappingEntityToViewModel;
    private PlayInMatchRepository playInMatchRepository;

    public PlayInTournamentController(PlayerRepository playerRepository, TeamRepository teamRepository
            , TournamentRepository tournamentRepository,TeamInTournamentRepository teamInTournamentRepository,
                                      PlayInTournamentRepository playInTournamentRepository,
                                      PlayInMatchRepository playInMatchRepository,
                                      MappingViewModelToEnity mapper, MappingEntityToViewModel mappingEntityToViewModel){
        this.playInTournamentRepository = playInTournamentRepository;
        this.mapper = mapper;
        this.mappingEntityToViewModel = mappingEntityToViewModel;
        this.teamInTournamentRepository = teamInTournamentRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;
        this.playInMatchRepository = playInMatchRepository;
    }

    @GetMapping("/all")
    public List<PlayerInTournament> getAll(){
        return this.playInTournamentRepository.findAll();
    }

    @PostMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PlayInTournamentViewModel create(@RequestBody PlayInTournamentViewModel playInTournamentViewModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException();
        }

        PlayerInTournament playerInTournament = this.mapper.convertFromPlayInTournamentViewModel(playInTournamentViewModel);
        return this.mappingEntityToViewModel.convertToPlayInTournamentViewModel(this.playInTournamentRepository.save(playerInTournament));
    }

    @PutMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PlayInTournamentViewModel update(@Valid @RequestBody PlayInTournamentViewModel playInTournamentViewModel){
        PlayerInTournament playInTournament =  this.playInTournamentRepository.findById(playInTournamentViewModel.getId())
                .map(playerInTournament -> {playerInTournament.setNbClothe(playInTournamentViewModel.getNbClothes());
                return this.playInTournamentRepository.save(playerInTournament);})
                .orElseThrow(()-> new ResourceClosedException("Can not find the Player In Tournament with id"));
        return this.mappingEntityToViewModel.convertToPlayInTournamentViewModel(playInTournament);
    }

    @GetMapping("/byTeamTourId/{teamTourId}")
    public List<PlayInTournamentViewModel> getByTeamtour (@PathVariable Long teamTourId){
        TeamInTournament teamInTournament = this.teamInTournamentRepository.findById(teamTourId).get();
        if(teamInTournament != null){
            List<PlayerInTournament>list = this.playInTournamentRepository.findPlayerInTournamentByTeamtour(teamInTournament);
            if(list != null && list.size() != 0){
                List<PlayInTournamentViewModel> viewModelList = this.convertListEntityToViewModel(list);
                return viewModelList;
            }
        }
        return null;
    }
   // @GetMapping("/getPlayerIdsByTeamTour/{teamTourId}")
    public Long[] getIdByTeamTour ( Long teamTourId){
        TeamInTournament teamInTournament = this.teamInTournamentRepository.findById(teamTourId).get();
        if(teamInTournament != null){
            List<PlayerInTournament>tess = this.playInTournamentRepository.findPlayerInTournamentByTeamtour(teamInTournament);
            List<Long> results = new ArrayList<Long>();
            for(PlayerInTournament p : tess){
                results.add(p.getPlayer().getId());
            }
            Long[] resultArr = new Long[results.size()];
            resultArr = results.toArray(resultArr);
            return resultArr;
        }
        return null;
    }

    @GetMapping("/getPlayerIdsByTeamTourForAdding/{teamTourId}")
    public List<PlayerViewModel> getListPlayersWithoutExisitng (@PathVariable Long teamTourId) {
        Long[] playerInTourId = getIdByTeamTour(teamTourId);
        List<Player> players = this.playerRepository.findByIdNotIn(playerInTourId);
        if(players != null && players.size() != 0) {
            return players.stream()
                    .map(t -> this.mappingEntityToViewModel.convertToPlayerViewModel(t))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @GetMapping(value = "/auth/addMultiple/{teamTourIdOld}/{teamTourCurrent}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<PlayInTournamentViewModel> addPlayersFromtThePreviousSeason(@PathVariable Long teamTourIdOld, @PathVariable Long teamTourCurrent){
        TeamInTournament teamInTournamentOld = this.teamInTournamentRepository.findById(teamTourIdOld).get();
        TeamInTournament ttCurrent = this.teamInTournamentRepository.findById(teamTourCurrent).get();
        if(teamInTournamentOld != null){
            List<PlayerInTournament>tess = this.playInTournamentRepository.findPlayerInTournamentByTeamtour(teamInTournamentOld);
            List<PlayerInTournament>ttNew = tess.stream()
                                                .map(t -> this.playInTournamentRepository.save(new
                                                        PlayerInTournament(null, t.getPlayer(),ttCurrent, t.getNbClothe(), new Long(0),new Long(0),new Long(0),new Long(0))))
                                                 .collect(Collectors.toList());
            if(ttNew != null && ttNew.size() != 0){
                List<PlayInTournamentViewModel> results = this.convertListEntityToViewModel(ttNew);
                return results;
            }
        }
        return  null;
    }

    public List<PlayInTournamentViewModel> convertListEntityToViewModel (List<PlayerInTournament> playerInTournaments){
        return playerInTournaments.stream()
                .map(t -> this.mappingEntityToViewModel.convertToPlayInTournamentViewModel(t))
                .collect(Collectors.toList());
    }

//    @GetMapping("/addPlayerInTourApi/{teamId}/{tourId}/{idFrom}/{idTo}")
//    public void addPlayerInTournamentApi(@PathVariable Long teamId, @PathVariable Long tourId, @PathVariable Long idFrom, @PathVariable Long idTo) {
//        List<Player> players = this.playerRepository.findByIdGreaterThanAndIdLessThan(idFrom, idTo);
//        Team teaam = this.teamRepository.findById(teamId).get();
//        Tournament tournament = this.tournamentRepository.findById(tourId).get();
//        TeamInTournament teamInTournament = this.teamInTournamentRepository.findByTeamAndTournament(teaam, tournament);
//
//        for(Player p : players){
//            PlayerInTournament playerInTournament = new PlayerInTournament(null, p, teamInTournament, 9);
//            this.playInTournamentRepository.save(playerInTournament);
//        }
//    }


    @GetMapping("/addRandomNbClothes/{teamTourId}")
    public void editNbClothes (@PathVariable Long teamTourId){
        TeamInTournament teamInTournament = this.teamInTournamentRepository.findById(teamTourId).get();
        if(teamInTournament != null){
            Random rand = new Random();

            List<PlayerInTournament>list = this.playInTournamentRepository.findPlayerInTournamentByTeamtour(teamInTournament);
            for (PlayerInTournament playerInTournament : list){
                playerInTournament.setNbClothe(rand.nextInt(100));
                this.playInTournamentRepository.save(playerInTournament);
            }
        }
    }

    @GetMapping("/updateApi")
    public void updateApi(){
        List<PlayerInTournament> playerInTournamentList = this.playInTournamentRepository.findAll();
        for(PlayerInTournament p : playerInTournamentList){
            List<PlayerInMatch> playerInMatches = this.playInMatchRepository.findByPlayersInTour(p);
            p.setTotalRedCards(new Long(0));
            p.setTotalYellowCards(new Long(0));
            p.setTotalGoals(new Long(0));
            if(playerInMatches == null && playerInMatches.size() == 0){
                p.setTotalMatchesJoined(new Long(0));
            }else{
                p.setTotalMatchesJoined(new Long(playerInMatches.size()));
                for(PlayerInMatch pM : playerInMatches){
                    p.setTotalGoals(p.getTotalGoals() + pM.getSum_goals_in_match());
                    p.setTotalYellowCards(p.getTotalYellowCards() + pM.getSum_yellow_card_in_match());
                    p.setTotalRedCards(p.getTotalRedCards() + pM.getSum_red_card_in_match());
                }
            }
            this.playInTournamentRepository.save(p);
        }
    }

    @DeleteMapping("/auth/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete(@PathVariable Long id){
        this.playInTournamentRepository.deleteById(id);
    }
}
