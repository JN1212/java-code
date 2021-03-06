package com.xdc.basic.skills;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.MethodSetter;
import org.kohsuke.args4j.spi.Setters;

/**
 * 解析类的方法和字段（递归解析父类) 引自：org.kohsuke.args4j.ClassParser
 */
public class ClassParser
{
    public void parse(Object bean, CmdLineParser parser)
    {
        // recursively process all the methods/fields.
        for (Class<?> c = bean.getClass(); c != null; c = c.getSuperclass())
        {
            for (Method m : c.getDeclaredMethods())
            {
                Option o = m.getAnnotation(Option.class);
                if (o != null)
                {
                    parser.addOption(new MethodSetter(parser, bean, m), o);
                }
                Argument a = m.getAnnotation(Argument.class);
                if (a != null)
                {
                    parser.addArgument(new MethodSetter(parser, bean, m), a);
                }
            }

            for (Field f : c.getDeclaredFields())
            {
                Option o = f.getAnnotation(Option.class);
                if (o != null)
                {
                    parser.addOption(Setters.create(f, bean), o);
                }
                Argument a = f.getAnnotation(Argument.class);
                if (a != null)
                {
                    parser.addArgument(Setters.create(f, bean), a);
                }
            }
        }
    }
}
