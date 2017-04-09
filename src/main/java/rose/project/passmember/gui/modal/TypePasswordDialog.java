package rose.project.passmember.gui.modal;

import rose.project.passmember.gui.ErrorPanel;
import rose.project.passmember.gui.GUI;
import rose.project.passmember.util.EntryType;
import rose.project.passmember.util.entry.Entry;
import rose.project.passmember.util.entry.PasswordEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

        // JDialog do not support KeyListener, use KeyBinding instead
        InputMap inputMap = ((JPanel) this.getContentPane()).getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = ((JPanel) this.getContentPane()).getActionMap();

        String enter = "enter";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enter);
        actionMap.put(enter, new SubmitAction(this, this.inputPanel));

        String escape = "escape";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), escape);
        actionMap.put(escape, new CancelAction(this));
    }

    public TypePasswordDialog(GUI gui, Entry defaultEntry) {
        this(
                gui,
                (defaultEntry instanceof PasswordEntry) ? EntryType.SIMPLE_PASSWORD : EntryType.FOLDER
        ); // not the most beautiful way to call the TypePasswordDialog's constructor

        this.inputPanel.populate(defaultEntry);
    }

    public Entry getEntry() {
        return this.inputPanel.getEntry();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.cancelButton)) {
            this.setVisible(false);
            this.dispose();
        }
        else if(e.getSource().equals(this.okButton)) {
            /* TEST IF THE FORM IS WELL FILLED */
            if(this.inputPanel.isFilled()) {
                this.setVisible(false);
                this.dispose();
            }
            else {
                this.getContentPane().add(new ErrorPanel(this.inputPanel.getErrorMessage()), BorderLayout.NORTH);
                this.pack(); // display and compute the new look'n feel
            }
        }
    }

     private static class SubmitAction extends AbstractAction {
        private TypePasswordDialog dialog;
        private EntryInputPanel form;

        public SubmitAction(TypePasswordDialog dialog, EntryInputPanel form) {
            this.dialog = dialog;
            this.form = form;
        }

         public void actionPerformed(ActionEvent e) {
            if (this.isEnabled()) {
                /* TEST IF THE FORM IS WELL FILLED */
                if(this.form.isFilled()) {
                    this.dialog.setVisible(false);
                    this.dialog.dispose();
                }
                else {
                    this.dialog.getContentPane().add(new ErrorPanel(this.form.getErrorMessage()), BorderLayout.NORTH);
                    this.dialog.pack(); // display and compute the new look'n feel
                }
            }
        }
    }

    private static class CancelAction extends AbstractAction {
        private TypePasswordDialog dialog;

        public CancelAction(TypePasswordDialog dialog) {
            this.dialog = dialog;
        }

        public void actionPerformed(ActionEvent e) {
            if (this.isEnabled()) {
                this.dialog.setVisible(false);
                this.dialog.dispose();
            }
        }
    }
}
