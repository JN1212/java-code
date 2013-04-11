package com.xdc.basic.example.apache.commons.lang3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;

public class SerializationUtilsTest
{
	public static void main(String[] args)
	{
		// 获取当前路径
		String curClassName = new Throwable().getStackTrace()[0].getClassName();
		String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
		String curPath = "src\\" + curPackage.replace(".", "\\") + "\\";

		Date date = new Date();
		byte[] bytes = SerializationUtils.serialize(date);
		System.out.println(ArrayUtils.toString(bytes));
		System.out.println(date);
		Date reDate = (Date) SerializationUtils.deserialize(bytes);
		System.out.println(reDate);
		System.out.println(ObjectUtils.equals(date, reDate));
		System.out.println(date == reDate);
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try
		{
			fos = new FileOutputStream(new File(curPath + "test.txt"));
			fis = new FileInputStream(new File(curPath + "test.txt"));
			SerializationUtils.serialize(date, fos);
			Date reDate2 = (Date) SerializationUtils.deserialize(fis);
			System.out.println(date.equals(reDate2));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fos.close();
				fis.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

}