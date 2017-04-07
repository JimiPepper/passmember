package rose.project.passmember.gui;

import rose.project.passmember.util.entry.Entry;
import rose.project.passmember.util.entry.FolderEntry;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.NoSuchElementException;

/**
 * Created by Lord Rose on 07/04/2017.
 */
public class JTreeWrapper {
    private JTree GUITree;
    private DefaultMutableTreeNode passwordsTree;
    private DefaultMutableTreeNode currentSelectedNode;
    private DefaultTreeModel treeModel;

    public JTreeWrapper() {
        this.passwordsTree = new DefaultMutableTreeNode(new FolderEntry());
        this.GUITree = new JTree();
        this.treeModel = (DefaultTreeModel) this.getGUITree().getModel();
    }

    public JTreeWrapper(DefaultMutableTreeNode root) {
        this.passwordsTree = (DefaultMutableTreeNode)root.getRoot(); // just to be sure
        this.GUITree = new JTree(this.passwordsTree);
        this.treeModel = (DefaultTreeModel) this.getGUITree().getModel();
    }

    public JTree getGUITree() {
        return this.GUITree;
    }

    public DefaultMutableTreeNode getPasswordsTree() {
        return this.passwordsTree;
    }

    public void addEntry(Entry entry) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(entry);

        if(!this.currentSelectedNode.isRoot()) { // we always append a new node as child of its parent node
            this.currentSelectedNode = (DefaultMutableTreeNode) this.currentSelectedNode.getParent();
        }

        this.treeModel.insertNodeInto(childNode, this.currentSelectedNode, this.currentSelectedNode.getChildCount());
        this.GUITree.scrollPathToVisible(new TreePath(childNode.getPath()));
    }

    public Entry removeEntry() throws NoSuchElementException {
        if(this.hasSelection()) {
            Entry removedEntry = (Entry)this.currentSelectedNode.getUserObject();
            this.treeModel.removeNodeFromParent(this.currentSelectedNode);
            this.currentSelectedNode = this.passwordsTree; // we select the root node
            // TODO : check if the GUI must be updated after removing an element from its model

            return removedEntry;
        }
        else {
            throw new NoSuchElementException("Impossible to delete an entry from an empty selection");
        }
    }

    public void updateEntry(Entry entry) {
        // TODO : Implement passmember.gui.JTreeWrapper.updateEntry()
    }

    public Entry getEntry() throws NoSuchElementException {
        if(this.hasSelection()) {
            return (Entry)this.currentSelectedNode.getUserObject();
        }
        else {
            throw new NoSuchElementException("Impossible to retrieve an entry from an empty selection");
        }
    }

    public void setCurrentSelectedNode(DefaultMutableTreeNode newSelectedNode) {
        this.currentSelectedNode = newSelectedNode;
    }

    public boolean hasSelection() {
        return !this.GUITree.isSelectionEmpty();
    }
}
