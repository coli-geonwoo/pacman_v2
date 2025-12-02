package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class MonsterPacGum extends StaticEntity {

    private int frameCount = 0;

    public MonsterPacGum(int x, int y) {
        super(16, x, y);
    }

    @Override
    public void render(Graphics2D g) {
        //MonsterPacGum이 깜빡이도록 하려면 60프레임 중 30프레임만 렌더링하면 됩니다.
        if (frameCount%60 < 30) {
            g.setColor(new Color(0, 255, 0));
            g.fillOval(getxPos(), getyPos(), this.size, this.size);
        }
    }

    @Override
    public void update() {
        frameCount++;
    }
}
