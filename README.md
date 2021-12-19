# Mars Rovers REST API

Code to return the rovers' position on Mars after some directions are given. <br>
The project's endpoints can be found at http://localhost:8080/swagger-ui.html

### You can't add rovers which positions are outside the surface's boundaries!

A rover with position X, Y = 12, 12 when we try to add it to surface with extremes 5, 5 will not be added. 
If you have a list of rovers and just some of them don't respect the surface's limits, the ones that match the criteria will be added, avoiding repetition.

### Deleting a rover or a surface

For you to delete a surface, you need to delete its rovers first and once you delete a rover, the code will remove it from the list of rovers on the surface it belongs

A rover with position X, Y = 12, 12 when we try to add it to surface with extremes 5, 5 will not be added.
If you have a list of rovers and just some of them don't respect the surface's limits, the ones that match the criteria will be added, avoiding repetition.

### Rovers don't collide, neither go outside the limits of the surface:
The rovers can not be on the same position, nor being created where a rover is already present, so if a rover is moving 
and detects that it will collide with another one or go over the surface's edge, it will stop and will move to the next command. For example:
```
rover 1 -> x: 5, y: 1, direction: E
rover 2 -> x: 5, y: 2, direction: S

When rover 2 gets the commands 'MRM', it will not perform the first command since it will cause a collision,
and its final position would be x: 4, y: 2, direction: W

The same happens if the surface edges are X: 5 and Y: 5, if the command 'MRM' is given to rover 1, 
its final position would be x: 5, y: 0, direction: S
```

####Possible commands to be used:
```
L, R, M -> Rover to turn Left, Right or Move forward according to its current direction
```

####Possible directions to be used:
```
N, E, W, S -> North, East, West and South
```

