package rose.project.passmember.util;

/**
 * Created by Lord Rose on 14/03/2017.
 */
public class PasswordEntry {
    public int id; // Unique identifier for each password stored in a file saved by PassMember
    public String title;
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

    public String toString() {
        return title;
    }
}
