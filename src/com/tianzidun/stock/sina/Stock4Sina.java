package com.tianzidun.stock.sina;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianzidun.http.utils.Http2Txt;
import com.tianzidun.utils.PublicUtil;
import com.tianzidun.utils.Replace;

public class Stock4Sina {
	private static final String SINA_STOCKS_HTML_URL = "http://stocks.sina.cn/sh/?code={code}&vt=4";
	private static List<String> matcherList;
	private static List<Replace> replaceList;
	
	public String getPb(String sCode) throws Exception{
		if(" ".equals(PublicUtil.ss(sCode)))
			throw new Exception("请输入正确的股票代码");
		String sResult = "";
		String[] sParam = getParam(sCode);
		for(String param:sParam){
			if(param.indexOf("市净率")>-1){
				List<String> slNumbers = PublicUtil.matcherNumFromString(param);
				sResult = slNumbers.size() > 0? slNumbers.get(0) : "";
				break;
			}
		}
		return sResult;
	}
	public String getPe(String sCode) throws Exception{
		if(" ".equals(PublicUtil.ss(sCode)))
			throw new Exception("请输入正确的股票代码");
		String sResult = "";
		String[] sParam = getParam(sCode);
		for(String param:sParam){
			if(param.indexOf("市盈率")>-1){
				List<String> slNumbers = PublicUtil.matcherNumFromString(param);
				sResult = slNumbers.size() > 0? slNumbers.get(0) : "";
				break;
			}
		}
		return sResult;
	}
	public Map<String,String> getPbPe(String sCode) throws Exception{
		if(" ".equals(PublicUtil.ss(sCode)))
			throw new Exception("请输入正确的股票代码");
		
		Map<String, String> mResult = new HashMap<String, String>(); 
		String[] sParam = getParam(sCode);

		for(String param:sParam){
			if(param.indexOf("市净率")>-1){
				List<String> slNumbers = PublicUtil.matcherNumFromString(param);
				mResult.put("PB",slNumbers.size() > 0? slNumbers.get(0) : "");
			} else if(param.indexOf("市盈率")>-1){
				List<String> slNumbers = PublicUtil.matcherNumFromString(param);
				mResult.put("PE",slNumbers.size() > 0? slNumbers.get(0) : "");
			}
		}
		
		return mResult;
	}
	
	public String[] getParam(String sCode){
		String[] sResult = null;
		String sData = "";
		try {
			matcherList = new ArrayList<String>();
			replaceList = new ArrayList<Replace>();
			matcherList.add("<ul class=\"stock_tabinfo\">([\\s\\S]+?)<\\/ul>");
			matcherList.add("<div class=\"stock_content\">([\\s\\S]+?)<\\/div>");
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
			
			sData = Http2Txt.url2Text(SINA_STOCKS_HTML_URL.replace("{code}", sCode), matcherList, replaceList);
			sResult = sData.replaceAll("\"", "").split("\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sResult;
	}
	
	public static void main(String[] args) throws Exception {
		Stock4Sina pb4Sina = new Stock4Sina();
		System.out.println(pb4Sina.getPb("sz000001"));
		System.out.println(pb4Sina.getPe("sz000001"));
		System.out.println(pb4Sina.getPbPe("sz000001"));
		System.out.println(pb4Sina.getPb("sh601099"));
		System.out.println(pb4Sina.getPe("sh601099"));
		System.out.println(pb4Sina.getPbPe("sh601099"));
		System.out.println(pb4Sina.getPb(""));
		System.out.println(pb4Sina.getPe(""));
		System.out.println(pb4Sina.getPbPe(""));
	}
}
