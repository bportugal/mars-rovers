package com.marsrovers.surfaces.controller;

import com.marsrovers.exceptions.ResponseHandler;
import com.marsrovers.surfaces.dtos.*;
import com.marsrovers.surfaces.services.SurfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
        List<SurfaceGetCompleteDTO> surfaces = surfaceService.getSurfaces();
        if (surfaces.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(surfaces);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<SurfaceBasicDTO> createSurface(@Valid @RequestBody SurfaceCreationDTO surfaceCreationDTO) {
        SurfaceBasicDTO createdSurface = surfaceService.createSurface(surfaceCreationDTO);
        if (createdSurface == null) {
            return ResponseEntity.notFound().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/getSurface/{id}")
                .buildAndExpand(createdSurface.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdSurface);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Object> deleteSurface(@PathVariable long id) {
        try {
            String result = surfaceService.deleteSurface(id);
            return ResponseHandler.generateResponse("Deleted!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.OK, null);
        }
    }

    @GetMapping(path = "/getRovers/{id}", produces = {"application/json"})
    public ResponseEntity<SurfaceGetRoversDTO> getRovers(@PathVariable long id) {
        return ResponseEntity.ok(surfaceService.getRovers(id));
    }

    @GetMapping(path = "/getSurfaceBoundaries/{id}", produces = {"application/json"})
    public ResponseEntity<SurfaceBasicDTO> getBoundaries(@PathVariable long id) {
        return ResponseEntity.ok(surfaceService.getBoundaries(id));
    }

    @PostMapping(path = "/addRovers/{id}")
    public ResponseEntity<SurfaceGetCompleteDTO> addRovers(@RequestBody SurfaceAddRoverDTO roversIds, @PathVariable long id) {
        return ResponseEntity.ok(surfaceService.addRovers(roversIds, id));
    }

    @DeleteMapping(value = "/deleteAllRoversFromSurface/{id}")
    public ResponseEntity<Object> deleteAllRoversFromSurface(@PathVariable long id) {
        try {
            String result = surfaceService.deleteAllRoversFromSurface(id);
            return ResponseHandler.generateResponse("Deleted!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.OK, null);
        }
    }

}
