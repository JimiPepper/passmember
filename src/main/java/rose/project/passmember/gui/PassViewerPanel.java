package rose.project.passmember.gui;

import rose.project.passmember.util.entry.PasswordEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lord Rose on 17/03/2017.
 */
public class PassViewerPanel extends JPanel {
    private JLabel labelLogin;
    private JLabel labelPassword;

    public PassViewerPanel() {
        Font labelFont = PassViewerPanel.retrieveFont();
        this.labelLogin = new JLabel();
        this.labelPassword = new JLabel();

        this.labelLogin.setFont(labelFont);
        this.labelPassword.setFont(labelFont);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(this.labelLogin);
        this.add(this.labelPassword);
    }

    public void loadSavedPassword(PasswordEntry savedPassword) {
        this.labelLogin.setText("Login : "+ savedPassword.login);
        this.labelPassword.setText("mot de passe : "+ savedPassword.password);

        final PassViewerPanel me = this;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                me.revalidate();
                me.repaint();
            }
        });
    }

    public void cleanUI() {
        this.labelLogin.setText("");
        this.labelPassword.setText("");
    }

    private static Font retrieveFont() {
        Font labelFont = UIManager.getDefaults().getFont("Label.font");

        Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        attributes.put(TextAttribute.SIZE, 18);

        return labelFont.deriveFont(attributes);
    }
}
