package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GhostSelectionDialog extends JDialog {
    private String[] selectedGhosts = new String[4];
    private boolean confirmed = false;

    private JComboBox<String>[] ghostComboBoxes;

    public GhostSelectionDialog(JFrame parent) {
        super(parent, "유령 선택 - Ghost Selection", true);
        setLayout(new BorderLayout(10, 10));

        // 메인 패널
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 유령 타입 옵션
        String[] ghostTypes = {"Blinky", "Pinky", "Inky", "Clyde"};

        ghostComboBoxes = new JComboBox[4];

        // 각 유령 선택 콤보박스 생성
        for (int i = 0; i < 4; i++) {
            JLabel label = new JLabel((i + 1) + "번째 유령 (Ghost " + (i + 1) + "):");
            label.setFont(new Font("Arial", Font.BOLD, 14));

            ghostComboBoxes[i] = new JComboBox<>(ghostTypes);
            ghostComboBoxes[i].setFont(new Font("Arial", Font.PLAIN, 14));
            ghostComboBoxes[i].setSelectedIndex(i); // 기본값 설정

            mainPanel.add(label);
            mainPanel.add(ghostComboBoxes[i]);
        }

        // 설명 라벨
        JLabel infoLabel = new JLabel("<html><center>중복 선택 가능합니다<br>Duplicates allowed</center></html>");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(new JLabel()); // 빈 공간
        mainPanel.add(infoLabel);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton startButton = new JButton("게임 시작 (Start Game)");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setPreferredSize(new Dimension(250, 40));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 선택된 유령들 저장
                for (int i = 0; i < 4; i++) {
                    selectedGhosts[i] = ((String) ghostComboBoxes[i].getSelectedItem()).toLowerCase();
                }
                confirmed = true;
                dispose();
            }
        });

        buttonPanel.add(startButton);

        // 패널 추가
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // 다이얼로그 설정
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public String[] getSelectedGhosts() {
        return selectedGhosts;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
