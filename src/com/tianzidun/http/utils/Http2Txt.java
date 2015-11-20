package com.tianzidun.http.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Pattern;

import com.tianzidun.utils.PublicUtil;
import com.tianzidun.utils.Replace;

public class Http2Txt {
//	private static char c160 = 160;
//	private static String s160 = new String(String.valueOf(c160));

	public static String url2Text(String sUrl, List<String> matcherList,List<Replace> replaceList) throws Exception {
		return html2Text(HttpClientUtil.getHttpData(sUrl), matcherList,replaceList);
	}
	
	public static String html2Text(String inputString, List<String> matcherList,List<Replace> replaceList) {
		String textStr = "";
		String tmpStr="";
		java.util.regex.Pattern pattern;
		java.util.regex.Matcher matcher;

		try {
			for (String sRegEx : matcherList) {
				if("".equals(textStr)) tmpStr = inputString;
				else {tmpStr = textStr; textStr = "";}
				if (sRegEx != null && sRegEx.length() > 0) {
					pattern = Pattern.compile(sRegEx, Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(tmpStr);
					while (matcher.find()) {
						textStr = textStr + matcher.group(1)+"\n";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Html2Text: " + e.getMessage());
		}
		try {
			if (" ".equals(PublicUtil.ss(textStr)))
				textStr = inputString;
			textStr = replaceStylec(textStr, replaceList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return textStr;// 返回文本字符串
	}
	

	/**
	 * 替换HTML标签
	 * 
	 * @param htmlStr
	 * @return
	 */
	private static String replaceStylec(String htmlStr, List<Replace> replaceList) {
		java.util.regex.Pattern pattern;
		java.util.regex.Matcher matcher;
		String sResult = htmlStr;

		for (Replace replace : replaceList) {
//			if (replace.getOrgin().equals(" "))
//				continue;
			if (replace.getReplace() == null)
				replace.setReplace("");

			if (replace.getType() == 1) { // 1：正则替换
				pattern = Pattern.compile(replace.getOrgin(), Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(sResult);
				while (matcher.find())
					sResult = sResult.replaceAll(matcher.group().replace("\\", "\\\\"), replace.getReplace());
			} else if (replace.getType() == 2) { // 2，字符串替换
				sResult = sResult.replaceAll(replace.getOrgin(), replace.getReplace());
			} else {
				continue;
			}
		}
		return sResult;
	}
	


	public static String GetUrlParam(String sUrl, String sParamName) {
		String sResult = "";
		String sTmpUrl;
		try {
			sTmpUrl = java.net.URLDecoder.decode(sUrl, "UTF-8");
			int iIndex = sTmpUrl.indexOf('?');
			if (iIndex > -1) {
				String paramStr = sTmpUrl.substring(iIndex + 1);
				if (!PublicUtil.ss(paramStr).equals(" ")) {
					String[] params = paramStr.split("&");
					for (int i = 0; i < params.length; i++) {
						String[] paramValues = params[i].split("=");
						if (sParamName.equalsIgnoreCase(paramValues[0]))
							if (paramValues[1] != null)
								sResult = paramValues[1];
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sResult;
	}

}
