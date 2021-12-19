package com.marsrovers.surfaces.controller;

import com.marsrovers.exceptions.ResponseHandler;
import com.marsrovers.surfaces.dtos.*;
import com.marsrovers.surfaces.services.SurfaceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/surface")
public class SurfaceController {

    @Autowired
    private SurfaceService surfaceService;

    @GetMapping(path = "/getSurface/{id}", produces = {"application/json"})
    @ApiOperation(value = "Returns a specific surface")
    public ResponseEntity<SurfaceGetCompleteDTO> getSurface(@PathVariable long id) {
        return ResponseEntity.ok(surfaceService.getSurface(id));
    }

    @GetMapping(path = "/getSurfaces", produces = {"application/json"})
    @ApiOperation(value = "Returns all the surfaces created")
    public ResponseEntity<List<SurfaceGetCompleteDTO>> getSurfaces() {
        return ResponseEntity.ok(surfaceService.getSurfaces());
    }

    @PostMapping(path = "/create")
    @ApiOperation(value = "Creates a surface")
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
    @ApiOperation(value = "Deletes a surface")
    public ResponseEntity<Object> deleteSurface(@PathVariable long id) {
        try {
            String result = surfaceService.deleteSurface(id);
            return ResponseHandler.generateResponse("Deleted!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.OK, null);
        }
    }

    @GetMapping(path = "/getRovers/{id}", produces = {"application/json"})
    @ApiOperation(value = "Returns the rovers of a specific surface")
    public ResponseEntity<SurfaceGetRoversDTO> getRovers(@PathVariable long id) {
        return ResponseEntity.ok(surfaceService.getRovers(id));
    }

    @GetMapping(path = "/getSurfaceBoundaries/{id}", produces = {"application/json"})
    @ApiOperation(value = "Returns the edges of a specific surface")
    public ResponseEntity<SurfaceBasicDTO> getBoundaries(@PathVariable long id) {
        return ResponseEntity.ok(surfaceService.getBoundaries(id));
    }

    @PutMapping(path = "/addRovers/{id}")
    @ApiOperation(value = "Add a list of rovers in a surface")
    public ResponseEntity<Object> addRovers(@RequestBody SurfaceAddRoverDTO roversIds, @PathVariable long id) {
        Map<String, String> roverStatus = surfaceService.addRovers(roversIds, id, false);
        return ResponseHandler.generateResponse("Complete", HttpStatus.OK, roverStatus);
        // return ResponseEntity.ok(surfaceService.addRovers(roversIds, id, false));
    }

    @DeleteMapping(value = "/deleteAllRoversFromSurface/{id}")
    @ApiOperation(value = "Deletes all rovers from a surface")
    public ResponseEntity<Object> deleteAllRoversFromSurface(@PathVariable long id) {
        try {
            String result = surfaceService.deleteAllRoversFromSurface(id);
            return ResponseHandler.generateResponse("Deleted!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.OK, null);
        }
    }

}
