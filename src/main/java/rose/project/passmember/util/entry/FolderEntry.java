package rose.project.passmember.util.entry;

import rose.project.passmember.util.entry.PasswordEntry;

/**
 * Created by Lord Rose on 07/04/2017.
 */
public class FolderEntry extends Entry {
    @Override
    public boolean equals(Object obj) {
        boolean response = false;

        if(obj != null) {
            if(obj instanceof PasswordEntry) {
                if(this.id == ((PasswordEntry) obj).id) {
                    response = true;
                }
            }
        }

        return response;
    }
}
