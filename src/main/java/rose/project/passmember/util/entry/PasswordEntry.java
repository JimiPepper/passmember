package rose.project.passmember.util.entry;

/**
 * Created by Lord Rose on 14/03/2017.
 */
public class PasswordEntry extends Entry {
    public int id;
    public String login;
    public String password;

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
