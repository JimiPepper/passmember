package rose.project.passmember.util.entry;

/**
 * Created by Lord Rose on 07/04/2017.
 */
public abstract class Entry {
    public int id; // Unique identifier for each element stored in a file saved by PassMember
    public String title;
    public String description;

    @Override
    public String toString() {
        return this.title;
    }
}
