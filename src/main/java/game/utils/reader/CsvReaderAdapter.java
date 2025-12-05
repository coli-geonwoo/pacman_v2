package game.utils.reader;

import game.GameLauncher;
import game.entities.Cherry;
import game.entities.Entity;
import game.entities.GhostHouse;
import game.entities.GhostPosition;
import game.entities.MonsterPacGum;
import game.entities.PacGum;
import game.entities.Pacman;
import game.entities.Position;
import game.entities.SuperPacGum;
import game.entities.Wall;
import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;
import game.ghostFactory.AbstractGhostFactory;
import game.utils.CollisionDetector;
import game.utils.CsvReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CsvReaderAdapter implements GameInfoReader {

    private final CsvReader csvReader = new CsvReader();


    @Override
    public GameInfoReadResponse readGameInfo(String[] selectGhosts, int lives) {
        List<List<String>> data = null;
        try {
            data = csvReader.parseCsv(getClass().getClassLoader().getResource("level/level.csv").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        int cellsPerRow = data.get(0).size();
        int cellsPerColumn = data.size();
        int cellSize = 8;

        int objectCnt = 0;
        Pacman pacman = null;
        int ghostIndex = 0;
        Optional<Ghost> target = Optional.empty();
        List<Entity> entities = new ArrayList<>();
        List<Ghost> ghosts = new ArrayList<>();
        List<Wall> walls  =new ArrayList<>();


        for (int xx = 0; xx < cellsPerRow; xx++) {
            for (int yy = 0; yy < cellsPerColumn; yy++) {
                String dataChar = data.get(yy).get(xx);
                if (dataChar.equals("x")) { //Création des murs //벽의 생성
                    Wall wall = new Wall(xx * cellSize, yy * cellSize);
                    entities.add(wall);
                    walls.add(wall);
                } else if (dataChar.equals("P")) { //Création de Pacman
                    pacman = new Pacman(xx * cellSize, yy * cellSize, lives);
                } else if (dataChar.equals("g")) {
                    String selectedGhost = selectGhosts[ghostIndex];
                    Position position = GhostPosition.values()[ghostIndex++].getPosition();
                    Ghost ghost = AbstractGhostFactory.makeByUserInputs(
                            selectedGhost, xx * cellSize, yy * cellSize,
                            position,
                            target.orElse(null)
                    );
                    ghosts.add(ghost);
                    if (ghost instanceof Blinky) {
                        target = Optional.of(ghost);
                    }
                } else if (dataChar.equals(".")) { //Création des PacGums
                    entities.add(new PacGum(xx * cellSize, yy * cellSize));
                    objectCnt++;
                } else if (dataChar.equals("o")) { //Création des SuperPacGums
                    entities.add(new SuperPacGum(xx * cellSize, yy * cellSize));
                } else if (dataChar.equals("-")) {
                    entities.add(new GhostHouse(xx * cellSize, yy * cellSize));
                } else if (dataChar.equals("h")) {
                    objectCnt++;
                    entities.add(new Cherry(xx * cellSize, yy * cellSize));
                } else if (dataChar.equals("m")) {
                    entities.add(new MonsterPacGum(xx * cellSize, yy * cellSize));
                }
            }
        }
        entities.add(pacman);
        entities.addAll(ghosts);
        return new GameInfoReadResponse(pacman, target, entities, ghosts, walls, objectCnt);
    }
}
