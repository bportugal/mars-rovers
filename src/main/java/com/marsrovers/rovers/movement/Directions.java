package com.marsrovers.rovers.movement;

import com.marsrovers.surfaces.model.Surface;

import java.util.HashMap;
import java.util.Map;

public enum Directions {
    N {
        @Override
        public String nextDirection(String movement) {
            if (movement.equals("L")) {
                return "W";
            } else if (movement.equals("R")) {
                return "E";
            }
            return null;
        }
        @Override
        public Integer move(Integer y, Surface surface) {
            if ((y+1) > surface.getExtremeY()) {
                return y;
            }
            return y+1;
        }
    },
    E {
        @Override
        public String nextDirection(String movement) {
            if (movement.equals("L")) {
                return "N";
            } else if (movement.equals("R")) {
                return "S";
            }
            return null;
        }
        @Override
        public Integer move(Integer x, Surface surface) {
            if ((x+1) > surface.getExtremeX()) {
                return x;
            }
            return x+1;
        }
    },
    W {
        @Override
        public String nextDirection(String movement) {
            if (movement.equals("L")) {
                return "S";
            } else if (movement.equals("R")) {
                return "N";
            }
            return null;
        }
        @Override
        public Integer move(Integer x, Surface surface) {
            if ((x-1) < 0) {
                return x;
            }
            return x-1;
        }
    },
    S {
        @Override
        public String nextDirection(String movement) {
            if (movement.equals("L")) {
                return "E";
            } else if (movement.equals("R")) {
                return "W";
            }
            return null;
        }
        @Override
        public Integer move(Integer y, Surface surface) {
            if ((y-1) < 0) {
                return y;
            }
            return y-1;
        }
    };

    public abstract String nextDirection(String s);

    public abstract Integer move(Integer position, Surface surface);

    public static String rotate90(String direction) {
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
    }

    public static Map<String, Integer> nextPosition(Integer x, Integer y, String direction, Surface surface) {
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
    }

}
