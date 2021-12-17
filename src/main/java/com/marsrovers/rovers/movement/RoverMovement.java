package com.marsrovers.rovers.movement;

import com.marsrovers.rovers.models.Rover;
import com.marsrovers.surfaces.model.Surface;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RoverMovement {

    private String currentDirection;
    private Map<String, Integer> position;

    public Rover moveRover(Rover rover, String command) {

        String direction = rover.getDirection();
        // Arrays.stream(RoverCommand.values()).map(RoverCommand::name).toArray(String[]::new);

        List<String> commandStrings = Arrays.stream(RoverCommand.values())
                .map(RoverCommand::name)
                .collect(Collectors.toList());

        //boolean isCommandValid = Arrays.stream(RoverCommand.values()).anyMatch((t) -> t.name().equals(command));

        if (commandStrings.contains(command.toUpperCase())) {
            switch (command.toUpperCase()) {
                case "R":
                case "L":
                    currentDirection = Directions.rotate90(direction);
                    break;
                case "M":
                    position = move(rover);
            }
        }

        rover.setDirection(currentDirection);
        rover.setXPosition(position.get("X"));
        rover.setYPosition(position.get("Y"));

        return rover;
    }

    private Map<String, Integer> move(Rover rover) {
        Integer x = rover.getXPosition();
        Integer y = rover.getYPosition();
        Surface surface = rover.getSurface();

        return Directions.nextPosition(x, y, currentDirection, surface);
    }
}
