package com.example.demo.拼音;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;

import java.util.Arrays;

/**
 * pinyin4J 是一个可以将汉字转换成拼音的lib，非常实用，其提供的 PinyinHelper 这个静态类对外提供拼音转换的服务，主要用到以下两个方法：
 *
 * static public String[] toHanyuPinyinStringArray(char ch) //将char(必须为汉字单字)转化为拼音，如果ch为非汉字，返回null
 * static public String[] toHanyuPinyinStringArray(char ch,HanyuPinyinOutputFormat outputFormat) //可以设置输出的格式
 */
public class PinyinTest {
    public static void main(String[] args) {

        String str = "你好世界";
        char[] chars = str.toCharArray();

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        //System.out.println(Arrays.toString(PinyinHelper.toHanyuPinyinStringArray(chars, format)));
    }
}
