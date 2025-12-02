package game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameOverScreen extends JDialog {
    private int finalScore;
    private boolean restart = false;
    private static List<ScoreEntry> rankings = new ArrayList<>();

    public GameOverScreen(JFrame parent, int score) {
        super(parent, "게임 종료 - Game Over", true);
        this.finalScore = score;

        setLayout(new BorderLayout(10, 10));
        setSize(500, 600);

        // 상단 패널 - 게임 오버 메시지
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scoreLabel = new JLabel("최종 점수 (Final Score): " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(gameOverLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(scoreLabel);

        // 이름 입력 패널
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        namePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel nameLabel = new JLabel("이름 (Name):");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JTextField nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton saveButton = new JButton("저장 (Save)");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));

        namePanel.add(nameLabel);
        namePanel.add(nameField);
        namePanel.add(saveButton);

        // 중간 패널 - 랭킹 테이블
        JPanel rankingPanel = new JPanel(new BorderLayout());
        rankingPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel rankingTitle = new JLabel("랭킹 (Rankings)", SwingConstants.CENTER);
        rankingTitle.setFont(new Font("Arial", Font.BOLD, 20));

        String[] columnNames = {"순위", "이름", "점수"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable rankingTable = new JTable(tableModel);
        rankingTable.setFont(new Font("Arial", Font.PLAIN, 14));
        rankingTable.setRowHeight(25);
        rankingTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(rankingTable);
        scrollPane.setPreferredSize(new Dimension(450, 300));

        rankingPanel.add(rankingTitle, BorderLayout.NORTH);
        rankingPanel.add(scrollPane, BorderLayout.CENTER);

        // 하단 패널 - 버튼들
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton restartButton = new JButton("다시 시작 (Restart)");
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.setPreferredSize(new Dimension(180, 40));

        JButton exitButton = new JButton("종료 (Exit)");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setPreferredSize(new Dimension(180, 40));

        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);

        // 이벤트 리스너
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(GameOverScreen.this,
                            "이름을 입력해주세요!\nPlease enter your name!",
                            "입력 오류",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // 랭킹에 추가
                rankings.add(new ScoreEntry(name, finalScore));
                Collections.sort(rankings);

                // 테이블 업데이트
                updateRankingTable(tableModel);

                nameField.setEnabled(false);
                saveButton.setEnabled(false);

                JOptionPane.showMessageDialog(GameOverScreen.this,
                        "점수가 저장되었습니다!\nScore saved!",
                        "저장 완료",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart = true;
                dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart = false;
                dispose();
                System.exit(0);
            }
        });

        // 초기 랭킹 테이블 업데이트
        updateRankingTable(tableModel);

        // 패널 추가
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(namePanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.NORTH);
        add(rankingPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void updateRankingTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);

        int rank = 1;
        for (ScoreEntry entry : rankings) {
            tableModel.addRow(new Object[]{rank++, entry.getName(), entry.getScore()});
            if (rank > 10) break; // 상위 10개만 표시
        }
    }

    public boolean isRestart() {
        return restart;
    }

    // 점수 엔트리 클래스
    static class ScoreEntry implements Comparable<ScoreEntry> {
        private String name;
        private int score;

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(ScoreEntry other) {
            return Integer.compare(other.score, this.score); // 내림차순
        }
    }
}
