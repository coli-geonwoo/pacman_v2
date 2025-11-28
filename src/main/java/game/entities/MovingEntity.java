package game.entities;

import game.GameplayPanel;
import game.entities.direction.Speeds;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//이동하는 엔티티를 설명하는 추상 클래스
public abstract class MovingEntity extends Entity {
    protected int spd;
    protected Speeds speeds = new Speeds(0, 0);
    protected BufferedImage sprite;
    protected float subimage = 0;
    protected int nbSubimagesPerCycle;
    protected int direction = 0;
    protected float imageSpd = 0.2f;

    public MovingEntity(int size, int xPos, int yPos, int spd, String spriteName, int nbSubimagesPerCycle,
                        float imageSpd) {
        super(size, xPos, yPos);
        this.spd = spd;
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("img/" + spriteName));
            this.nbSubimagesPerCycle = nbSubimagesPerCycle;
            this.imageSpd = imageSpd;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        updatePosition();
    }


    public void updatePosition() {
        //엔티티의 위치 업데이트
        if (!speeds.isStopped()) {
            position.moveX(speeds.getxSpeed(), size, spd);
            position.moveY(speeds.getySpeed(), size, spd);

            //방향에 따라 방향 값이 변경됩니다(이미지의 어느 부분을 표시할지 등을 나타내는 정수)
            direction = speeds.getDirection();

            //표시될 애니메이션의 현재 프레임 값을 증가시킵니다(속도는 다를 수 있음). 애니메이션의 프레임 수에 따라 값은 자체적으로 다시 루프됩니다.
            subimage += imageSpd;
            if (subimage >= nbSubimagesPerCycle) {
                subimage = 0;
            }
            return;
        }

        position.moveX(0, size, spd);
        position.moveY(0, size, spd);
    }

    @Override
    public void render(Graphics2D g) {
        //기본적으로 각 "스프라이트"는 방향에 해당하는 애니메이션의 4가지 변형을 포함하는 것으로 간주되며 각 애니메이션에는 특정 수의 프레임이 있습니다.
        //이를 알고 있으므로 애니메이션의 올바른 방향과 프레임에 해당하는 스프라이트 이미지의 일부만 표시합니다.
        g.drawImage(sprite.getSubimage((int) subimage * size + direction * size * nbSubimagesPerCycle, 0, size, size),
                getxPos(), getyPos(), null);
    }

    //엔티티가 게임 영역의 그리드 사각형에 올바르게 배치되었는지 여부를 확인하는 메서드
    public boolean onTheGrid() {
        return position.onTheGrid();
    }

    //엔티티가 게임 영역에 있는지 여부를 판별하는 메서드
    public boolean onGameplayWindow() {
        return position.onGameplayWindow();
    }

    public Rectangle getHitbox() {
        return new Rectangle(getxPos(), getyPos(), size, size);
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public void setSprite(String spriteName) {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("img/" + spriteName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getSubimage() {
        return subimage;
    }

    public void setSubimage(float subimage) {
        this.subimage = subimage;
    }

    public int getNbSubimagesPerCycle() {
        return nbSubimagesPerCycle;
    }

    public void setNbSubimagesPerCycle(int nbSubimagesPerCycle) {
        this.nbSubimagesPerCycle = nbSubimagesPerCycle;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void updateSpeed(Speeds speeds) {
        if (speeds.isStopped()) {
            return;
        }

        if (!speeds.isDiagonal()) {
            this.speeds = speeds;
        } else if (speeds.getxSpeed() != 0) {
            this.speeds = new Speeds(0, speeds.getxSpeed());
        } else {
            this.speeds = new Speeds(speeds.getxSpeed(), 0);
        }
    }

    public int getxSpd() {
        return speeds.getxSpeed();
    }

    public int getySpd() {
        return speeds.getySpeed();
    }

    public int getSpd() {
        return spd;
    }
}
