package game.entities;

import java.awt.*;

//SuperPacGums 클래스
public class SuperPacGum extends StaticEntity {
    private int frameCount = 0;

    public SuperPacGum(int xPos, int yPos) {
        super(16, xPos, yPos);
    }

    @Override
    public void render(Graphics2D g) {
        //SuperPacGums가 깜빡이도록 하려면 60프레임 중 30프레임만 렌더링하면 됩니다.
        if (frameCount%60 < 30) {
            g.setColor(new Color(255, 183, 174));
            g.fillOval(this.xPos, this.yPos, this.size, this.size);
        }
    }

    @Override
    public void update() {
        frameCount++;
    }
}
