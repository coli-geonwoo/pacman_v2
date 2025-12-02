package game;

import javax.swing.*;
import java.io.IOException;

//애플리케이션 진입점
public class GameLauncher {
    private static UIPanel uiPanel;
    private static String[] selectedGhosts;

    public static void main(String[] args) {
        // 먼저 유령 선택 창 표시
        JFrame tempFrame = new JFrame();
        GhostSelectionDialog dialog = new GhostSelectionDialog(tempFrame);
        dialog.setVisible(true);

        // 사용자가 취소하면 프로그램 종료
        if (!dialog.isConfirmed()) {
            System.exit(0);
            return;
        }

        // 선택된 유령들 저장
        selectedGhosts = dialog.getSelectedGhosts();
        tempFrame.dispose();

        JFrame window = new JFrame();
        window.setTitle("Pacman");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gameWindow = new JPanel();

        //"플레이 영역" 생성
        try {
            gameWindow.add(new GameplayPanel(448,496));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public static String[] getSelectedGhosts() {
        return selectedGhosts;
    }
}
