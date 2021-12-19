package com.marsrovers.rovers.assembler;

import com.marsrovers.rovers.controllers.RoverController;
import com.marsrovers.rovers.dtos.RoverGetCompleteDTO;
import com.marsrovers.rovers.mappers.RoverMapper;
import com.marsrovers.rovers.models.Rover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class RoverModelAssembler implements RepresentationModelAssembler<Rover, RoverGetCompleteDTO> {

    @Autowired
    private RoverMapper roverMapper;

    @Override
    public RoverGetCompleteDTO toModel(Rover entity) {
        RoverGetCompleteDTO roverGetCompleteDTO = roverMapper.roverToRoverCompleteDTO(entity);
        roverGetCompleteDTO.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RoverController.class).getRover(roverGetCompleteDTO.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RoverController.class).deleteRover(roverGetCompleteDTO.getId())).withRel("delete Rover"),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RoverController.class).getSurfaceFromRover(roverGetCompleteDTO.getId())).withRel("get surface from Rover"),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RoverController.class).getCurrentPosition(roverGetCompleteDTO.getId())).withRel("get current position of Rover"),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RoverController.class).getRovers()).withRel("allRovers"));

        return roverGetCompleteDTO;

    }
}
