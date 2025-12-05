package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GhostSelectionDialog extends JDialog {
    private String[] selectedGhosts = new String[4];
    private boolean confirmed = false;
    private int lives = 3;
    private boolean beginnerMode = false;

    private JSpinner livesSpinner;
    private JComboBox<String>[] ghostComboBoxes;
    private JCheckBox beginnerCheckBox;

    private static final String[] GHOST_TYPES = {"Blinky", "Pinky", "Inky", "Clyde"};
    private static final String[] GHOST_TYPES_FIRST = {"Blinky", "Pinky", "Clyde"};
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);

    public GhostSelectionDialog(JFrame parent) {
        super(parent, "유령 선택 - Ghost Selection", true);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = createMainPanel();
        JPanel buttonPanel = createButtonPanel();

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ghostComboBoxes = new JComboBox[4];

        // 첫 번째 유령 (Inky 제외)
        addGhostSelector(panel, 0, GHOST_TYPES_FIRST, 0);

        // 나머지 유령들 (모든 타입 가능)
        for (int i = 1; i < 4; i++) {
            addGhostSelector(panel, i, GHOST_TYPES, i);
        }

        // 설명 라벨
        addInfoLabel(panel);

        // 생명 개수 선택
        addLivesSelector(panel);

        // BEGINNER 모드 체크박스
        addBeginnerModeCheckbox(panel);

        return panel;
    }

    private void addGhostSelector(JPanel panel, int index, String[] types, int defaultIndex) {
        JLabel label = new JLabel((index + 1) + "번째 유령 (Ghost " + (index + 1) + "):");
        label.setFont(LABEL_FONT);

        ghostComboBoxes[index] = new JComboBox<>(types);
        ghostComboBoxes[index].setFont(NORMAL_FONT);
        ghostComboBoxes[index].setSelectedIndex(defaultIndex);

        panel.add(label);
        panel.add(ghostComboBoxes[index]);
    }

    private void addInfoLabel(JPanel panel) {
        JLabel infoLabel = new JLabel("<html><center>중복 선택 가능합니다<br>Duplicates allowed</center></html>");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(new JLabel()); // 빈 공간
        panel.add(infoLabel);
    }

    private void addLivesSelector(JPanel panel) {
        JLabel livesLabel = new JLabel("팩맨 생명 개수:");
        livesLabel.setFont(LABEL_FONT);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(3, 1, 10, 1);
        livesSpinner = new JSpinner(spinnerModel);
        livesSpinner.setFont(NORMAL_FONT);
        ((JSpinner.DefaultEditor) livesSpinner.getEditor()).getTextField().setFont(NORMAL_FONT);

        panel.add(livesLabel);
        panel.add(livesSpinner);
    }

    private void addBeginnerModeCheckbox(JPanel panel) {
        JLabel beginnerLabel = new JLabel("초보자 모드 (Beginner):");
        beginnerLabel.setFont(LABEL_FONT);

        beginnerCheckBox = new JCheckBox("<html>유령 Scatter Mode</html>");
        beginnerCheckBox.setFont(new Font("Arial", Font.PLAIN, 12));

        panel.add(beginnerLabel);
        panel.add(beginnerCheckBox);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton startButton = new JButton("게임 시작 (Start Game)");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setPreferredSize(new Dimension(250, 40));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSelections();
                confirmed = true;
                dispose();
            }
        });

        panel.add(startButton);
        return panel;
    }

    private void saveSelections() {
        for (int i = 0; i < 4; i++) {
            selectedGhosts[i] = ((String) ghostComboBoxes[i].getSelectedItem()).toLowerCase();
        }
        lives = (Integer) livesSpinner.getValue();
        beginnerMode = beginnerCheckBox.isSelected();
    }

    public String[] getSelectedGhosts() {
        return selectedGhosts;
    }

    public int getLives() {
        return lives;
    }

    public boolean isBeginnerMode() {
        return beginnerMode;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
