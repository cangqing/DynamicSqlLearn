import org.learn.dynamicSql.ognl.OgnlUtil;
import org.learn.dynamicSql.parsing.TokenHandler;
import org.learn.dynamicSql.script.DynamicContext;

import static org.learn.dynamicSql.script.DynamicContext.PARAMETER_OBJECT_KEY;

public class BindingTokenHandler implements TokenHandler {

    private DynamicContext context;

    public BindingTokenHandler(DynamicContext context) {
        this.context = context;
    }

    @Override
    public String handleToken(String content) {
        Object value = OgnlUtil.getValue(content, context.getBindings().get(PARAMETER_OBJECT_KEY));
        String srtValue =String.valueOf(value);
        return srtValue;
    }
}
