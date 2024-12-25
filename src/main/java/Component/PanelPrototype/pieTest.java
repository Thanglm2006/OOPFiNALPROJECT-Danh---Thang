package Component.PanelPrototype;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx. scene. Scene;
import javax.swing.*;
import java.awt.*;

public class pieTest extends JFrame {
    public pieTest() throws HeadlessException {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFXPanel fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);
        Platform.runLater(() -> {
            PieChart pieChart = new PieChart();
            pieChart.getData().add(new PieChart.Data("Java", 40));
            pieChart.getData().add(new PieChart.Data("C++", 30));
            pieChart.getData().add(new PieChart.Data("Python", 20));
            pieChart.getData().add(new PieChart.Data("C#", 10));
            pieChart.getData().forEach(data ->
                    data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), " ", String.format("%.2f%%", (data.getPieValue() / pieChart.getData().stream().mapToDouble(PieChart.Data::getPieValue).sum()) * 100)
                            )
                    )
            );
            pieChart.setLabelsVisible(true);
            Scene scene = new Scene(new Group(pieChart));
            fxPanel.setScene(scene);
        });
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new pieTest();
        });
    }
}
