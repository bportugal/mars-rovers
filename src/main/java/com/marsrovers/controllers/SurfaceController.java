package com.marsrovers.controllers;

import com.marsrovers.dtos.*;
import com.marsrovers.models.Surface;
import com.marsrovers.services.SurfaceService;
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
@RequestMapping(value = "/surface")
public class SurfaceController {

    @Autowired
    private SurfaceService surfaceService;

    @GetMapping(path = "/getSurface/{id}", produces = {"application/json"})
    public ResponseEntity<SurfaceGetCompleteDTO> getSurface(@PathVariable long id) {
        return ResponseEntity.ok(surfaceService.getSurface(id));
    }

    @GetMapping(path = "/getSurfaces", produces = {"application/json"})
    public ResponseEntity<List<SurfaceGetCompleteDTO>> getSurfaces() {
        return ResponseEntity.ok(surfaceService.getSurfaces());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Surface> createSurface(@Valid @RequestBody SurfaceCreationDTO surfaceCreationDTO) {
        return ResponseEntity.ok(surfaceService.createSurface(surfaceCreationDTO));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<SurfaceBasicDTO> deleteRover(@PathVariable long id) {
        return ResponseEntity.ok(surfaceService.deleteSurface(id));
    }

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
