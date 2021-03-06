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
    public boolean isFilled() {
        return !this.inputTitle.getText().isEmpty();
    }

    public void populate(Entry entry) throws IllegalArgumentException {
        if(entry == null) {
            throw new IllegalArgumentException("Impossible to populate from a null entry");
        }
        else if(!(entry instanceof FolderEntry)) {
            throw new IllegalArgumentException("Expected a FolderEntry instance");
        }
        else {
            FolderEntry folderEntry = (FolderEntry) entry;
            this.inputTitle.setText(folderEntry.title);
            this.inputDescription.setText(folderEntry.description);
        }
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

    @Override
    public String getErrorMessage() throws IllegalStateException {
        if(!this.isFilled()) {
            return new String("The form needs to be filled with a title");
        }
        else {
            throw new IllegalStateException("The form is well filled, no error message can't be compute");
        }
    }
}
