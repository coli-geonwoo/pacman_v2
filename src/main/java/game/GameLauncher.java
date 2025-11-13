package game;

import javax.swing.*;
import java.io.IOException;

//Point d'entrée de l'application
//애플리케이션 진입점
public class GameLauncher {
    private static UIPanel uiPanel;

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("Pacman");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gameWindow = new JPanel();

        //Création de la "zone de jeu"
        //"플레이 영역" 생성
        try {
            gameWindow.add(new GameplayPanel(448,496));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Création de l'UI (pour afficher le score)
        //UI 생성(점수 표시용)
        uiPanel = new UIPanel(256,496);
        gameWindow.add(uiPanel);

        window.setContentPane(gameWindow);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static UIPanel getUIPanel() {
        return uiPanel;
    }
}
