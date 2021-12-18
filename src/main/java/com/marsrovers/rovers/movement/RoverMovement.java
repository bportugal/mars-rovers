package com.marsrovers.rovers.movement;

import com.marsrovers.rovers.models.Rover;
import com.marsrovers.rovers.repository.RoverRepository;
import com.marsrovers.surfaces.model.Surface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RoverMovement {

    private Directions currentDirection;
    private Map<String, Integer> position;

    @Autowired
    private RoverRepository roverRepository;

    public Rover moveRover(Rover rover, String command) {

        currentDirection = rover.getDirection();
        // Arrays.stream(RoverCommand.values()).map(RoverCommand::name).toArray(String[]::new);

        List<String> commandStrings = Arrays.stream(Commands.values())
                .map(Commands::name)
                .collect(Collectors.toList());

        //boolean isCommandValid = Arrays.stream(RoverCommand.values()).anyMatch((t) -> t.name().equals(command));

        if (!commandStrings.contains(command.toUpperCase())) {
            roverRepository.save(rover);
            throw new IllegalArgumentException(
                    "Rover position updated (X: " + rover.getXPosition() + ", Y: " + rover.getYPosition() + ", direction: " + rover.getDirection() + "), '"
                    + command.toUpperCase() + "' command not supported. Possible values are L, R and M");
        }
        switch (command.toUpperCase()) {
            case "R":
            case "L":
                currentDirection = currentDirection.nextDirection(command.toUpperCase());
                rover.setDirection(currentDirection);
                break;
            case "M":
                position = move(rover);
                rover.setXPosition(position.get("X"));
                rover.setYPosition(position.get("Y"));
                break;
            default:
                throw new IllegalArgumentException("Command not supported");
        }
        return rover;
    }

    private Map<String, Integer> move(Rover rover) {
        Map<String, Integer> currentPosition = new HashMap<>();
        currentPosition.put("X", rover.getXPosition());
        currentPosition.put("Y", rover.getYPosition());
        Surface surface = rover.getSurface();

        currentPosition = currentDirection.nextPosition(currentPosition, surface);
        Set<Rover> rovers = surface.getRovers();
        if (rovers.size() == 1) {
            return currentPosition;
        }

        for (Rover roverFromSurface: rovers) {
            if (roverFromSurface.getId() != rover.getId() &&
                    doRoversCollide(currentPosition.get("X"), currentPosition.get("Y"), roverFromSurface)) {

                return new HashMap<String, Integer>() {{put("X", rover.getXPosition()); put("Y", rover.getYPosition());
                }};
            }
        }

        return currentPosition;
    }

    private boolean doRoversCollide(Integer x, Integer y, Rover roverFromSurface) {
        return (x.equals(roverFromSurface.getXPosition()) && y.equals(roverFromSurface.getYPosition()));
    }
}
