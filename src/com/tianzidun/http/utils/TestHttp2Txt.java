package com.tianzidun.http.utils;

import java.util.ArrayList;
import java.util.List;

import com.tianzidun.utils.Replace;

public class TestHttp2Txt {
	private static String sUrl = "";
	private static List<String> matcherList;
	private static List<Replace> replaceList;
	
	
	public static void main(String[] args) throws Exception {
		sUrl = "http://stocks.sina.cn/sh/?code=sh600030&vt=4";
		matcherList = new ArrayList<String>();
		replaceList = new ArrayList<Replace>();
//		matcherList.add("<div class=\"col-2 fr\">([\\s\\S]+?)<\\/div>");//TODO
		matcherList.add("<ul class=\"stock_tabinfo\">([\\s\\S]+?)<\\/ul>");//TODO
		matcherList.add("<div class=\"stock_content\">([\\s\\S]+?)<\\/div>");//TODO
//		matcherList.add("<li>([\\s\\S]+?)<\\/li>");//TODO
		Replace replace = new Replace();
		replace.setOrder(1);
		replace.setType(1);
		replace.setOrgin("</?[a-zA-Z]+[^><]*>");
		replace.setReplace("");
		replaceList.add(replace);
		replace = new Replace();
		replace.setOrder(2);
		replace.setType(2);
		replace.setOrgin(" ");
		replace.setReplace("");
		replaceList.add(replace);
		replace = new Replace();
		replace.setOrder(3);
		replace.setType(2);
		replace.setOrgin("\t");
		replace.setReplace("");
//		replaceList.add(replace);
	
		System.out.println(Http2Txt.url2Text(sUrl, matcherList, replaceList));
	}
}
