import org.apache.commons.text.StringSubstitutor;
import org.junit.jupiter.api.Test;
import org.learn.dynamicSql.parsing.GenericTokenParser;
import org.learn.dynamicSql.script.Configuration;
import org.learn.dynamicSql.script.DynamicContext;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class TokenParseTest {
    @Test
    public void textMessageFormat(){
        String template="select * from people where name=\"{0}\" and sex=\"{1}\"";
        Object[] params = new Object[]{"jack", "female"};
        String msg = MessageFormat.format(template, params);
        System.out.println(msg);
    }
    @Test
    public void stringSubstitute(){
        String template="select * from people where name='${name}' and sex=${sex}";
        Map valuesMap = new HashMap();
        valuesMap.put("name", "jack");
        valuesMap.put("sex", "female");
        StringSubstitutor sub = new StringSubstitutor(valuesMap);
        String target = sub.replace(template);
        System.out.println(target);
    }

    @Test
    public void testHandler() {
        final HashMap<String, String> parameterObject = new HashMap<String, String>() {{
            put("name", "jack");
            put("sex", "female");
        }};
        String template="select * from people where name='${name}' and sex=${sex}";
        DynamicContext context=new DynamicContext(new Configuration(),parameterObject);
        BindingTokenHandler handler=new BindingTokenHandler(context);
        GenericTokenParser parser=new GenericTokenParser("${", "}", handler);
        String target=parser.parse(template);
        System.out.println(target);
    }

    @Test
    public void testOgnl() {
        final HashMap<String, String> parameterObject = new HashMap<String, String>() {{
            put("name", "jack");
        }};
        DynamicContext context=new DynamicContext(new Configuration(),parameterObject);
        BindingTokenHandler handler=new BindingTokenHandler(context);
        GenericTokenParser parser=new GenericTokenParser("${", "}", handler);
        String expression="hello ${name}";
        expression="Expression test: ${name.indexOf('v')} / ${name in {'Bob', 'Steve'\\} ? 'yes' : 'no'}.";
        String target=parser.parse(expression);
        System.out.println(target);
    }
}