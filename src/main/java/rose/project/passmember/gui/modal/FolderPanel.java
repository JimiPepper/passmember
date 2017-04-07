package rose.project.passmember.gui.modal;

import rose.project.passmember.util.entry.Entry;
import rose.project.passmember.util.entry.FolderEntry;

import javax.swing.*;

/**
 * Created by Lord Rose on 07/04/2017.
 */
public class FolderPanel extends EntryInputPanel {
    private JTextField inputTitle;
    private JTextArea inputDescription;

    public FolderPanel() {
        GroupLayout formLayout = new GroupLayout(this);
        this.setLayout(formLayout);

        JLabel labelTitle = new JLabel("Titre :");
        this.inputTitle = new JTextField(30);

        JLabel labelDescription = new JLabel("Description :");
        this.inputDescription = new JTextArea(15, 15);

        formLayout.setHorizontalGroup(
                formLayout.createParallelGroup()
                        .addGroup(formLayout.createSequentialGroup()
                                .addComponent(labelTitle)
                                .addComponent(this.inputTitle)
                        )
                        .addGroup(formLayout.createSequentialGroup()
                                .addComponent(labelDescription)
                        )
                        .addGroup(
                                formLayout.createSequentialGroup()
                                .addComponent(this.inputDescription)
                        )
        );
        formLayout.setVerticalGroup(
                formLayout.createSequentialGroup()
                        .addGroup(formLayout.createParallelGroup()
                                .addComponent(labelTitle)
                                .addComponent(this.inputTitle)
                        )
                        .addGroup(formLayout.createSequentialGroup()
                            .addComponent(labelDescription)
                        )
                        .addGroup(formLayout.createSequentialGroup()
                                .addComponent(this.inputDescription)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        )
        );

        formLayout.setAutoCreateGaps(true);
        formLayout.setAutoCreateContainerGaps(true);
    }

    @Override
    protected boolean isFilled() {
        return !this.inputTitle.getText().isEmpty();
    }

    @Override
    public Entry getEntry() throws UnsupportedOperationException {

        if(!this.isFilled()) {
            throw new UnsupportedOperationException("Can't retrieve the folder if no title is provided");
        }
        else {
            FolderEntry newFolder = new FolderEntry();
            newFolder.title = this.inputTitle.getText().trim();
            newFolder.description = this.inputDescription.getText().trim();

            return newFolder;
        }
    }
}
