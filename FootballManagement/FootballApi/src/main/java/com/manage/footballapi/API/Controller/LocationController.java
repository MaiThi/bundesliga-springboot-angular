package com.manage.footballapi.API.Controller;

import com.manage.footballapi.API.ViewModel.LocationViewModel;
import com.manage.footballapi.Mapping.MappingEntityToViewModel;
import com.manage.footballapi.Mapping.MappingViewModelToEnity;
import com.manage.footballapi.Model.Location;
import com.manage.footballapi.Repository.LocationRepository;
import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin
public class LocationController {

    private LocationRepository locationRepository;
    private MappingViewModelToEnity mapper;
    private MappingEntityToViewModel mappingEntityToViewModel;
    @Autowired
    public LocationController(LocationRepository locationRepository, MappingViewModelToEnity mapper, MappingEntityToViewModel mappingEntityToViewModel){
        this.locationRepository = locationRepository;
        this.mapper = mapper;
        this.mappingEntityToViewModel = mappingEntityToViewModel;
    }

    @GetMapping("/all")
    public List<Location> getAll(){
        return this.locationRepository.findAll();
    }

    @PostMapping
    public LocationViewModel create(@RequestBody LocationViewModel locationViewModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException();
        }
        Location location = this.mapper.convertFromLocationViewModel(locationViewModel);
        this.locationRepository.save(location);

        return this.mappingEntityToViewModel.convertToLocalViewModel(location);
    }

    @PutMapping
    public LocationViewModel update(@Valid @RequestBody LocationViewModel locationViewModel) {
        Location lo =  this.locationRepository.findById(locationViewModel.getId())
                .map(location -> {location.setAddress(locationViewModel.getAddress());
                location.setDesciption(locationViewModel.getDescription());
                location.setName(locationViewModel.getName());
                location.setNumber_holding_people(locationViewModel.getNbPeopleHolded());
                location.setImage(locationViewModel.getImage());
                return this.locationRepository.save(location);
                }).orElseThrow(()-> new ResourceClosedException("Can not find Location with id" + locationViewModel.getId()));
        return this.mappingEntityToViewModel.convertToLocalViewModel(lo);
    }


    @DeleteMapping("/{id}")
    public void delete (@PathVariable Long id){
        this.locationRepository.deleteById(id);
    }
}
