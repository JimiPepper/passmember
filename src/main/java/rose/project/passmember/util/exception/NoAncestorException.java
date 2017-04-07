package rose.project.passmember.util.exception;

import rose.project.passmember.util.tree.Node;

/**
 * Created by Lord Rose on 02/04/2017.
 */
public class NoAncestorException extends Exception {
    public NoAncestorException(Node n) {
        super(n.toString() +" has no ancestor");
    }
}
