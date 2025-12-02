package game;

import game.entities.Cherry;
import game.entities.GameLevel;
import game.entities.MonsterPacGum;
import game.entities.PacGum;
import game.entities.SuperPacGum;
import game.entities.ghosts.Ghost;

import game.ghostStates.State;
import javax.swing.*;
import java.awt.*;

public class UIPanel extends JPanel implements Observer {
    public static int width;
    public static int height;

    private int score = 0;
    private int pacManLifes = 0;
    private GameLevel gameLevel = GameLevel.LEVEL1;
    private JLabel scoreLabel;
    private JLabel lifeLabel;
    private JLabel levelLabel;

    public UIPanel(int width, int height, int pacManLifes) {
        this.width = width;
        this.height = height;
        this.pacManLifes = pacManLifes;
        setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        scoreLabel = new JLabel("  Score: " + score);
        levelLabel = new JLabel("  Level: " + gameLevel.name());
        lifeLabel = new JLabel("  Life: " + pacManLifes);
        setFontAndForeGround(levelLabel);
        setFontAndForeGround(scoreLabel);
        setFontAndForeGround(lifeLabel);
    }

    private void setFontAndForeGround(JLabel label) {
        label.setFont(label.getFont().deriveFont(20.0F));
        label.setForeground(Color.white);
        this.add(label, BorderLayout.WEST);
    }

    public void updateScore(int incrScore) {
        this.score += incrScore;
        this.scoreLabel.setText("Score: " + score);
        this.gameLevel = GameLevel.mapWithScore(score);
        this.levelLabel.setText("Level: " + gameLevel.name());
    }

    public int getScore() {
        return score;
    }

    //팩맨이 팩덤, 파워 팩덤 또는 유령과 접촉하면 인터페이스에 알림이 전송되고, 표시되는 점수도 그에 따라 업데이트됩니다.
    @Override
    public void updatePacGumEaten(PacGum pg) {
        updateScore(10);
    }

    @Override
    public void updateCherry(Cherry c) {
        updateScore(200);;
    }

    @Override
    public void updateSuperPacGumEaten(SuperPacGum spg) {
        updateScore(100);
    }

    @Override
    public void updateGhostCollision(Ghost gh) {
        if (!Game.getPacman().isGodMode() && gh.isState(State.FRIGHTENED)) {
            updateScore(500);
        }
        this.pacManLifes = Game.getPacman().getLife();
        this.lifeLabel.setText("Life: " + this.pacManLifes);
    }

    @Override
    public void updateMonsterPacGumEaten(MonsterPacGum monster) {

    }

    public void resetScore() {
        score = 0;
        repaint();
    }
}
