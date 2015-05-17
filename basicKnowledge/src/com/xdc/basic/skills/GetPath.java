package com.xdc.basic.skills;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class GetPath
{
    private static String fileSpt = System.getProperty("file.separator");

    /**
     * 获得与当前类包名一致的路径
     * 
     * @return
     */
    public static String getPackagePath()
    {
        // 获取当前路径
        String curClassName = new Throwable().getStackTrace()[1].getClassName();
        String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
        String packagePath = curPackage.replace(".", fileSpt) + fileSpt;
        return packagePath;
    }

    /**
     * 获得相对于“用户的当前工作目录”的相对路径 （eclipse中“用户的当前工作目录”为该工程根目录）
     * 
     * @return
     */
    public static String getRelativePath()
    {
        // 获取当前路径
        String curClassName = new Throwable().getStackTrace()[1].getClassName();
        String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
        String packagePath = curPackage.replace(".", fileSpt) + fileSpt;

        // 获得相对路径
        String relativePath = "src" + fileSpt + packagePath;
        return relativePath;
    }

    /**
     * 获得绝对路径
     * 
     * @return
     */
    public static String getAbsolutePath()
    {
        // 获取当前路径
        String curClassName = new Throwable().getStackTrace()[1].getClassName();
        String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
        String packagePath = curPackage.replace(".", fileSpt) + fileSpt;

        // 获得相对路径
        String relativePath = "src" + fileSpt + packagePath;

        // 用户的当前工作目录
        String userDir = System.getProperty("user.dir");

        String absolutePath = userDir + fileSpt + relativePath;
        return absolutePath;
    }

    public static String connect(String... paths)
    {
        if (ArrayUtils.isEmpty(paths))
        {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (String path : paths)
        {
            sb.append(path).append(fileSpt);
        }

        String connectedPath = sb.toString();

        // 去掉最后的分隔符
        connectedPath = StringUtils.removeEnd(connectedPath, fileSpt);

        // 将连续的分隔符合并，并改为当前平台的分隔符
        connectedPath = connectedPath.replaceAll("[\\\\/]{1,}", fileSpt + fileSpt);

        return connectedPath;
    }

    public static void main(String[] args)
    {
        String packagePath = GetPath.getPackagePath();
        String relativePath = GetPath.getRelativePath();
        String absolutePath = GetPath.getAbsolutePath();

        System.out.println(packagePath);
        System.out.println(relativePath);
        System.out.println(absolutePath);
    }
}
