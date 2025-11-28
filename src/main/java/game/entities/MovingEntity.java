package game.entities;

import game.GameplayPanel;
import game.entities.direction.Speeds;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//이동하는 엔티티를 설명하는 추상 클래스
public abstract class MovingEntity extends Entity {
    protected int spd;
    protected int xSpd = 0;
    protected int ySpd = 0;
    protected BufferedImage sprite;
    protected float subimage = 0;
    protected int nbSubimagesPerCycle;
    protected int direction = 0;
    protected float imageSpd = 0.2f;

    public MovingEntity(int size, int xPos, int yPos, int spd, String spriteName, int nbSubimagesPerCycle, float imageSpd) {
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


    //TODO 리패터링
    public void updatePosition() {
        //Mise à jour de la position de l'entité
        //엔티티의 위치 업데이트
        if (!(xSpd == 0 && ySpd == 0)) { //Si la vitesse horizontale ou la vitesse verticale n'est pas nulle, on incrémente la position horizontale et verticale en conséquence
            xPos+=xSpd;
            yPos+=ySpd;

            //방향에 따라 방향 값이 변경됩니다(이미지의 어느 부분을 표시할지 등을 나타내는 정수)

            if (xSpd > 0) {
                direction = 0;
            } else if (xSpd < 0) {
                direction = 1;
            } else if (ySpd < 0) {
                direction = 2;
            } else if (ySpd > 0) {
                direction = 3;
            }

            //On incrémente la valeur de l'image courante de l'animation à afficher (la vitesse peut varier), et selon le nombre d'images de l'animation, la valeur fait une boucle
            //표시될 애니메이션의 현재 프레임 값을 증가시킵니다(속도는 다를 수 있음). 애니메이션의 프레임 수에 따라 값은 자체적으로 다시 루프됩니다.
            subimage += imageSpd;
            if (subimage >= nbSubimagesPerCycle) {
                subimage = 0;
            }
        }

        //Si l'entité va au dela des bords de la zone de jeu, elle passe de l'autre côté
        //엔티티가 게임 영역의 가장자리를 넘어가면 반대쪽으로 이동합니다.
        if (xPos > GameplayPanel.width) {
            xPos = 0 - size + spd;
        }

        if (xPos < 0 - size + spd) {
            xPos = GameplayPanel.width;
        }

        if (yPos > GameplayPanel.height) {
            yPos = 0 - size + spd;
        }

        if (yPos < 0 - size + spd) {
            yPos = GameplayPanel.height;
        }
    }

    @Override
    public void render(Graphics2D g) {
        //Par défaut, on considère que chaque "sprite" contient 4 variations de l'animation correspondant à une direction et chaque animation a un certain nombre d'images
        //En sachant cela, on affiche seulement la partie de l'image du sprite correspondant à la bonne direction et à la bonne frame de l'animation

        //기본적으로 각 "스프라이트"는 방향에 해당하는 애니메이션의 4가지 변형을 포함하는 것으로 간주되며 각 애니메이션에는 특정 수의 프레임이 있습니다.
        //이를 알고 있으므로 애니메이션의 올바른 방향과 프레임에 해당하는 스프라이트 이미지의 일부만 표시합니다.
        g.drawImage(sprite.getSubimage((int)subimage * size + direction * size * nbSubimagesPerCycle, 0, size, size), this.xPos, this.yPos,null);
    }

    //Méthode pour savoir si l'entité est bien positionnée sur une case de la grille de la zone de jeu ou non
    //엔티티가 게임 영역의 그리드 사각형에 올바르게 배치되었는지 여부를 확인하는 메서드
    public boolean onTheGrid() {
        return (xPos%8 == 0 && yPos%8 == 0);
    }

    //Méthode pour savoir si l'entité est dans la zone de jeu ou non
    //엔티티가 게임 영역에 있는지 여부를 판별하는 메서드
    public boolean onGameplayWindow() { return !(xPos<=0 || xPos>= GameplayPanel.width || yPos<=0 || yPos>= GameplayPanel.height); }

    public Rectangle getHitbox() {
        return new Rectangle(xPos, yPos, size, size);
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

    public int getxSpd() {
        return xSpd;
    }

    public void updateSpeed(Speeds speeds) {
        if (speeds.isStopped()) {
            return;
        }

        if (!speeds.isDiagonal()) {
            this.xSpd = speeds.getxSpeed();
            this.ySpd = speeds.getySpeed();
        } else if (speeds.getxSpeed() != 0) {
            this.xSpd = 0;
            this.ySpd = speeds.getySpeed();
        } else {
            this.xSpd = speeds.getxSpeed();
            this.ySpd = 0;
        }
    }

    public void setxSpd(int xSpd) {
        this.xSpd = xSpd;
    }

    public int getySpd() {
        return ySpd;
    }

    public void setySpd(int ySpd) {
        this.ySpd = ySpd;
    }

    public int getSpd() {
        return spd;
    }
}
