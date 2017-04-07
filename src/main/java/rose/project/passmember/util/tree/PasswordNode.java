package rose.project.passmember.util.tree;

import rose.project.passmember.util.PasswordEntry;
import rose.project.passmember.util.exception.NoAncestorException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lord Rose on 02/04/2017.
 */
public class PasswordNode implements Node {
    private int id;
    private PasswordEntry password;
    private Node ancestor;

    public PasswordNode(int id, PasswordEntry password) {
        this.id = id;
        this.password = password;
    }

    public void setAncestor(Node newAncestor) {
        this.ancestor = newAncestor;
    }

    @Override
    public Node getAncestor() throws NoAncestorException {
        if(this.ancestor == null) {
            throw new NoAncestorException(this);
        }
        else {
            return this.ancestor;
        }
    }

    public PasswordEntry getPassword() {
        return this.password;
    }

    @Override
    public Node getChild(int id) throws IllegalArgumentException {
        throw new IllegalArgumentException("A PasswordNode instance is always a leaf");
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public List<Integer> getPath() {
        boolean canLoop = true;
        Node currentNode = this;
        List<Integer> currentPath = new LinkedList<>();

        currentPath.add(currentNode.getId());

        while(canLoop) {
            try {
                currentNode = this.getAncestor();
                currentPath.add(currentNode.getId());
            }
            catch (NoAncestorException nae) {
                canLoop = false;
            }
        }

        return currentPath;
    }
}
