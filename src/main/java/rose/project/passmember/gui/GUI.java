package rose.project.passmember.gui;

import rose.project.passmember.io.FileManager;
import rose.project.passmember.gui.modal.TypePasswordDialog;
import rose.project.passmember.util.PasswordEntry;
import rose.project.passmember.util.tree.Node;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created by Lord Rose on 14/03/2017.
 */
public class GUI extends JFrame implements TreeSelectionListener, ActionListener {
    public static final String TITLE = "PassMember";
    private PassViewerPanel summary;
    private DefaultMutableTreeNode passwords;
    private JPanel rootPane;
    private JPanel actionBar;
    private JTree tree;
    private JMenuBar menuBar;
    private JOptionPane pane;
    private JDialog savePrompt;
    private File loadedFile;
    private boolean fileHasChanged;

    /* ITEM DES MENUS */
    private JMenuItem ouvrir;
    private JMenuItem sauvegarder;
    private JMenuItem fermer;
    private JMenuItem quitter;

    /* BOUTONS DE LA BARRE D'ACTION  */
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton addFolderButton;

    /* BOITE DE DIALOGUE */
    private TypePasswordDialog typePassDialog;

    public GUI() {
        super(TITLE);
        this.fileHasChanged = false;

        this.setPreferredSize(new Dimension(640, 480));

        this.rootPane = new JPanel();
        this.rootPane.setLayout(new BorderLayout());

        this.tree = new JTree();
        this.tree.setRootVisible(false);
        this.tree.addTreeSelectionListener(this);

        this.summary = new PassViewerPanel();

        this.getContentPane().add(rootPane);
        this.buildMenuBar();
        this.buildActionBar();
        this.buildConfirmSaveFile();
        this.initIconFrame();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public GUI(String filepath) {
        this();

        this.loadedFile = new File(filepath);
        DefaultMutableTreeNode listPasswords = FileManager.load(this.loadedFile);
        this.initGUI(listPasswords);
    }

    private void initIconFrame() {
        URL iconURL = getClass().getResource("/icon.png");
        ImageIcon icon = new ImageIcon(iconURL);

        this.setIconImage(icon.getImage());
    }

    public void initGUI(DefaultMutableTreeNode passwords) {
        this.passwords = passwords;
        final JPanel me_rootPane = this.rootPane;

        this.rootPane.removeAll();
        this.summary.cleanUI();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                me_rootPane.revalidate();
                me_rootPane.repaint();
            }
        });

        this.tree = new JTree(this.passwords);
        this.tree.setRootVisible(false);
        this.tree.addTreeSelectionListener(this);

        this.rootPane.add(this.actionBar, BorderLayout.NORTH);
        this.rootPane.add(this.tree, BorderLayout.WEST);
        this.rootPane.add(this.summary, BorderLayout.CENTER);
        this.pack();
    }

    private void buildMenuBar() {
        this.menuBar = new JMenuBar();
        JMenu menu = new JMenu("Fichiers");

        this.ouvrir = new JMenuItem("Ouvrir");
        this.ouvrir.addActionListener(this);
        this.ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK)); // Ctrl + O
        menu.add(this.ouvrir);

        this.sauvegarder = new JMenuItem("Sauvegarder");
        this.sauvegarder.addActionListener(this);
        this.sauvegarder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK)); // Ctrl + Suppr
        menu.add(this.sauvegarder);

        this.fermer = new JMenuItem("Fermer");
        this.fermer.addActionListener(this);
        this.fermer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK)); // Ctrl + Suppr
        menu.add(this.fermer);


        this.quitter = new JMenuItem("Quitter");
        this.quitter.addActionListener(this);
        this.quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.CTRL_MASK)); // Ctrl + Suppr
        menu.add(this.quitter);

        this.menuBar.add(menu);
        this.setJMenuBar(this.menuBar);
    }

    public void cleanUI() {
        final JPanel me_rootPane = this.rootPane;
        this.rootPane.removeAll();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                me_rootPane.revalidate();
                me_rootPane.repaint();
            }
        });
    }

    private void buildActionBar() {
        this.actionBar = new JPanel();
        this.actionBar.setLayout(new BoxLayout(this.actionBar, BoxLayout.X_AXIS));

        this.addButton = new JButton();
        this.addButton.addActionListener(this);
        URL iconURL = getClass().getResource("/icons/add.png");
        ImageIcon icon = new ImageIcon(iconURL);
        Image newimg = icon.getImage().getScaledInstance((int)(icon.getIconHeight() * 0.05), (int)(icon.getIconWidth() * 0.05),  java.awt.Image.SCALE_SMOOTH ) ;
        this.addButton.setIcon(new ImageIcon(newimg));

        this.updateButton = new JButton();
        this.updateButton.addActionListener(this);
        iconURL = getClass().getResource("/icons/edit.png");
        icon = new ImageIcon(iconURL);
        newimg = icon.getImage().getScaledInstance((int)(icon.getIconHeight() * 0.05), (int)(icon.getIconWidth() * 0.05),  java.awt.Image.SCALE_SMOOTH ) ;
        this.updateButton.setIcon(new ImageIcon(newimg));

        this.addFolderButton = new JButton();
        this.addFolderButton.addActionListener(this);
        iconURL = getClass().getResource("/icons/new-folder.png");
        icon = new ImageIcon(iconURL);
        newimg = icon.getImage().getScaledInstance((int)(icon.getIconHeight() * 0.05), (int)(icon.getIconWidth() * 0.05),  java.awt.Image.SCALE_SMOOTH ) ;
        this.addFolderButton.setIcon(new ImageIcon(newimg));

        this.deleteButton = new JButton();
        this.deleteButton.addActionListener(this);
        iconURL = getClass().getResource("/icons/delete.png");
        icon = new ImageIcon(iconURL);
        newimg = icon.getImage().getScaledInstance((int)(icon.getIconHeight() * 0.05), (int)(icon.getIconWidth() * 0.05),  java.awt.Image.SCALE_SMOOTH ) ;
        this.deleteButton.setIcon(new ImageIcon(newimg));

        this.actionBar.add(this.addButton);
        this.actionBar.add(this.updateButton);
        this.actionBar.add(this.addFolderButton);
        this.actionBar.add(this.deleteButton);
    }

    public void buildConfirmSaveFile() {
        this.pane = new JOptionPane(
                "Voulez-vous sauvegarder les modifications avant la fermeture du fichier ?",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        this.savePrompt = this.pane.createDialog(this, "Enregistrer le document ?");
    }

    public boolean hasLoadFile() {
        return this.passwords != null;
    }

    private void appendNewPassword(PasswordEntry password, DefaultMutableTreeNode node) {
        node.add(new DefaultMutableTreeNode(password));
        this.tree.repaint();
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        System.out.println(e.getPath().toString());
        System.out.println(e.getPath().getLastPathComponent().toString());

        System.out.println(e.getSource());

        if(!this.tree.isSelectionEmpty()) { // to avoid calling on a delete entry
            this.summary.loadSavedPassword((PasswordEntry) ((DefaultMutableTreeNode) this.tree.getLastSelectedPathComponent()).getUserObject());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(this.ouvrir.equals(source)) {
            this.loadedFile = FileManager.get(this);

            if(this.loadedFile != null) { // nul si aucun fichier n'est choisi
                this.initGUI(FileManager.load(this.loadedFile));
            }
        }
        else if(this.sauvegarder.equals(source)) {
            this.fileHasChanged = false;
            FileManager.export(this.passwords, this.loadedFile);
        }
        else if(this.fermer.equals(source)) {
            if(this.fileHasChanged) {
                this.savePrompt.show();
                Object response = this.pane.getValue();

                if(!response.equals(JOptionPane.CANCEL_OPTION)) {
                    if(response.equals(JOptionPane.YES_OPTION)) { // yes
                        FileManager.export(this.passwords, this.loadedFile);
                    }

                    this.fileHasChanged = false;
                }
                else { // cancel
                    // rien ne se produit (pour le moment)
                }
            }

            // dans tous les cas, on ferme le fichier
            this.loadedFile = null;
            this.passwords = null;
            this.cleanUI();
        }
        else if(this.quitter.equals(source)) {
            if(this.fileHasChanged) {
                this.savePrompt.show();
                Object response = this.pane.getValue();

                if(!response.equals(JOptionPane.CANCEL_OPTION)) {
                    if(response.equals(JOptionPane.YES_OPTION)) { // yes
                        FileManager.export(this.passwords, this.loadedFile);
                    }

                    this.fileHasChanged = false;
                }
                else { // cancel
                    // rien ne se produit (pour le moment)
                }
            }

            System.exit(0); // OK
        }
        else if(this.addButton.equals(source)) {
            if(this.hasLoadFile()) {
                boolean oldFileHasChanged = this.fileHasChanged;

                try {
                    this.fileHasChanged = true;

                    this.typePassDialog = new TypePasswordDialog(this);

                    if(this.tree.isSelectionEmpty()) {
                        this.appendNewPassword(this.typePassDialog.getPassword(), (DefaultMutableTreeNode)this.tree.getModel().getRoot());
                    }
                    else{
                        this.appendNewPassword(this.typePassDialog.getPassword(), (DefaultMutableTreeNode) ((DefaultMutableTreeNode) this.tree.getLastSelectedPathComponent()).getParent());
                    }

                    this.initGUI(this.passwords);
                }
                catch (UnsupportedOperationException uoe) {
                    this.fileHasChanged = oldFileHasChanged; // restore previous value
                }
            }
        }
        // TODO : Implémenter la mise à jour d'un mot de passe (voir pour charger la fenêtre modale avec des valeurs par défaut)
        else if(this.updateButton.equals(source)) {
            if(this.hasLoadFile() && !this.tree.isSelectionEmpty()) {
                String currentSelected = this.tree.getSelectionPath().getLastPathComponent().toString();
                /*
                for(PasswordEntry password : this.passwords) {
                    if(password.title.equals(currentSelected)) {
                        this.typePassDialog = new TypePasswordDialog(this);
                        this.passwords.add(this.typePassDialog.getPassword());

                        this.initGUI(this.passwords);

                        this.summary.loadSavedPassword(password);
                    }
                }
                */
            }
        }
        // TODO : Implémenter l'ajout d'un dossier de mot de passe
        else if(this.addFolderButton.equals(source)) {

        }
        else if(this.deleteButton.equals(source)) {
            if(!this.tree.isSelectionEmpty()) {
                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                model.removeNodeFromParent(((DefaultMutableTreeNode) this.tree.getLastSelectedPathComponent()));
            }
        }
    }
}