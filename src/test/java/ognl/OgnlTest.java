package ognl;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OgnlTest {
    @Test
    public void evalExpression() throws OgnlException {
        People people = new People("jack", "male");
        OgnlTest demo = new OgnlTest();
        OgnlContext context = demo.buildOgnlContext();
        context.put("people", people);
        context.setRoot(people);
        //Object express = Ognl.parseExpression("#people.name");
        //Object express = Ognl.parseExpression("1+1");
        //Object express = Ognl.parseExpression("name");
        //Object obj=Ognl.getValue("#people.name", context, context.getRoot());
        Object obj = Ognl.getValue("name", context, context.getRoot());
        System.out.println(obj);
        obj = Ognl.getValue("{1,2,3,4}[2]", context, context.getRoot());
        System.out.println(obj);

        List<People> peopleList = new ArrayList<People>();
        Collections.addAll(peopleList, new People("name1", "male"), new People("name2", "male"), new People("name3", "male"), new People("name4", "male"));
        context.put("peopleList", peopleList);
        //投影操作：
        obj = Ognl.getValue("#peopleList.{name}", context, context.getRoot());
        System.out.println(obj);
        //选择操作：
        // ? 选择满足条件的所有元素
        //^ 选择满足条件的第一个元素
        //$ 选择满足条件的最后一个元素
        obj = Ognl.getValue("#peopleList.{^ #this.name == 'name1'}[0]", context, context.getRoot());
        System.out.println(obj);
//        List<People> list = (List) obj;
//        System.out.println(list.get(0).sex);
    }

    OgnlContext buildOgnlContext() {
        OgnlContext context = (OgnlContext) Ognl.createDefaultContext(this,
                new DefaultMemberAccess(true),
                new DefaultClassResolver(),
                new DefaultTypeConverter());
        context.setRoot(this);
        return context;
    }

    public static void main(String rgs[]) throws OgnlException {
        OgnlTest demo = new OgnlTest();
        // 构建一个OgnlContext对象
        OgnlContext context = demo.buildOgnlContext();
        // 设置根节点，以及初始化一些实例对象
        context.put("year_id", "2020");
        context.put("month_id", "09");
        context.put("corp_name", "贵州燃气");
        context.put("county", "龙里");
        context.put("cust_type_name", "政府党政机关");
        // ognl表达式执行
        String template = "select \n" +
                "cust_type_name\n" +
                ",case when cast('#{month_id}' as int ) %2=1 then nvl(sum(single_cnt),0)  else nvl(sum(double_cnt),0) end as plan_cnt \n" +
                "from cisadm_dws.dws_cis_meter_read_plan_df\n" +
                "where concat(year_id ,month_id)  <concat('#{year_id}','#{month_id}')\n" +
                "and corp_name='#{corp_name}'\n" +
                "and county='#{county}'\n" +
                "and cust_type_name='#{cust_type_name}'\n" +
                "group by \n" +
                "cust_type_name";
        String template1 = "#cust_type_name != null ? #cust_type_name : empty";
        Object expression = Ognl.parseExpression(template1);
        Object result = Ognl.getValue(expression, context, context.getRoot());
        System.out.println(result);
    }

    class People {
        String name;
        String sex;

        public People(String name, String sex) {
            this.name = name;
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
