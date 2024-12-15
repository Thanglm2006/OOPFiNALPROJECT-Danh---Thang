package dashboard.swing.scrollbar;

import javax.swing.*;
import java.awt.*;

public class ScrollBarCustom extends JScrollBar {

//    Tùy chỉnh kích thước
    public ScrollBarCustom() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(5, 5)); // Kích thước mặc định
        setForeground(new Color(94, 139, 231));
        setUnitIncrement(20);
        setOpaque(false);
    }
}
