package com.marsrovers.rovers.mappers;

import com.marsrovers.rovers.dtos.*;
import com.marsrovers.rovers.models.Rover;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoverMapper {

    // get Current Position
    RoverBasicDTO roverToRoverBasicDTO (Rover rover);

    // get Rover
    RoverGetCompleteDTO roverToRoverCompleteDTO (Rover rover);

    //get Surface from Rover
    RoverGetSurfaceDTO roverToRoverSurfaceDTO (Rover rover);

    Rover roverSurfaceToRover (RoverGetSurfaceDTO rover);

    RoverGetSurfaceDTO roverCompleteToRoverSurface (RoverGetCompleteDTO rover);

    // creating new rover from DTO
    Rover roverCreationDTOToRover (RoverCreationDTO roverCreationDTO);

    // update an existing Rover from a complete RoverDTO object
    Rover roverCompleteDTOToRover (RoverGetCompleteDTO roverGetCompleteDTO);

    //Rover roverCommandsToRoverNewPosition (RoverCommandDTO roverCommandDTO);

    RoverResponseDTO roverMappedAfterCommands (Rover rover);
}
