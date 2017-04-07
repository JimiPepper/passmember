package rose.project.passmember.gui.modal;

import rose.project.passmember.util.entry.Entry;
import javax.swing.*;

/**
 * Created by Lord Rose on 30/03/2017.
 */
abstract class EntryInputPanel extends JPanel {
    protected abstract boolean isFilled();
    public abstract Entry getEntry() throws UnsupportedOperationException;
}
