package rose.project.passmember.gui.modal;

import rose.project.passmember.util.Tools;
import rose.project.passmember.util.TypePassword;
import rose.project.passmember.util.PasswordEntry;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lord Rose on 30/03/2017.
 */
public class SimplePasswordPanel extends PasswordRetrieverPanel implements ActionListener {
    private JTextField inputTitle;
    private JTextField inputLogin;
    private JTextField inputPassword;
    private JButton generatePasswordButton;

    public SimplePasswordPanel() {
        GroupLayout formLayout = new GroupLayout(this);
        this.setLayout(formLayout);

        JLabel labelTitle = new JLabel("Titre :");
        this.inputTitle = new JTextField(30);

        JLabel labelLogin = new JLabel("Login :");
        this.inputLogin = new JTextField(30);

        JLabel labelPassword = new JLabel("Mot de passe :");
        this.inputPassword = new JTextField(30);

        this.generatePasswordButton = new JButton("Générer");
        this.generatePasswordButton.addActionListener(this);

        formLayout.setHorizontalGroup(
                formLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(formLayout.createSequentialGroup()
                                .addComponent(labelTitle)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addComponent(this.inputTitle)
                        )
                        .addGroup(formLayout.createSequentialGroup()
                                .addComponent(labelLogin)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addComponent(this.inputLogin)
                        )
                        .addGroup(formLayout.createSequentialGroup()
                                .addComponent(labelPassword)
                                .addComponent(this.inputPassword)
                                .addComponent(this.generatePasswordButton)
                        )
        );
        formLayout.setVerticalGroup(
                formLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(formLayout.createSequentialGroup()
                                .addComponent(labelTitle)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addComponent(labelLogin)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addComponent(labelPassword)
                        )
                        .addGroup(formLayout.createSequentialGroup()
                                .addComponent(this.inputTitle)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addComponent(this.inputLogin)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addGroup(formLayout.createParallelGroup()
                                        .addComponent(this.inputPassword)
                                        .addComponent(this.generatePasswordButton)
                                )
                        )
        );

        formLayout.setAutoCreateGaps(true);
        formLayout.setAutoCreateContainerGaps(true);
    }

    @Override
    protected boolean isFilled() {
        return !this.inputTitle.getText().isEmpty() && !this.inputLogin.getText().isEmpty() && !this.inputPassword.getText().isEmpty();
    }

    @Override
    public PasswordEntry getPassword() throws UnsupportedOperationException {
        PasswordEntry input = new PasswordEntry();

        if (!this.isFilled()) {
            throw new UnsupportedOperationException("Tous les champs n'ont pas été remplis");
        } else {
            input.title = this.inputTitle.getText().trim();
            input.login = this.inputLogin.getText().trim();
            input.password = this.inputPassword.getText();

            return input;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.generatePasswordButton)) {
            this.inputPassword.setText(Tools.randomPassword(TypePassword.ALL, true, 12));
            // this.repaint();
        }
    }
}
