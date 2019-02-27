package com.manage.footballapi.API.Controller;

import com.manage.footballapi.API.ViewModel.GamePositionViewModel;
import com.manage.footballapi.Mapping.MappingViewModelToEnity;
import com.manage.footballapi.Model.GamePosition;
import com.manage.footballapi.Repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/positions/api")
@CrossOrigin
public class GamePositionController {

    private PositionRepository positionRepository;
    private MappingViewModelToEnity mapper;

    @Autowired
    public GamePositionController(PositionRepository positionRepository, MappingViewModelToEnity mapper){
        this.positionRepository = positionRepository;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    public List<GamePosition> all(){
        return this.positionRepository.findAll();
    }

    @PostMapping("/auth")
    public GamePosition create(@RequestBody GamePositionViewModel positionViewModel, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new ValidationException();
        }
        GamePosition position = this.mapper.convertFromPositionViewModel(positionViewModel);
        this.positionRepository.save(position);
        return position;
    }

    @DeleteMapping("/auth/{id}")
    public void delete(@PathVariable Long id){
        this.positionRepository.deleteById(id);
    }
}
