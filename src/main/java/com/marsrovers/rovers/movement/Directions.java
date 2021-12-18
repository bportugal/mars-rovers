package com.marsrovers.rovers.movement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.marsrovers.surfaces.model.Surface;

import java.util.Map;

public enum Directions {
    N("N") {
        @Override
        public Directions nextDirection(String command) {
            if (command.equals("L")) {
                return W;
            } else if (command.equals("R")) {
                return E;
            }
            return null;
        }
        @Override
        public Map<String, Integer> nextPosition(Map<String, Integer> position, Surface surface) {
            Integer y = position.get("Y");
            if ((y+1) <= surface.getExtremeY()) {
                position.put("Y", y+1);
            }
            return position;
        }
    },
    E("E") {
        @Override
        public Directions nextDirection(String command) {
            if (command.equals("L")) {
                return N;
            } else if (command.equals("R")) {
                return S;
            }
            return null;
        }
        @Override
        public Map<String, Integer> nextPosition(Map<String, Integer> position, Surface surface) {
            Integer x = position.get("X");
            if ((x+1) <= surface.getExtremeX()) {
                position.put("X", x+1);
            }
            return position;
        }
    },
    W("W") {
        @Override
        public Directions nextDirection(String command) {
            if (command.equals("L")) {
                return S;
            } else if (command.equals("R")) {
                return N;
            }
            return null;
        }
        @Override
        public Map<String, Integer> nextPosition(Map<String, Integer> position, Surface surface) {
            Integer x = position.get("X");
            if ((x-1) >= 0) {
                position.put("X", x-1);
            }
            return position;
        }
    },
    S("S") {
        @Override
        public Directions nextDirection(String command) {
            if (command.equals("L")) {
                return E;
            } else if (command.equals("R")) {
                return W;
            }
            return null;
        }
        @Override
        public Map<String, Integer> nextPosition(Map<String, Integer> position, Surface surface) {
            Integer y = position.get("Y");
            if ((y-1) >= 0) {
                position.put("Y", y-1);
            }
            return position;
        }
    };

    private final String name;

    Directions(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonCreator()
    public static Directions getDirectionFromNameString(String value) {
        try {
            return Directions.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public abstract Directions nextDirection(String command);

    public abstract Map<String, Integer> nextPosition(Map<String, Integer> position, Surface surface);


    /*public static String rotate90(String command) {
        switch (direction) {
            case "N":
                return Directions.N.nextDirection(direction);
            case "E":
                return Directions.E.nextDirection(direction);
            case "W":
                return Directions.W.nextDirection(direction);
            case "S":
                return Directions.S.nextDirection(direction);
            default:
                return "Direction not valid";
        }
    }*/

    /*public static Map<String, Integer> nextPosition(Integer x, Integer y, String direction, Surface surface) {
        Map<String, Integer> position = new HashMap<>();
        switch (direction) {
            case "N":
                position.put("X", x);
                position.put("Y", Directions.N.move(y, surface));
                return position;
            case "E":
                position.put("X", Directions.E.move(x, surface));
                position.put("Y", y);
                return position;
            case "W":
                position.put("X", Directions.W.move(x, surface));
                position.put("Y", y);
                return position;
            case "S":
                position.put("X", x);
                position.put("Y", Directions.S.move(y, surface));
                return position;
            default:
                return null;
        }
    }*/

}
