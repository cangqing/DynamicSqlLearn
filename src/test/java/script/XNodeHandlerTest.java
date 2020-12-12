package script;

import org.junit.jupiter.api.Test;
import org.learn.dynamicSql.node.SqlNode;
import org.learn.dynamicSql.node.TextSqlNode;
import org.learn.dynamicSql.parsing.XNode;
import org.learn.dynamicSql.parsing.XPathParser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XNodeHandlerTest {
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

    @Test
    public void testHandler() throws IOException {
        XPathParser parser = new XPathParser(getXmlString(resource));
        XNode xnode = parser.evalNode("/mapper/select/where");
        NodeList children = xnode.getNode().getChildNodes();
        List<SqlNode> contents = new ArrayList<>();

        for (int i = 0; i < children.getLength(); i++) {
            XNode child = xnode.newXNode(children.item(i));
            if (child.getNode().getNodeType() == Node.CDATA_SECTION_NODE || child.getNode().getNodeType() == Node.TEXT_NODE) {
                String data = child.getStringBody("");
                TextSqlNode textSqlNode = new TextSqlNode(data);
                contents.add(textSqlNode);
            } else if (child.getNode().getNodeType() == Node.ELEMENT_NODE) { // issue #628
                String nodeName = child.getNode().getNodeName();
//                XMLScriptBuilder.NodeHandler handler = nodeHandlerMap.get(nodeName);
//                if (handler == null) {
//                    throw new RuntimeException("Unknown element <" + nodeName + "> in SQL statement.");
//                }
//                handler.handleNode(child, contents);
            }
        }
    }
}
