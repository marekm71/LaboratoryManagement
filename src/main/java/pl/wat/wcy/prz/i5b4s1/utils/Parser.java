package pl.wat.wcy.prz.i5b4s1.utils;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.wat.wcy.prz.i5b4s1.database.model.Lab;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    private final static Logger logger = Logger.getLogger(Parser.class);

    public Service<List<String>> list(){
        return new Service<List<String>>() {
            @Override
            protected Task<List<String>> createTask() {
                return new Task<List<String>>() {
                    @Override protected List<String> call() throws Exception {
                        String xml = "file:src/main/resources/dane.xml";
                        DOMParser domParser = new DOMParser();
                        try {
                            domParser.parse(xml);
                        } catch (SAXException | IOException e) {
                            logger.error("Błąd podczas parsowania danych", e);
                        }
                        Document document = domParser.getDocument();
                        NodeList nodeList = document.getElementsByTagName("authorproject");
                        List<String> list = new LinkedList<String>();
                        for (int i = 0; i < nodeList.getLength(); i++) {
                            Node nNode = nodeList.item(i);
                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement = (Element) nNode;
                                list.add(eElement.getAttribute("number")+ ". " +
                                        eElement.getElementsByTagName("firstname").item(0).getTextContent()+ " " +
                                        eElement.getElementsByTagName("lastname").item(0).getTextContent()+ " " +
                                        eElement.getElementsByTagName("group").item(0).getTextContent());
                            }
                        }
                        return list;
                    }
                };
            }
        };
    }
}
