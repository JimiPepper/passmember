package rose.project.passmember.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import rose.project.passmember.gui.GUI;
import rose.project.passmember.util.PasswordEntry;
import rose.project.passmember.util.tree.DirectoryNode;
import rose.project.passmember.util.tree.Node;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lord Rose on 17/03/2017.
 */
public class FileManager {
    public static File get(GUI gui) {
        String path = System.getProperty("user.home"); // "resources";
        JFileChooser fc = new JFileChooser(path);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        fc.setDragEnabled(false);
        fc.setFileHidingEnabled(false);
        fc.addChoosableFileFilter(new FileFilter() {
            private final String xml = "xml";

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }

                String fileExtension = this.getExtension(f);
                if (fileExtension != null) {
                    if (fileExtension.equals(this.xml)) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }

                return false;
            }

            @Override
            public String getDescription() {
                return "Passmember file filter";
            }

            private String getExtension(File f) {
                String ext = null;
                String s = f.getName();
                int i = s.lastIndexOf('.');

                if (i > 0 &&  i < s.length() - 1) {
                    ext = s.substring(i+1).toLowerCase();
                }
                return ext;
            }
        });

        int responseUser = fc.showOpenDialog(gui);
        File chosenFile;
        if (responseUser == JFileChooser.APPROVE_OPTION) {
            chosenFile = fc.getSelectedFile();
        } else {
            chosenFile = null;
        }

        return chosenFile;
    }

    public static DefaultMutableTreeNode load(File file) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser xmlParser = null;

        try {
            xmlParser = factory.newSAXParser();
            SAXHandlerPassmember handler = new SAXHandlerPassmember();
            xmlParser.parse(file.getAbsolutePath(), handler);
            return (DefaultMutableTreeNode) handler.loadedSavedPassword.getRoot();
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new DefaultMutableTreeNode();
    }

    public static void export(DefaultMutableTreeNode entries, File file) {
        System.out.println("Je veux sauvegarder !!");

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("passwords");
            doc.appendChild(rootElement);
            FileManager.exportAsXML(rootElement, doc, entries);


            // set attribute to staff element
            /*
            Attr attr = doc.createAttribute("id");
            attr.setValue("1");
            staff.setAttributeNode(attr);
            */

            // shorten way
            // staff.setAttribute("id", "1");

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            File f = new File( URLDecoder.decode( file.getAbsolutePath(), "UTF-8" ) );
            StreamResult result = new StreamResult(f);

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
    }

    private static Element exportAsXML(Element currentNode, Document doc, DefaultMutableTreeNode tree) {
        if(tree.getChildCount() == 0) {
            PasswordEntry currentPassword = (PasswordEntry) tree.getUserObject();
            Element newPasswordNode = doc.createElement("password");

            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode(currentPassword.title));
            newPasswordNode.appendChild(title);

            Element login = doc.createElement("login");
            login.appendChild(doc.createTextNode(currentPassword.login));
            newPasswordNode.appendChild(login);

            Element password = doc.createElement("saved-password");
            password.appendChild(doc.createTextNode(currentPassword.password));
            newPasswordNode.appendChild(password);

            return newPasswordNode;
        }
        else {
            Element newFolderNode = doc.createElement("folder");

            DefaultMutableTreeNode currentChild;
            for(int i = 0 ; i < tree.getChildCount() ; i++) {
                currentChild = (DefaultMutableTreeNode)tree.getChildAt(i);
                newFolderNode.appendChild(FileManager.exportAsXML(newFolderNode, doc, currentChild));
            }

            currentNode.appendChild(newFolderNode);

            return currentNode;
        }
    }
}
