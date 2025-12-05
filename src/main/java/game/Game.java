package game;

import static javax.swing.SwingUtilities.invokeLater;

import game.entities.Cherry;
import game.entities.Entity;
import game.entities.MonsterPacGum;
import game.entities.PacGum;
import game.entities.Pacman;
import game.entities.Position;
import game.entities.SuperPacGum;
import game.entities.Wall;
import game.entities.ghosts.Ghost;
import game.entities.pacmanStates.GodMode;
import game.entities.pacmanStates.MonsterMode;
import game.ghostStates.State;
import game.utils.CollisionDetector;
import game.utils.KeyHandler;
import game.utils.reader.GameInfoReadResponse;
import game.utils.reader.GameInfoReader;
import java.awt.Graphics2D;
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
    private static Ghost target;
    private static boolean isGameOver = false;
    private static boolean firstInput = false;

    public Game(GameInfoReader gameInfoReader) {
        //게임 초기화
        resetGame();
        GameInfoReadResponse gameInfoReadResponse = gameInfoReader.readGameInfo(GameLauncher.getSelectedGhosts(),
                GameLauncher.getLives());
        pacman = gameInfoReadResponse.pacman();
        ghosts = gameInfoReadResponse.ghosts();
        walls = gameInfoReadResponse.walls();
        objects = gameInfoReadResponse.entities();
        objectCnt = gameInfoReadResponse.objectCnt();
        if (gameInfoReadResponse.target().isPresent()) {
            target = gameInfoReadResponse.target().get();
        }
        CollisionDetector collisionDetector = new CollisionDetector(this);
        pacman.setCollisionDetector(collisionDetector);
        pacman.registerObserver(GameLauncher.getUIPanel());
        pacman.registerObserver(this);
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
        Game.getPacman().switchMode(new MonsterMode(0));
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
                pacman.switchMode(new GodMode(0));
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
        target = null;
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
