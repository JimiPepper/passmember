package rose.project.passmember.gui.modal;

import rose.project.passmember.util.entry.Entry;
import javax.swing.*;

/**
 * Created by Lord Rose on 30/03/2017.
 */
abstract class EntryInputPanel extends JPanel {
    public abstract boolean isFilled();
    public abstract String getErrorMessage() throws IllegalStateException; // TODO : Implements my own fields to get and list errors for each fields
    public abstract Entry getEntry() throws UnsupportedOperationException;
    public abstract void populate(Entry entry) throws IllegalArgumentException;
}
