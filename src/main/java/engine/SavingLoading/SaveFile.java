package engine.SavingLoading;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {
    public static Document create() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();

            return docBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            System.out.println("SaveFile Create Fatal Error Document Builder");
            System.exit(1);
        }
        return null;
    }

    public static void save(Document doc, String filename) {
        // Does Not Create File Instantaneously
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            File f = new File(filename);
            FileWriter writer = new FileWriter(f);
            StreamResult result = new StreamResult(writer);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);
            writer.flush();
            writer.close();
        } catch (IOException | TransformerException e) {
            System.out.println("SaveFile Save Fatal Error");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public static Document read(String filepath) {
        try {
        // Set up the parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            doc.getDocumentElement().normalize();

            return doc;
        } catch (ParserConfigurationException e) {
            System.out.println("SaveFile Read Fatal Error Document Builder");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("SaveFile Read IOException");
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (SAXException e) {
            System.out.println("SaveFile Read File Parse Error");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
