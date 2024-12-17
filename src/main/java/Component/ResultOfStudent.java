package Component;

import Res.SQLQueries;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultOfStudent extends JPanel {

    public ResultOfStudent(int id, SQLQueries sql) {
        ResultSet res = sql.getSTResult(id);
        JPanel res1 = new JPanel();
        res1.setLayout(new BoxLayout(res1, BoxLayout.Y_AXIS));
        res1.setBackground(Color.WHITE);

        try {
            int i=0;
            while (res.next()) {
                String assignmentName = res.getString("AssignmentName");
                float score = res.getFloat("Score");
                StudentResultPanel resultPanel = new StudentResultPanel(assignmentName, score,(i%2==0)? Color.LIGHT_GRAY:Color.DARK_GRAY);
                resultPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, resultPanel.getPreferredSize().height));
                res1.add(resultPanel);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(res1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    private static class StudentResultPanel extends JPanel {
        private JLabel Name;
        private JLabel Score;

        public StudentResultPanel(String name, float score, Color c) {
            setLayout(new BorderLayout());
            setBackground(c);
            setBorder(new EtchedBorder(Color.CYAN,Color.LIGHT_GRAY));

            Name = new JLabel(name);
            Name.setFont(new Font("Times New Roman", Font.PLAIN, 30));

            Score = new JLabel(String.format("%.2f", score), SwingConstants.RIGHT);
            Score.setFont(new Font("Times New Roman", Font.BOLD, 30));
            Score.setForeground(Color.BLUE);

            add(Name, BorderLayout.WEST);
            add(Score, BorderLayout.EAST);
        }
    }
}