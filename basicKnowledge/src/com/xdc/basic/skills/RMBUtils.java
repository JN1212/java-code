package com.xdc.basic.skills;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class RMBUtils
{
    public final static String Prefix    = "人民币";
    public final static String Postfix   = "整";
    public final static String Digits    = "零壹贰叁肆伍陆柒捌玖";
    public final static String Positions = "分角元拾佰仟万拾佰仟亿拾佰仟";

    public static String convert(double money) throws Exception
    {
        String digits = double2String(money);

        // 不做特殊判断，将阿拉伯数字转成中文
        String RMB = digits2RMB(digits);

        // 去除零后面的单位，但不包括亿、万、元
        RMB = wipeUnitAfter0ExceptYiWanYuan(RMB);

        // 去除重复零，变为一个
        RMB = wipeRepeat0(RMB);

        // 去除亿、万、元前面的零，并清除末尾0
        RMB = wipe0BeforeYiWanYuanAndLast(RMB);

        // 添加后缀
        RMB = postfix(RMB);

        // 添加前缀
        return Prefix + RMB;
    }

    private static String postfix(String RMB)
    {
        if (RMB.endsWith("元"))
        {
            return RMB + Postfix;
        }
        else
        {
            // 特殊情况：assertEquals("人民币壹分", RMBUtils.convert(0.01D));
            return RMB.replaceAll("零元零?", "");
        }
    }

    private static String wipe0BeforeYiWanYuanAndLast(String RMB)
    {
        RMB = RMB.replaceAll("零亿", "亿");

        RMB = RMB.replaceAll("零万", "万");

        // 以零元开头不去除零
        RMB = RMB.replaceAll("(?!^)零元", "元");

        // 特殊情况：assertEquals("人民币壹仟亿零贰佰元整", RMBUtils.convert(100000000200D));
        RMB = RMB.replaceAll("亿万", "亿");

        // 特殊情况： assertEquals("人民币壹元整", RMBUtils.convert(1D));
        RMB = RMB.replaceAll("零$", "");

        return RMB;
    }

    private static String wipeRepeat0(String RMB)
    {
        return RMB.replaceAll("零+", "零");
    }

    private static String wipeUnitAfter0ExceptYiWanYuan(String RMB)
    {
        return RMB.replaceAll("零[^元万亿]", "零");
    }

    private static String digits2RMB(String digits) throws Exception
    {
        int pointIndex = digits.indexOf(".");
        int positionCursor = pointIndex + 1;
        if (positionCursor >= Positions.length())
        {
            throw new Exception("钱数超出范围！");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digits.length(); i++)
        {
            char c = digits.charAt(i);
            if (c == '.')
            {
                continue;
            }
            int digit = c - '0';
            sb.append(Digits.charAt(digit));
            sb.append(Positions.charAt(positionCursor));
            positionCursor--;
        }
        return sb.toString();
    }

    private static String double2String(double money)
    {
        BigDecimal bd = new BigDecimal(money);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return bd.toString();
    }

    @Test
    public void testConvert() throws Exception
    {
        assertEquals("人民币壹仟贰佰叁拾肆亿伍仟陆佰柒拾捌万玖仟零壹拾贰元整", RMBUtils.convert(123456789012D));
        assertEquals("人民币玖仟玖佰玖拾玖亿玖仟玖佰玖拾玖万玖仟玖佰玖拾玖元整", RMBUtils.convert(999999999999D));
        assertEquals("人民币壹仟亿零贰佰元整", RMBUtils.convert(100000000200D));
        assertEquals("人民币伍拾陆万柒仟捌佰玖拾元整", RMBUtils.convert(567890D));
        assertEquals("人民币壹佰元整", RMBUtils.convert(100D));
        assertEquals("人民币壹元整", RMBUtils.convert(1D));
        assertEquals("人民币零元整", RMBUtils.convert(0D));

        assertEquals("人民币壹拾贰亿叁仟肆佰伍拾陆万柒仟捌佰玖拾元零壹分", RMBUtils.convert(1234567890.01D));
        assertEquals("人民币玖拾玖亿玖仟玖佰玖拾玖万玖仟玖佰玖拾玖元壹角壹分", RMBUtils.convert(9999999999.11D));
        assertEquals("人民币伍拾陆万柒仟捌佰玖拾元玖角贰分", RMBUtils.convert(567890.92D));
        assertEquals("人民币壹佰元叁角玖分", RMBUtils.convert(100.39D));
        assertEquals("人民币壹元贰角贰分", RMBUtils.convert(1.22D));
        assertEquals("人民币壹元贰角", RMBUtils.convert(1.20D));
        assertEquals("人民币壹元零贰分", RMBUtils.convert(1.02D));
        assertEquals("人民币玖角玖分", RMBUtils.convert(0.99D));
        assertEquals("人民币壹角", RMBUtils.convert(0.1D));
        assertEquals("人民币壹分", RMBUtils.convert(0.01D));
    }
}
