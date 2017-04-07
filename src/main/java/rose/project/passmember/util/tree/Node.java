package rose.project.passmember.util.tree;

import rose.project.passmember.util.exception.NoAncestorException;

import java.util.List;

/**
 * Created by Lord Rose on 02/04/2017.
 */
public interface Node {
    public int getId();
    public Node getAncestor() throws NoAncestorException;
    public void setAncestor(Node newAncestor);
    public Node getChild(int id) throws IllegalArgumentException;
    public boolean isRoot();
    public boolean hasChildren();
    public List<Integer> getPath();
}
