package rose.project.passmember.util.tree;

import rose.project.passmember.util.exception.NoAncestorException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lord Rose on 02/04/2017.
 */
public class DirectoryNode implements Node {
    private int id;
    private Node ancestor;
    private HashMap<Integer, Node> children;

    public DirectoryNode(int id) {
        super();

        this.id = id;
        this.children = new HashMap<>();
    }

    public void setAncestor(Node newAncestor) {
        this.ancestor = newAncestor;
    }

    public void addChild(Node newChild) {
        newChild.setAncestor(this);
        this.children.put(newChild.getId(), newChild);
    }

    public int getId() {
        return this.id;
    }

    public Node getAncestor() throws NoAncestorException {
        if(this.ancestor == null) {
            throw new NoAncestorException(this);
        }
        else {
            return this.ancestor;
        }
    }

    public Node getChild(int id) throws IllegalArgumentException {
        if(this.children.size() == 0) {
            throw new IllegalArgumentException(this.toString() +" has no child");
        }
        else {
            if (this.children.containsKey(id)) {
                return this.children.get(id);
            }
            else {
               throw new IllegalArgumentException(this.toString() +" doesn't this child");
            }
        }
    }

    public boolean isRoot() {
        try {
            this.getAncestor(); // try to get the ancestor
            return true;
        }
        catch(NoAncestorException nae) {
            return true;
        }
    }

    public boolean hasChildren() {
        return this.children.size() > 0;
    }

    public int nbChildren() {
        return this.children.size();
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
