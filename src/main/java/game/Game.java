package game;

import static javax.swing.SwingUtilities.invokeLater;

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
import game.entities.pacmanStates.GodMode;
import game.entities.pacmanStates.MonsterMode;
import game.ghostFactory.AbstractGhostFactory;
import game.ghostStates.State;
import game.utils.CollisionDetector;
import game.utils.CsvReader;
import game.utils.KeyHandler;
import java.awt.Graphics2D;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

//게임 자체를 관리하는 클래스
public class Game implements Observer {
    private static List<Entity> objects;
    private static List<Ghost> ghosts;
    private static List<Wall> walls;
    private static int objectCnt = 0;
    private static int crashCnt = 0;

    private static Pacman pacman;
    private static Blinky blinky;
    private static boolean isGameOver = false;

    private static boolean firstInput = false;

    public Game() {
        //게임 초기화
        resetGame();

        //레벨 csv 파일 로딩
        List<List<String>> data = null;
        try {
            data = new CsvReader().parseCsv(getClass().getClassLoader().getResource("level/level.csv").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        int cellsPerRow = data.get(0).size();
        int cellsPerColumn = data.size();
        int cellSize = 8;

        CollisionDetector collisionDetector = new CollisionDetector(this);

        String[] selectedGhosts = GameLauncher.getSelectedGhosts();
        int lives = GameLauncher.getLives();

        int ghostIndex = 0;

        //레벨에는 "격자"가 있으며 CSV 파일의 각 셀에 대해 현재 캐릭터에 따라 특정 엔터티가 격자의 셀에 표시됩니다.
        for (int xx = 0; xx < cellsPerRow; xx++) {
            for (int yy = 0; yy < cellsPerColumn; yy++) {
                String dataChar = data.get(yy).get(xx);
                if (dataChar.equals("x")) { //Création des murs //벽의 생성
                    objects.add(new Wall(xx * cellSize, yy * cellSize));
                } else if (dataChar.equals("P")) { //Création de Pacman
                    pacman = new Pacman(xx * cellSize, yy * cellSize, lives);
                    pacman.setCollisionDetector(collisionDetector);

                    //다양한 Pacman 관찰자들의 기록
                    pacman.registerObserver(GameLauncher.getUIPanel());
                    pacman.registerObserver(this);
                } else if (dataChar.equals("g")) {
                    String selectedGhost = selectedGhosts[ghostIndex];
                    Position position = GhostPosition.values()[ghostIndex++].getPosition();
                    Ghost ghost = AbstractGhostFactory.makeByUserInputs(selectedGhost, xx * cellSize, yy * cellSize,
                            position);
                    ghosts.add(ghost);
                    if (ghost instanceof Blinky) {
                        blinky = (Blinky) ghost;
                    }
                } else if (dataChar.equals(".")) { //Création des PacGums
                    objects.add(new PacGum(xx * cellSize, yy * cellSize));
                    objectCnt++;
                } else if (dataChar.equals("o")) { //Création des SuperPacGums
                    objects.add(new SuperPacGum(xx * cellSize, yy * cellSize));
                } else if (dataChar.equals("-")) { //Création des murs de la maison des fantômes //유령집 벽 만들기
                    objects.add(new GhostHouse(xx * cellSize, yy * cellSize));
                } else if (dataChar.equals("h")) {
                    objectCnt++;
                    objects.add(new Cherry(xx * cellSize, yy * cellSize));
                } else if (dataChar.equals("m")) { //Création des murs de la maison des fantômes //유령집 벽 만들기
                    objects.add(new MonsterPacGum(xx * cellSize, yy * cellSize));
                }
            }
        }
        objects.add(pacman);
        objects.addAll(ghosts);

        for (Entity o : objects) {
            if (o instanceof Wall) {
                walls.add((Wall) o);
            }
        }
    }

    public static List<Wall> getWalls() {
        return walls;
    }

    public List<Entity> getEntities() {
        return objects;
    }

    public static void increaseCrash() {
        crashCnt++;
        if (crashCnt == objectCnt) {
            Game.isGameOver = true;
            invokeLater(() -> GameLauncher.showGameOver(GameLauncher.getUIPanel().getScore()));
        }
    }

    //모든 엔터티 업데이트
    public void update() {
        for (Entity o : objects) {
            if (!o.isDestroyed()) {
                o.update();
            }
        }
    }

    //입력 관리
    public void input(KeyHandler k) {
        pacman.input(k);
    }

    //모든 엔터티의 렌더링
    public void render(Graphics2D g) {
        for (Entity o : objects) {
            if (!o.isDestroyed()) {
                o.render(g);
            }
        }
    }

    public static Pacman getPacman() {
        return pacman;
    }

    public static Ghost getTargetGhost() {
        if (blinky != null) {
            return blinky;
        }
        return ghosts.get(0);
    }

    public static Position getPacmanPosition() {
        return new Position(pacman.getxPos(), pacman.getyPos());
    }

    //팩맨이 팩덤, 파워 펠릿, 유령과 접촉하면 게임에 알림이 전송됩니다.
    @Override
    public void updatePacGumEaten(PacGum pg) {
        pg.destroy(); //La PacGum est détruite quand Pacman la mange
    }

    @Override
    public void updateCherry(Cherry c) {
        c.destroy();
    }

    @Override
    public void updateSuperPacGumEaten(SuperPacGum spg) {
        spg.destroy(); //La SuperPacGum est détruite quand Pacman la mange
        for (Ghost gh : ghosts) {
            gh.superPacGumEaten();
        }
    }

    @Override
    public void updateMonsterPacGumEaten(MonsterPacGum monsterPacGum) {
        monsterPacGum.destroy();
        Game.getPacman().switchMode(new MonsterMode());
    }

    @Override
    public void updateGhostCollision(Ghost gh) {
        if (pacman.isGodMode()) {
            return;
        }

        if (gh.isState(State.FRIGHTENED) || pacman.isMonsterMode()) {
            gh.eaten();
        } else if (!gh.isState(State.EATEN)) {
            //마지막 생명이 아니면 > 생명 깎고 갓모드 전환
            if (!pacman.isLastLife()) {
                pacman.decreaseLife();
                pacman.switchMode(new GodMode());
                return;
            }

            // 이미 게임 오버 처리 중이면 리턴
            if (Game.isGameOver) {
                return;
            }

            Game.isGameOver = true;

            // 게임 오버 화면 표시 및 재시작 처리
            invokeLater(() -> GameLauncher.showGameOver(GameLauncher.getUIPanel().getScore()));
        }
    }

    private static void resetGame() {
        isGameOver = false;
        firstInput = false;
        objects = new ArrayList();
        ghosts = new ArrayList();
        walls = new ArrayList();
        pacman = null;
        blinky = null;
        crashCnt = 0;
        objectCnt = 0;
    }

    public static void setFirstInput(boolean b) {
        firstInput = b;
    }

    public static boolean getFirstInput() {
        return firstInput;
    }
}
