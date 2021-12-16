package com.marsrovers.controllers;

import com.marsrovers.dtos.*;
import com.marsrovers.mappers.RoverMapper;
import com.marsrovers.models.Rover;
import com.marsrovers.repository.RoverRepository;
import com.marsrovers.services.RoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rover")
public class RoverController {

    @Autowired
    private RoverService roverService;

    @GetMapping(path = "/getRover/{id}", produces = {"application/json"})
    public ResponseEntity<RoverGetCompleteDTO> getRover(@PathVariable long id) {
        return ResponseEntity.ok(roverService.getRover(id));
    }

    @GetMapping(path = "/getRovers", produces = {"application/json"})
    public ResponseEntity<List<RoverGetCompleteDTO>> getRovers() {
        return ResponseEntity.ok(roverService.getRovers());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<RoverBasicDTO> createRover(@Valid @RequestBody RoverCreationDTO roverCreationDTO) {
        return ResponseEntity.ok(roverService.createRover(roverCreationDTO));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<RoverBasicDTO> deleteRover(@PathVariable long id) {
        return ResponseEntity.ok(roverService.deleteRover(id));
    }

    @GetMapping(path = "/getSurface/{id}", produces = {"application/json"})
    public ResponseEntity<RoverGetSurfaceDTO> getSurfaceFromRover(@PathVariable long id) {
        return ResponseEntity.ok(roverService.getSurfaceFromRover(id));
    }

    @GetMapping(path = "/getCurrentPosition/{id}", produces = {"application/json"})
    public ResponseEntity<RoverBasicDTO> getCurrentPosition(@PathVariable long id) {
        return ResponseEntity.ok(roverService.getCurrentPosition(id));
    }

    /*@PutMapping(path = "sendCommandsToRover/", produces = {"application/json"})
    public ResponseEntity<RoverResponseDTO> sendCommands(@RequestBody )*/

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
