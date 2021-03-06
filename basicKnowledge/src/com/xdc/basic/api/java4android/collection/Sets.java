package com.xdc.basic.api.java4android.collection;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class Sets
{
    public static void init(Set<String> set)
    {
        if (set == null)
        {
            return;
        }

        for (int i = 5; i > 0; i--)
        {
            String elememt = String.valueOf(i);

            set.add(elememt);
            // set中的元素是不重复的，第二次插入set无变化，并且add返回值为false
            set.add(elememt);
        }
    }

    public static void output(Set<String> set)
    {
        if (set == null)
        {
            return;
        }

        Iterator<String> it = set.iterator();
        while (it.hasNext())
        {
            String elememt = it.next();
            System.out.println("elememt: " + elememt);
        }
    }

    public static void testHashSet()
    {
        Set<String> hashSet = new HashSet<String>();
        init(hashSet);

        // 元素允许为null
        hashSet.add(null);
        hashSet.add(null);

        output(hashSet);
    }

    public static void testLinkedHashSet()
    {
        Set<String> linkedHashSet = new LinkedHashSet<String>();
        init(linkedHashSet);

        // 元素允许为null
        linkedHashSet.add(null);
        linkedHashSet.add(null);

        output(linkedHashSet);
    }

    public static void testTreeSet()
    {
        // 使用定制排序规则的TreeSet，当然这里只是展示用法，所以定制的String的排序规则与默认的规则是一样的。
        Set<String> treeSet = new TreeSet<String>(new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                if (o1 == null && o2 == null)
                {
                    return 0;
                }

                return o1.compareTo(o2);
            }
        });

        init(treeSet);

        // // 元素不允许为null
        // treeSet.add(null);
        // treeSet.add(null);

        output(treeSet);
    }

    public static void main(String[] args)
    {
        System.out.println("采用HashSet");
        Sets.testHashSet();
        System.out.println();

        System.out.println("采用LinkedHashSet --保持放入顺序");
        Sets.testLinkedHashSet();
        System.out.println();

        System.out.println("采用TreeSet --按元素排序");
        Sets.testTreeSet();
        System.out.println();
    }
}
