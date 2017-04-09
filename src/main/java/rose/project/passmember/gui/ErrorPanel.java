package rose.project.passmember.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by Lord Rose on 09/04/2017.
 */
public class ErrorPanel extends JPanel {
    private static Color BACKGROUND_COLOR = new Color(247, 183, 183);
    private static Color FOREGROUND_COLOR = new Color(112, 30, 30);
    private String errorMessage;

    public ErrorPanel(String errorMessage) {
        this.errorMessage = errorMessage;

        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        this.setLayout(layout);

        JLabel messageLabel = new JLabel(this.errorMessage);
        this.setBackground(BACKGROUND_COLOR);
        messageLabel.setForeground(FOREGROUND_COLOR);
        URL iconURL = getClass().getResource("/icons/warning.png");
        ImageIcon icon = new ImageIcon(iconURL);
        Image newimg = icon.getImage().getScaledInstance((int)(icon.getIconHeight() * 0.05), (int)(icon.getIconWidth() * 0.05), java.awt.Image.SCALE_SMOOTH);
        messageLabel.setIcon(new ImageIcon(newimg));

        this.add(messageLabel);
    }
}
