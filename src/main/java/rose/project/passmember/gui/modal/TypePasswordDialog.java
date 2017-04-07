package rose.project.passmember.gui.modal;

import rose.project.passmember.gui.GUI;
import rose.project.passmember.util.EntryType;
import rose.project.passmember.util.entry.Entry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lord Rose on 17/03/2017.
 */
public class TypePasswordDialog extends JDialog implements ActionListener {
    private JButton cancelButton;
    private JButton okButton;
    private EntryInputPanel inputPanel;
    private final String TITLE = "Veuillez saisir une nouvelle entrée";

    public TypePasswordDialog(GUI gui, EntryType type) {
        super(gui);
        this.setModal(true);
        this.setTitle(this.TITLE);

        Container rootPanel = this.getContentPane();
        JPanel validationPanel = new JPanel();

        switch (type) {
            case SIMPLE_PASSWORD: this.inputPanel = new SimplePasswordPanel(); break;
            default: this.inputPanel = new FolderPanel();
        }

        validationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        rootPanel.setLayout(new BorderLayout());
        this.cancelButton = new JButton("Annuler");
        this.okButton = new JButton("Valider");

        this.cancelButton.addActionListener(this);
        this.okButton.addActionListener(this);

        validationPanel.add(okButton);
        validationPanel.add(cancelButton);

        rootPanel.add(this.inputPanel, BorderLayout.CENTER);
        rootPanel.add(validationPanel, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.cancelButton)) {
            this.setVisible(false);
            this.dispose();
        }
        else if(e.getSource().equals(this.okButton)) {
            this.setVisible(false);
            this.dispose();
        }
    }

    public Entry getEntry() {
       return this.inputPanel.getEntry();
    }
}
