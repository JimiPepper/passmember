package rose.project.passmember.gui.modal;

import rose.project.passmember.util.entry.PasswordEntry;
import javax.swing.*;

/**
 * Created by Lord Rose on 30/03/2017.
 */
abstract class PasswordRetrieverPanel extends JPanel {
    protected abstract boolean isFilled();
    public abstract PasswordEntry getPassword() throws UnsupportedOperationException;
}
