package game;

import game.utils.KeyHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameplayPanel extends JPanel implements Runnable {
    public static int width;
    public static int height;
    private Thread thread;
    private boolean running = false;

    private BufferedImage img;
    private Graphics2D g;
    private Image backgroundImage;
    private Image scaledBackground; // 미리 스케일링된 배경

    private KeyHandler key;
    private Game game;

    public GameplayPanel(int width, int height) throws IOException {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
        setDoubleBuffered(true); // 이중 버퍼링 활성화

        // 배경 이미지 로드 및 미리 스케일링
        backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("img/background.png"));
        scaledBackground = backgroundImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    @Override
    public void addNotify() {
        super.addNotify();

        if (thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    public void init() {
        running = true;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();

        // 렌더링 품질 설정 최적화
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        key = new KeyHandler(this);
        game = new Game();
    }

    public void update() {
        game.update();
    }

    public void input(KeyHandler key) {
        game.input(key);
    }

    public void render() {
        if (g != null) {
            // 미리 스케일링된 배경 사용
            g.drawImage(scaledBackground, 0, 0, null);
            game.render(g);
        }
    }

    // paintComponent를 오버라이드하여 Swing의 이중 버퍼링 활용
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (img != null) {
            graphics.drawImage(img, 0, 0, null);
        }
    }

    @Override
    public void run() {
        init();

        final double GAME_HERTZ = 60.0; // 60 FPS로 변경
        final double TBU = 1000000000.0 / GAME_HERTZ;
        final int MUBR = 5;

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime = System.nanoTime();

        final double TARGET_FPS = 60.0; // 60 FPS로 변경
        final double TTBR = 1000000000.0 / TARGET_FPS;

        int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);

        while (running) {
            double now = System.nanoTime();
            int updateCount = 0;

            // 업데이트 루프
            while ((now - lastUpdateTime) > TBU && (updateCount < MUBR)) {
                input(key);
                update();
                lastUpdateTime += TBU;
                updateCount++;
            }

            if (now - lastUpdateTime > TBU) {
                lastUpdateTime = now - TBU;
            }

            // 렌더링
            if (now - lastRenderTime >= TTBR) {
                render();
                repaint(); // paintComponent 호출
                lastRenderTime = now;
                frameCount++;
            }

            // FPS 카운터
            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime) {
                System.out.println("FPS: " + frameCount);
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            // CPU 사용률 감소를 위한 대기
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopGame() {
        running = false;
    }
}
