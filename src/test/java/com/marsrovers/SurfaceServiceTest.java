package com.marsrovers;

import com.marsrovers.rovers.dtos.RoverBasicDTO;
import com.marsrovers.rovers.dtos.RoverCreationDTO;
import com.marsrovers.rovers.dtos.RoverGetCompleteDTO;
import com.marsrovers.rovers.mappers.RoverMapper;
import com.marsrovers.rovers.models.Rover;
import com.marsrovers.rovers.movement.Directions;
import com.marsrovers.rovers.services.RoverService;
import com.marsrovers.surfaces.assembler.SurfaceModelAssembler;
import com.marsrovers.surfaces.dtos.SurfaceBasicDTO;
import com.marsrovers.surfaces.dtos.SurfaceCreationDTO;
import com.marsrovers.surfaces.dtos.SurfaceGetCompleteDTO;
import com.marsrovers.surfaces.mapper.SurfaceMapper;
import com.marsrovers.surfaces.model.Surface;
import com.marsrovers.surfaces.repository.SurfaceRepository;
import com.marsrovers.surfaces.services.SurfaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = SurfaceService.class)
public class SurfaceServiceTest {

    @InjectMocks
    private SurfaceService surfaceService;

    @MockBean
    private SurfaceRepository surfaceRepository;

    @MockBean
    private SurfaceMapper surfaceMapper;

    @MockBean
    private SurfaceModelAssembler surfaceModelAssembler;

    @MockBean
    private RoverService roverService;

    @MockBean
    private RoverMapper roverMapper;

    private List<Surface> list = new ArrayList<>();
    private SurfaceCreationDTO creationDTO1;

    @BeforeEach
    void setup() {
        Surface surface1 = new Surface();
        surface1.setId(1);
        surface1.setName("test1");
        surface1.setExtremeX(4);
        surface1.setExtremeY(3);

        Surface surface2 = new Surface();
        surface2.setId(2);
        surface2.setName("test2");
        surface1.setExtremeX(4);
        surface1.setExtremeY(3);

        list.add(surface1);
        list.add(surface2);

        SurfaceGetCompleteDTO surfaceComplete = new SurfaceGetCompleteDTO();
        surfaceComplete.setId(1);
        surfaceComplete.setName("testCompleteDTO");
        surfaceComplete.setExtremeX(5);
        surfaceComplete.setExtremeY(5);

        creationDTO1 = new SurfaceCreationDTO();
        creationDTO1.setId(1);
        creationDTO1.setExtremeY(3);
        creationDTO1.setExtremeY(3);
        creationDTO1.setName("surface 1");

        SurfaceBasicDTO basicDTO = new SurfaceBasicDTO();
        basicDTO.setId(1);
        basicDTO.setExtremeY(3);
        basicDTO.setExtremeY(3);
        basicDTO.setName("surface 1");

        Mockito.when(surfaceRepository.findAll()).thenReturn(list);
        Mockito.when(surfaceRepository.findByName(Mockito.any(String.class))).thenReturn(new ArrayList<>());
        Mockito.when(surfaceRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Mockito.when(surfaceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(list.get(0)));
        Mockito.when(surfaceModelAssembler.toModel(Mockito.any(Surface.class))).thenReturn(surfaceComplete);
        Mockito.when(surfaceMapper.surfaceToSurfaceCompleteDTO(Mockito.any(Surface.class))).thenReturn(surfaceComplete);

        Mockito.when(surfaceRepository.saveAndFlush(surfaceMapper.surfaceCreationDTOToSurface(Mockito.any(SurfaceCreationDTO.class)))).thenReturn(surface1);
        Mockito.when(surfaceMapper.surfaceToSurfaceBasicDTO(Mockito.any(Surface.class))).thenReturn(basicDTO);
    }

    @Test
    public void getAllSurfacesTest() {

        List<SurfaceGetCompleteDTO> completeDTO = surfaceService.getSurfaces();

        assertEquals(completeDTO.size(), 2);
    }

    @Test
    public void getSurfacesByIdTest() {
        SurfaceGetCompleteDTO completeDTO = surfaceService.getSurface(1);

        assertNotEquals(completeDTO.getName(), list.get(0).getName()); // expected: test2, actual: test1
    }

    @Test
    public void createRoverTest() {

        SurfaceBasicDTO dtoCreated = surfaceService.createSurface(creationDTO1);

        assertEquals("surface 1", dtoCreated.getName());
    }
}
