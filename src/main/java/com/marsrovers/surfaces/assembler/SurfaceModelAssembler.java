package com.marsrovers.surfaces.assembler;

import com.marsrovers.surfaces.controller.SurfaceController;
import com.marsrovers.surfaces.dtos.SurfaceGetCompleteDTO;
import com.marsrovers.surfaces.mapper.SurfaceMapper;
import com.marsrovers.surfaces.model.Surface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class SurfaceModelAssembler implements RepresentationModelAssembler<Surface, SurfaceGetCompleteDTO> {

    @Autowired
    private SurfaceMapper surfaceMapper;

    @Override
    public SurfaceGetCompleteDTO toModel(Surface entity) {
        SurfaceGetCompleteDTO surfaceGetCompleteDTO = surfaceMapper.surfaceToSurfaceCompleteDTO(entity);
        surfaceGetCompleteDTO.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(SurfaceController.class).getSurface(surfaceGetCompleteDTO.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(SurfaceController.class).deleteSurface(surfaceGetCompleteDTO.getId())).withRel("delete Surface"),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(SurfaceController.class).getSurfaces()).withRel("allSurfaces"));

        return surfaceGetCompleteDTO;
    }
}
