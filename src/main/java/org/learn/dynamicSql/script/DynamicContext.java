package org.learn.dynamicSql.script;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class DynamicContext {
    public static final String PARAMETER_OBJECT_KEY = "_parameter";
    private final ContextMap bindings;

    public DynamicContext(Configuration configuration, Object parameterObject) {
        bindings = new ContextMap();
        bindings.put(PARAMETER_OBJECT_KEY, parameterObject);
    }

    private final StringJoiner sqlBuilder = new StringJoiner(" ");
    public void appendSql(String sql) {
        sqlBuilder.add(sql);
    }
    public String getSql() {
        return sqlBuilder.toString().trim();
    }
    public Map<String, Object> getBindings() {
        return bindings;
    }

    static class ContextMap extends HashMap<String, Object> {
        @Override
        public Object get(Object key) {
            String strKey = (String) key;
            return super.get(strKey);
        }
    }
}
