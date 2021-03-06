package com.marsrovers.surfaces.mapper;

import com.marsrovers.surfaces.dtos.SurfaceBasicDTO;
import com.marsrovers.surfaces.dtos.SurfaceCreationDTO;
import com.marsrovers.surfaces.dtos.SurfaceGetCompleteDTO;
import com.marsrovers.surfaces.dtos.SurfaceGetRoversDTO;
import com.marsrovers.surfaces.model.Surface;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SurfaceMapper {

    // get Surface's limits
    SurfaceBasicDTO surfaceToSurfaceBasicDTO (Surface surface);

    // get Surface
    SurfaceGetCompleteDTO surfaceToSurfaceCompleteDTO (Surface surface);

    //get Rovers on a Surface
    SurfaceGetRoversDTO surfaceToSurfaceGetRoversDTO (Surface surface);

    // creating new surface from DTO
    Surface surfaceCreationDTOToSurface (SurfaceCreationDTO surfaceCreationDTO);

    // update an existing Surface from a complete SurfaceDTO object
    Surface surfaceUpdateDTOToSurface (SurfaceGetCompleteDTO surfaceGetCompleteDTO);

}
