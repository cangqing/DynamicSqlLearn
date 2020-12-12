import org.junit.jupiter.api.Test;
import org.learn.dynamicSql.node.IfSqlNode;
import org.learn.dynamicSql.node.MixedSqlNode;
import org.learn.dynamicSql.node.SqlNode;
import org.learn.dynamicSql.node.TextSqlNode;
import org.learn.dynamicSql.script.Configuration;
import org.learn.dynamicSql.script.DynamicContext;

import java.util.Arrays;
import java.util.HashMap;

public class SqlNodeTest {

    private MixedSqlNode mixedContents(SqlNode... contents) {
        return new MixedSqlNode(Arrays.asList(contents));
    }
    @Test
    public void testSqlNode() throws Exception {
        final HashMap<String, String> parameterObject = new HashMap<String, String>() {{
            put("name", "Steve");
        }};
        TextSqlNode textSqlNode=new TextSqlNode("SELECT * FROM BLOG");
        IfSqlNode ifSqlNode=new IfSqlNode(mixedContents(new TextSqlNode("WHERE name = '${name}'")), "true");
        MixedSqlNode mixedSqlNode = mixedContents(textSqlNode,ifSqlNode);
        DynamicContext context=new DynamicContext(new Configuration(),parameterObject);
        mixedSqlNode.apply(context);
        System.out.println(context.getSql());
    }
}