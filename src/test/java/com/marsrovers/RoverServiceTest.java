package com.marsrovers;

import com.marsrovers.rovers.assembler.RoverModelAssembler;
import com.marsrovers.rovers.dtos.RoverBasicDTO;
import com.marsrovers.rovers.dtos.RoverCreationDTO;
import com.marsrovers.rovers.dtos.RoverGetCompleteDTO;
import com.marsrovers.rovers.mappers.RoverMapper;
import com.marsrovers.rovers.models.Rover;
import com.marsrovers.rovers.movement.Directions;
import com.marsrovers.rovers.movement.RoverMovement;
import com.marsrovers.rovers.repository.RoverRepository;
import com.marsrovers.rovers.services.RoverService;
import com.marsrovers.surfaces.mapper.SurfaceMapper;
import com.marsrovers.surfaces.services.SurfaceService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = RoverService.class)
public class RoverServiceTest {

    @InjectMocks
    private RoverService roverService;

    @MockBean
    private RoverRepository roverRepository;

    @MockBean
    private RoverModelAssembler roverModelAssembler;

    @MockBean
    private RoverMapper roverMapper;

    @MockBean
    private RoverMovement roverMovement;

    @MockBean
    private SurfaceService surfaceService;

    @MockBean
    private SurfaceMapper surfaceMapper;

    private List<Rover> list = new ArrayList<>();
    private RoverCreationDTO creationDTO1;

    @BeforeEach
    void setup() {
        Rover rover1 = new Rover();
        rover1.setId(1);
        rover1.setName("test1");
        rover1.setXPosition(0);
        rover1.setYPosition(0);
        rover1.setDirection(Directions.N);

        Rover rover2 = new Rover();
        rover2.setId(2);
        rover2.setName("test2");
        rover2.setXPosition(2);
        rover2.setYPosition(2);
        rover2.setDirection(Directions.E);

        list.add(rover1);
        list.add(rover2);

        RoverGetCompleteDTO roverComplete = new RoverGetCompleteDTO();
        roverComplete.setId(1);
        roverComplete.setName("testCompleteDTO");
        roverComplete.setXPosition(0);
        roverComplete.setYPosition(0);
        roverComplete.setDirection(Directions.N);

        creationDTO1 = new RoverCreationDTO();
        creationDTO1.setId(1);
        creationDTO1.setDirection(Directions.W);
        creationDTO1.setXPosition(3);
        creationDTO1.setYPosition(3);
        creationDTO1.setName("rover 1");

        RoverBasicDTO basicDTO = new RoverBasicDTO();
        basicDTO.setId(1);
        basicDTO.setDirection(Directions.W);
        basicDTO.setXPosition(3);
        basicDTO.setYPosition(3);
        basicDTO.setName("rover 1");

        Mockito.when(roverRepository.findAll()).thenReturn(list);
        Mockito.when(roverRepository.findByName(Mockito.any(String.class))).thenReturn(new ArrayList<>());
        Mockito.when(roverRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Mockito.when(roverRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(list.get(0)));
        Mockito.when(roverModelAssembler.toModel(Mockito.any(Rover.class))).thenReturn(roverComplete);
        Mockito.when(roverMapper.roverToRoverCompleteDTO(Mockito.any(Rover.class))).thenReturn(roverComplete);

        Mockito.when(roverRepository.saveAndFlush(roverMapper.roverCreationDTOToRover(Mockito.any(RoverCreationDTO.class)))).thenReturn(rover1);
        Mockito.when(roverMapper.roverToRoverBasicDTO(Mockito.any(Rover.class))).thenReturn(basicDTO);
    }

    @Test
    public void getAllRoversTest() {

        List<RoverGetCompleteDTO> completeDTO = roverService.getRovers();

        assertEquals(completeDTO.size(), 2);
    }

    @Test
    public void getRoversByIdTest() {
        RoverGetCompleteDTO completeDTO = roverService.getRover(1);

        assertNotEquals(completeDTO.getName(), list.get(0).getName()); // expected: test2, actual: test1
    }

    @Test
    public void createRoverTest() {

        RoverBasicDTO dtoCreated = roverService.createRover(creationDTO1);

        assertEquals("rover 1", dtoCreated.getName());
    }
}
