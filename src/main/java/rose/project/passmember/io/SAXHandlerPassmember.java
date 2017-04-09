package rose.project.passmember.io;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import rose.project.passmember.util.entry.FolderEntry;
import rose.project.passmember.util.entry.PasswordEntry;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Lord Rose on 13/03/2017.
 */
public class SAXHandlerPassmember extends DefaultHandler {
    boolean bWebsite;
    boolean bTitle;
    boolean bLogin;
    boolean bSavedPassword;
    boolean bFolder;
    PasswordEntry currentPassword;
    public DefaultMutableTreeNode loadedSavedPassword;

    public SAXHandlerPassmember() {
        super();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        if(name.equalsIgnoreCase(XMLTags.FOLDER)) {
            if(this.loadedSavedPassword == null) { // no root
                FolderEntry folderEntry = new FolderEntry();
                folderEntry.title = "root";
                folderEntry.show = true;
                this.loadedSavedPassword = new DefaultMutableTreeNode(folderEntry);
            }
            else {
                FolderEntry folderEntry = new FolderEntry();
                folderEntry.title = attributes.getValue("title");
                folderEntry.show = attributes.getValue("show").equals("yes") ? true : false;
                DefaultMutableTreeNode newFolder = new DefaultMutableTreeNode(folderEntry);
                this.loadedSavedPassword.add(newFolder);

                this.loadedSavedPassword = newFolder; // move pointer to current folder node that will be populated
            }
        }

        if(name.equalsIgnoreCase(XMLTags.PASSWORD)) {
            this.currentPassword = new PasswordEntry();
            this.bWebsite = true;
        }

        if(name.equalsIgnoreCase(XMLTags.TITLE)) {
            this.bTitle = true;
        }

        if(name.equalsIgnoreCase(XMLTags.LOGIN)) {
            this.bLogin = true;
        }

        if(name.equalsIgnoreCase(XMLTags.SAVED_PASSWORD)) {
            this.bSavedPassword = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        if(name.equalsIgnoreCase(XMLTags.PASSWORD)) {
            this.loadedSavedPassword.add(new DefaultMutableTreeNode(currentPassword));
            this.bWebsite = false;
        }

        if(name.equalsIgnoreCase(XMLTags.TITLE)) {
            this.bTitle = false;
        }

        if(name.equalsIgnoreCase(XMLTags.LOGIN)) {
            this.bLogin = false;
        }

        if(name.equalsIgnoreCase(XMLTags.SAVED_PASSWORD)) {
            this.bSavedPassword = false;
        }
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        String content = new String(chars, start, length);

        if(this.bTitle) {
            this.currentPassword.title = content;
        }

        if(this.bLogin) {
            this.currentPassword.login = content;
        }

        if(this.bSavedPassword) {
            this.currentPassword.password = content;
        }
    }
}
