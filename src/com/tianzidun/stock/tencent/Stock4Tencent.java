package com.tianzidun.stock.tencent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tianzidun.http.utils.HttpClientUtil;
import com.tianzidun.utils.JsonHelper;
import com.tianzidun.utils.PublicUtil;

public class Stock4Tencent {
	private static final String TENCENT_PB_API_URL = "http://ifzq.gtimg.cn/appstock/app/minute/query?code={code}";
	
	public String getPb(String sCode) throws Exception{
		if(" ".equals(PublicUtil.ss(sCode)))
			throw new Exception("请输入正确的股票代码");
		
		String[] sParam = getParam(sCode);
		return (sParam != null && sParam.length > 46)?sParam[46]:"";
	}
	public String getPe(String sCode) throws Exception{
		if(" ".equals(PublicUtil.ss(sCode)))
			throw new Exception("请输入正确的股票代码");
		
		String[] sParam = getParam(sCode);
		
		return (sParam != null && sParam.length > 39)?sParam[39]:"";
	}
	public Map<String,String> getPbPe(String sCode) throws Exception{
		if(" ".equals(PublicUtil.ss(sCode)))
			throw new Exception("请输入正确的股票代码");
		
		Map<String, String> mResult = new HashMap<String, String>(); 
		String[] sParam = getParam(sCode);
		if(sParam != null && sParam.length > 39)
			mResult.put("PE", sParam[39]);
		if(sParam != null && sParam.length > 39)
			mResult.put("PB", sParam[46]);
		
		return mResult;
	}
	
	@SuppressWarnings("rawtypes")
	public String[] getParam(String sCode){
		String[] sResult = null;
		String sRequest = "";
		try {
			sRequest = HttpClientUtil.getHttpData(TENCENT_PB_API_URL.replace("{code}", sCode));
			Map map = JsonHelper.toMap(sRequest);
			
			if(map != null){
				if("0".equals(PublicUtil.ss((String) map.get("code")))){
					String sData = (String) map.get("data");
					map = JsonHelper.toMap((String) map.get("data"));
					sData = (String)map.get(sCode);
					map = JsonHelper.toMap(sData);
					sData = (String)map.get("qt");
					map = JsonHelper.toMap(sData);
					
					sData = (String)map.get(sCode);
					if(!" ".equals(PublicUtil.ss(sData))){
						sData = sData.substring(1, sData.length()-1);
					}
					sResult = sData.replaceAll("\"", "").split(",");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sResult;
	}
	
	public static void main(String[] args) throws Exception {
		Stock4Tencent pb4Tencent = new Stock4Tencent();
		System.out.println(pb4Tencent.getPb("sz000001"));
		System.out.println(pb4Tencent.getPe("sz000001"));
		System.out.println(pb4Tencent.getPbPe("sz000001"));
		System.out.println(pb4Tencent.getPb("sh601099"));
		System.out.println(pb4Tencent.getPe("sh601099"));
		System.out.println(pb4Tencent.getPbPe("sh601099"));
	}
}
