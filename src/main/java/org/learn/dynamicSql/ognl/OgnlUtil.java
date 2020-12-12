package org.learn.dynamicSql.ognl;

import ognl.ClassResolver;
import ognl.DefaultClassResolver;
import ognl.Ognl;
import ognl.OgnlException;

import java.util.Map;

public class OgnlUtil {
    private static final OgnlMemberAccess MEMBER_ACCESS = new OgnlMemberAccess();
    private static final ClassResolver CLASS_RESOLVER = new DefaultClassResolver();

    public static Object parseExpression(String expression) throws OgnlException {
        Object value = Ognl.parseExpression(expression);
        return value;
    }

    public static Object getValue(String expression, Object root) {
        try {
            Map context = Ognl.createDefaultContext(root, MEMBER_ACCESS, CLASS_RESOLVER, null);
            return Ognl.getValue(parseExpression(expression), context, root);
        } catch (OgnlException e) {
            throw new RuntimeException("Error evaluating expression '" + expression + "'. Cause: " + e, e);
        }
    }
}
