package game.entities;

import java.awt.*;

//팩껌 클래스
public class PacGum extends StaticEntity {
    public PacGum(int xPos, int yPos) {
        super(4, xPos + 8, yPos + 8);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(255, 183, 174));
        g.fillRect(getxPos(), getyPos(), size, size);
    }
}
