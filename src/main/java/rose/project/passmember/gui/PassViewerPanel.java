package rose.project.passmember.gui;

import rose.project.passmember.util.entry.Entry;
import rose.project.passmember.util.entry.FolderEntry;
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
    private Font labelFont;

    public PassViewerPanel() {
        this.labelFont = PassViewerPanel.retrieveFont();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void loadSavedPassword(Entry loadedEntry) {
        this.cleanUI();

        if(loadedEntry instanceof PasswordEntry) {
            PasswordEntry savedPassword = (PasswordEntry)loadedEntry;
            JLabel labelLogin = new JLabel();
            JLabel labelPassword = new JLabel();

            labelLogin.setFont(this.labelFont);
            labelPassword.setFont(this.labelFont);
            labelLogin.setText("Login : " + savedPassword.login);
            labelPassword.setText("mot de passe : " + savedPassword.password);

            this.add(labelLogin);
            this.add(labelPassword);
        }
        else {
            FolderEntry savedFolder = (FolderEntry)loadedEntry;
            JLabel labelTitle = new JLabel();
            JLabel labelDescription = new JLabel();

            labelTitle.setFont(this.labelFont);
            labelDescription.setFont(this.labelFont);
            labelTitle.setText("Name : " + savedFolder.title);
            labelDescription.setText("Description : " + savedFolder.description);

            this.add(labelTitle);
            this.add(labelDescription);
        }

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
        for(Component component : this.getComponents()) {
            if(component instanceof JLabel) {
                this.remove(component);
            }
        }
    }

    private static Font retrieveFont() {
        Font labelFont = UIManager.getDefaults().getFont("Label.font");

        Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        attributes.put(TextAttribute.SIZE, 18);

        return labelFont.deriveFont(attributes);
    }
}
