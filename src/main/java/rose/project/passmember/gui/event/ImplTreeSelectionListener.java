package rose.project.passmember.gui.event;

import rose.project.passmember.gui.JTreeWrapper;
import rose.project.passmember.gui.PassViewerPanel;
import rose.project.passmember.util.entry.PasswordEntry;

import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * Created by Lord Rose on 07/04/2017.
 */
public class ImplTreeSelectionListener implements TreeSelectionListener {
    private JTreeWrapper GUITree;
    private PassViewerPanel viewerPanel;

    public ImplTreeSelectionListener(JTreeWrapper GUITree, PassViewerPanel viewerPanel) {
        this.GUITree = GUITree;
        this.viewerPanel = viewerPanel;
    }

    @Override
    public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
        System.out.println(e.getPath().toString());
        System.out.println(e.getPath().getLastPathComponent().toString());

        System.out.println(e.getSource());

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
}
