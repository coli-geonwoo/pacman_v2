package game.utils.reader;

import game.entities.Entity;
import game.entities.Pacman;
import game.entities.Wall;
import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;
import java.util.List;
import java.util.Optional;

public record GameInfoReadResponse(
        Pacman pacman,
        Optional<Ghost> target,
        List<Entity> entities,
        List<Ghost> ghosts,
        List<Wall> walls,
        int objectCnt
) {

}
