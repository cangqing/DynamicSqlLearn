package parsing;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class XNodeTest {
    String resource = "src/main/resources/blog-mapping.xml";
    protected String getXmlString(String resource) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(resource))) {
            StringBuilder sb = new StringBuilder();
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                sb.append(temp);
            }
            return sb.toString();
        }
    }

    private Document createDocument(InputSource inputSource) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setValidating(false);

            factory.setNamespaceAware(false);
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(false);
            factory.setCoalescing(false);
            factory.setExpandEntityReferences(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(null);
            return builder.parse(inputSource);
        } catch (Exception e) {
            throw new RuntimeException("Error creating document instance.  Cause: " + e, e);
        }
    }

    @Test
    public void testNodeParse() throws IOException, XPathExpressionException {
        Document root=createDocument(new InputSource(new StringReader(getXmlString(resource))));
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        Node parentNode= (Node) xpath.evaluate("/mapper/select/where", root, XPathConstants.NODE);
        NodeList nodeList = parentNode.getChildNodes();
        if (nodeList != null) {
            for (int i = 0, n = nodeList.getLength(); i < n; i++) {
                Node childNode = nodeList.item(i);
                System.out.println(childNode);
            }
        }
    }
}
