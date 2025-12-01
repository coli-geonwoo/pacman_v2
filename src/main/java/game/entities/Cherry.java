package game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Cherry extends StaticEntity {

    private BufferedImage sprite;

    public Cherry(int xPos, int yPos) {
        super(24, xPos, yPos);
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("img/cherry.png"));
        } catch (IOException e) {
            throw new RuntimeException("체리 이미지 초기화 과정에서 문제가 생겼습니다");
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(sprite, getxPos(), getyPos(), size, size, null);
    }
}
