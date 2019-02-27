package com.manage.footballapi.API.Controller;

import com.manage.footballapi.API.ViewModel.PlayerViewModel;
import com.manage.footballapi.Mapping.MappingEntityToViewModel;
import com.manage.footballapi.Mapping.MappingViewModelToEnity;
import com.manage.footballapi.Model.Player;
import com.manage.footballapi.Repository.PlayerRepository;
import org.hibernate.ResourceClosedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/players/api")
@CrossOrigin
public class PlayerController {

    private PlayerRepository playerRepository;
    private MappingViewModelToEnity mapper;
    private MappingEntityToViewModel mappingEntityToViewModel;

    public PlayerController(PlayerRepository playerRepository, MappingViewModelToEnity mapper, MappingEntityToViewModel mappingEntityToViewModel){
        this.playerRepository = playerRepository;
        this.mapper = mapper;
        this.mappingEntityToViewModel = mappingEntityToViewModel;
    }

    @GetMapping("/all")
    public List<PlayerViewModel> getAll (){
        List<Player> players = this.playerRepository.findAll();
        List<PlayerViewModel> playerViewModels = players.stream()
                                                    .map(player -> this.mappingEntityToViewModel.convertToPlayerViewModel(player))
                                                    .collect(Collectors.toList());
        return playerViewModels;
    }

    @GetMapping("/byId/{id}")
    public PlayerViewModel getById(@PathVariable Long id) {
        Player player = this.playerRepository.findById(id).get();
        if(player != null){
            return this.mappingEntityToViewModel.convertToPlayerViewModel(player);
        }
        return null;
    }

    @GetMapping("/getPlayerExcept/{ids}")
    public List<Player> getAllExceptforIds(@PathVariable Long[] ids){
        return this.playerRepository.findByIdNotIn(ids);
    }
    @PostMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PlayerViewModel save (@RequestBody PlayerViewModel playerViewModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException();
        }
        Player player = this.mapper.convertFromPlayerViewModel(playerViewModel);

        player = this.playerRepository.saveAndFlush(player);

        //PlayerViewModel playerViewModel1 = this.mappingEntityToViewModel.convertToPlayerViewModel(player);
        return this.mappingEntityToViewModel.convertToPlayerViewModel( this.playerRepository.saveAndFlush(player));
    }

    @PutMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN')")
   public PlayerViewModel update(@RequestBody PlayerViewModel playerViewModel){
        Player pl =  this.playerRepository.findById(playerViewModel.getId())
                .map(player -> {player.setNationality(playerViewModel.getNationality());
                                player.setWeight(playerViewModel.getWeight());
                                player.setHeight(playerViewModel.getHeight());
                                player.setFirst_name(playerViewModel.getFirstName());
                                player.setLast_name(playerViewModel.getLastName());
                                player.setDate_of_birth(playerViewModel.getDateOfBirth());
                                player.setPortrait_image(playerViewModel.getImage());
                return this.playerRepository.save(player);}).orElseThrow(()-> new ResourceClosedException("Player can not find"));
        return this.mappingEntityToViewModel.convertToPlayerViewModel(pl);

    }

    @GetMapping("/byIdRange/{idStart}/{idEnd}")
    public List<Player>getInRangeById(@PathVariable Long idStart, @PathVariable Long idEnd) {
        return  null;
    }

    @DeleteMapping("/auth/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete (@PathVariable Long id){
        this.playerRepository.deleteById(id);
    }
}
