package com.scd.test;

import com.scd.util.StrParseUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author James
 */
public class StrParseUtilTest {

    @Test
    public void testLine() {
        String tables= "t_article|t_task_param|t_test|t_user";
        List<String> resultList = StrParseUtil.parseStrBySeparator(tables, "|");
        Assert.assertEquals(4, resultList.size());
        Assert.assertEquals("t_article", resultList.get(0));
        Assert.assertEquals("t_task_param", resultList.get(1));
        Assert.assertEquals("t_test", resultList.get(2));
        Assert.assertEquals("t_user", resultList.get(3));
    }

    @Test
    public void testComma() {
        String tables= "t_article,t_task_param,t_test,t_user";
        List<String> resultList = StrParseUtil.parseStrBySeparator(tables, ",");
        Assert.assertEquals(4, resultList.size());
        Assert.assertEquals("t_article", resultList.get(0));
        Assert.assertEquals("t_task_param", resultList.get(1));
        Assert.assertEquals("t_test", resultList.get(2));
        Assert.assertEquals("t_user", resultList.get(3));
    }

    @Test
    public void testCommaAndSpace() {
        String tables= "t_article, t_task_param , t_test , t_user";
        List<String> resultList = StrParseUtil.parseStrBySeparator(tables, ",");
        Assert.assertEquals(4, resultList.size());
        Assert.assertEquals("t_article", resultList.get(0));
        Assert.assertEquals("t_task_param", resultList.get(1));
        Assert.assertEquals("t_test", resultList.get(2));
        Assert.assertEquals("t_user", resultList.get(3));
        List<String> resultList2 = StrParseUtil.parseStrBySeparator(tables, "|");
        Assert.assertEquals(1, resultList2.size());
    }
}
