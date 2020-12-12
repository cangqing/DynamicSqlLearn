package parsing;

import org.junit.jupiter.api.Test;
import org.learn.dynamicSql.parsing.XNode;
import org.learn.dynamicSql.parsing.XPathParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class XPathParsingTest {
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
    public void test_parse_xpath() throws IOException {
        XPathParser parser = new XPathParser(getXmlString(resource));
        XNode xnode=parser.evalNode("/mapper");
        String usersNodeToString =xnode.toString();
        System.out.println(usersNodeToString);
    }
}
