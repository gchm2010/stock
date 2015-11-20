package com.tianzidun.stock;

import java.util.Map;

import com.tianzidun.http.utils.HttpClientUtil;
import com.tianzidun.utils.JsonHelper;
import com.tianzidun.utils.PublicUtil;

public class Pb4Tencent {
	private static final String TENCENT_PB_API_URL = "http://ifzq.gtimg.cn/appstock/app/minute/query?code={code}";
	
	public String getPb(String sCode) throws Exception{
		if(" ".equals(PublicUtil.ss(sCode)))
			throw new Exception("请输入正确的股票代码");
		return getParam(sCode)[46];
	}
	
	@SuppressWarnings("rawtypes")
	public String[] getParam(String sCode){
		String[] sResult = null;
		String sRequest = "";
		try {
			sRequest = HttpClientUtil.getHttpData(TENCENT_PB_API_URL.replace("{code}", sCode));
			Map map = JsonHelper.toMap(sRequest);
			
			System.out.println(map);
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
		Pb4Tencent pb4Tencent = new Pb4Tencent();
		System.out.println(pb4Tencent.getPb("sz000001"));
		System.out.println(pb4Tencent.getPb("sz000002"));
		System.out.println(pb4Tencent.getPb("sz000003"));
		System.out.println(pb4Tencent.getPb("sz000004"));
		System.out.println(pb4Tencent.getPb("sz000005"));
		System.out.println(pb4Tencent.getPb("sz000006"));
		System.out.println(pb4Tencent.getPb("sz000007"));
		System.out.println(pb4Tencent.getPb("sz000008"));
		System.out.println(pb4Tencent.getPb("sz000099"));
		System.out.println(pb4Tencent.getPb("sh601908"));
	}
}
