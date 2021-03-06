package util;

import model.exception.WrongXMLNamespaceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ClusterFileIO {
    private static final String namespace = "http://rise.cs.drexel.edu/minos/clsx";
    private static ClusterFileIO instance = new ClusterFileIO();

    private ClusterFileIO() {
        //use getInstance method instead
    }

    public static ClusterFileIO getInstance() {
        return instance;
    }

    public void saveClusterData(File path, TreeNode treeRoot) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(path, false));

        out.write("<cluster xmlns=\"http://rise.cs.drexel.edu/minos/clsx\">");
        out.newLine();

        writeGroup(out, (DefaultMutableTreeNode) treeRoot);

        out.append("</cluster>");
        out.close();
    }

    public DefaultMutableTreeNode loadClusterData(File source) throws IOException, WrongXMLNamespaceException, ParserConfigurationException, SAXException {
        DefaultMutableTreeNode treeRoot;

        DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder xmlBuilder;
        Document doc;

        xmlBuilder = xmlFactory.newDocumentBuilder();
        doc = xmlBuilder.parse(source);

        doc.getDocumentElement().normalize();
        Element tempClsx = (Element) doc.getChildNodes().item(0);

        if (tempClsx.hasAttribute("xmlns") && !tempClsx.getAttribute("xmlns").equals(namespace)) {
            throw new WrongXMLNamespaceException();
        }

        treeRoot = new DefaultMutableTreeNode("", true);
        buildTree(doc.getDocumentElement(), treeRoot);
        treeRoot = (DefaultMutableTreeNode) treeRoot.getFirstChild();
        treeRoot.removeFromParent();

        return treeRoot;
    }


    private void writeGroup(BufferedWriter out, DefaultMutableTreeNode node) throws IOException {
        String attributeName = node.getUserObject().toString();
        if (node.getAllowsChildren()) {
            out.append("<group name=\"").append(attributeName).append("\">");
            out.newLine();

            for (int i = 0; i < node.getChildCount(); i++) {
                writeGroup(out, (DefaultMutableTreeNode) node.getChildAt(i));
            }

            out.append("</group>");
            out.newLine();
        } else {
            out.append("<item name=\"").append(attributeName).append("\" />");
            out.newLine();
        }
    }

    private void buildTree(Element DOMnode, DefaultMutableTreeNode treeNode) {
        NodeList nodeList = DOMnode.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element elem;
            DefaultMutableTreeNode newNode;

            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                elem = (Element) node;
                newNode = new DefaultMutableTreeNode(elem.getAttribute("name"), elem.hasChildNodes());
                if (newNode.getAllowsChildren()) {
                    buildTree(elem, newNode);
                }

                treeNode.add(newNode);
            }
        }
    }
}
