package script;

import org.junit.jupiter.api.Test;
import org.learn.dynamicSql.parsing.XNode;
import org.learn.dynamicSql.parsing.XPathParser;
import org.learn.dynamicSql.script.Configuration;
import org.learn.dynamicSql.script.DynamicContext;
import org.learn.dynamicSql.script.XMLScriptBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class XMLScriptBuilderTest {
    private String resource = "src/main/resources/blog-mapping.xml";
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
    public void test_parse() throws IOException {
        XPathParser parser = new XPathParser(getXmlString(resource));
        XNode rootNode = parser.evalNode("/mapper/select");
        XMLScriptBuilder builder = new XMLScriptBuilder(new Configuration(), rootNode,null);

        final HashMap<String, String> parameterObject = new HashMap<String, String>() {{
            put("id", "119");
            put("name", "Steve");
        }};
        DynamicContext context=builder.parseScriptNode(parameterObject);
        System.out.println(context.getSql());
    }
}
