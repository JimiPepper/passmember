package rose.project.passmember.gui.event;

import rose.project.passmember.gui.JTreeWrapper;
import rose.project.passmember.gui.PassViewerPanel;
import rose.project.passmember.util.entry.FolderEntry;

import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

/**
 * Created by Lord Rose on 07/04/2017.
 */
public class ImplTreeSelectionExpandListener implements TreeSelectionListener, TreeWillExpandListener {
    private JTreeWrapper GUITree;
    private PassViewerPanel viewerPanel;

    public ImplTreeSelectionExpandListener(JTreeWrapper GUITree, PassViewerPanel viewerPanel) {
        this.GUITree = GUITree;
        this.viewerPanel = viewerPanel;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        System.out.println(e.getPath().toString());

        if(GUITree.hasSelection()) {
            TreePath currentPath = this.GUITree.getGUITree().getSelectionPath();

            if(currentPath == null) {
                this.GUITree.setCurrentSelectedNode(this.GUITree.getPasswordsTree()); //root
            }
            else {
                this.GUITree.setCurrentSelectedNode((DefaultMutableTreeNode) currentPath.getLastPathComponent());
            }

            this.viewerPanel.loadSavedPassword(this.GUITree.getEntry());
        }
    }

    @Override
    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
        DefaultMutableTreeNode expandedNode = (DefaultMutableTreeNode)event.getPath().getLastPathComponent();
        ((FolderEntry)expandedNode.getUserObject()).show = true;
    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
        DefaultMutableTreeNode expandedNode = (DefaultMutableTreeNode)event.getPath().getLastPathComponent();
        ((FolderEntry)expandedNode.getUserObject()).show = false;
    }
}
