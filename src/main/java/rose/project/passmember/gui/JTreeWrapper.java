package rose.project.passmember.gui;

import rose.project.passmember.util.entry.Entry;
import rose.project.passmember.util.entry.FolderEntry;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
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
        this.currentSelectedNode = this.passwordsTree;
        this.GUITree = new JTree();
        this.GUITree.setExpandsSelectedPaths(true);
        this.GUITree.setShowsRootHandles(true);
        this.GUITree.setRootVisible(false);
        this.GUITree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.treeModel = (DefaultTreeModel) this.getGUITree().getModel();

        this.GUITree.setCellRenderer(new CustomTreeCellRenderer());
    }

    public JTreeWrapper(DefaultMutableTreeNode root) {
        this.passwordsTree = (DefaultMutableTreeNode)root.getRoot(); // just to be sure
        this.currentSelectedNode = this.passwordsTree;
        this.GUITree = new JTree(this.passwordsTree);
        this.GUITree.setExpandsSelectedPaths(true);
        this.GUITree.setShowsRootHandles(true);
        this.GUITree.setRootVisible(false);
        this.GUITree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.treeModel = (DefaultTreeModel) this.getGUITree().getModel();


        this.GUITree.setCellRenderer(new CustomTreeCellRenderer());
    }

    public JTree getGUITree() {
        return this.GUITree;
    }

    public DefaultMutableTreeNode getPasswordsTree() {
        return this.passwordsTree;
    }

    public void addEntry(Entry entry) {
        DefaultMutableTreeNode folderNode = this.currentSelectedNode; // after appended a node, currentSelectedNode address is lost
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(entry);

        if(!this.currentSelectedNode.isRoot() && !(currentSelectedNode.getUserObject() instanceof FolderEntry)) {
            // we always append a new node as child of its parent node only if it is not a folder that is currently selected
            this.currentSelectedNode = (DefaultMutableTreeNode) this.currentSelectedNode.getParent();
            folderNode = this.currentSelectedNode;
        }

        this.treeModel.insertNodeInto(childNode, this.currentSelectedNode, this.currentSelectedNode.getChildCount());
        this.GUITree.expandPath(new TreePath(folderNode.getPath()));
        this.GUITree.setSelectionPath(new TreePath(childNode.getPath()));
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

    private static class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

            Icon icon;
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)value;
            if(currentNode.getUserObject() instanceof FolderEntry) { // we do not use leaf variable because a folder may be empty if it contains no password
                icon = UIManager.getIcon("Tree.openIcon");
            }
            else {
                icon = UIManager.getIcon("Tree.leafIcon");
            }

            this.setIcon(icon);

            return this;
        }
    }
}
