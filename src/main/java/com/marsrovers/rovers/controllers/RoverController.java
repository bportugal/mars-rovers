package com.marsrovers.rovers.controllers;

import com.marsrovers.exceptions.ResponseHandler;
import com.marsrovers.rovers.dtos.*;
import com.marsrovers.rovers.models.Rover;
import com.marsrovers.rovers.services.RoverService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/rover")
public class RoverController {

    @Autowired
    private RoverService roverService;

    @GetMapping(path = "/getRover/{id}", produces = {"application/json"})
    @ApiOperation(value = "Returns a specific rover")
    public ResponseEntity<RoverGetCompleteDTO> getRover(@PathVariable long id) {
        return ResponseEntity.ok(roverService.getRover(id));
    }

    @GetMapping(path = "/getRovers", produces = {"application/json"})
    @ApiOperation(value = "Returns all the rovers created")
    public ResponseEntity<List<RoverGetCompleteDTO>> getRovers() {
        return ResponseEntity.ok(roverService.getRovers());
    }

    @PostMapping(path = "/create")
    @ApiOperation(value = "Creates a rover")
    public ResponseEntity<RoverBasicDTO> createRover(@Valid @RequestBody RoverCreationDTO roverCreationDTO) {
        RoverBasicDTO createdRover = roverService.createRover(roverCreationDTO);
        if (createdRover == null) {
            return ResponseEntity.notFound().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/getRover/{id}")
                .buildAndExpand(createdRover.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdRover);
    }

    @DeleteMapping(path = "/delete/{id}")
    @ApiOperation(value = "Deletes a rover")
    public ResponseEntity<Object> deleteRover(@PathVariable long id) {
        try {
            String result = roverService.deleteRover(id);
            return ResponseHandler.generateResponse("Deleted!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.OK, null);
        }
    }

    @GetMapping(path = "/getSurface/{id}", produces = {"application/json"})
    @ApiOperation(value = "Returns the surface of a specific rover")
    public ResponseEntity<RoverGetSurfaceDTO> getSurfaceFromRover(@PathVariable long id) {
        return ResponseEntity.ok(roverService.getSurfaceFromRover(id));
    }

    @GetMapping(path = "/getCurrentPosition/{id}", produces = {"application/json"})
    @ApiOperation(value = "Returns the current position of a specific rover")
    public ResponseEntity<RoverBasicDTO> getCurrentPosition(@PathVariable long id) {
        return ResponseEntity.ok(roverService.getCurrentPosition(id));
    }

    @PostMapping(path = "/sendCommandsToRover")
    @ApiOperation(value = "Sends the list of commands to a specific rover")
    public ResponseEntity<RoverResponseDTO> sendCommands(@RequestBody RoverCommandDTO roverCommands) {
        return ResponseEntity.ok(roverService.moveRover(roverCommands));
    }

    @PutMapping(value = "/addSurface/{id}")
    @ApiOperation(value = "Add a surface for the rover")
    public ResponseEntity<RoverGetSurfaceDTO> addSurface(@RequestParam (value = "surfaceId") Long surfaceId, @PathVariable long id) {
        return ResponseEntity.ok(roverService.addSurface(surfaceId, id, false));
    }

}
