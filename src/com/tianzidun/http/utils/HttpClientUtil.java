package com.tianzidun.http.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import com.tianzidun.utils.PublicUtil;

public class HttpClientUtil {
	private final static int HTTP_TIME_OUT = 30 * 1000;

	public static String getHttpData(String sUrl) throws Exception {
		String sResult = "";
		URL url = null;
		InputStream is = null;
		InputStreamReader inputReader = null;
		BufferedReader bufReader = null;
		
		try {
			url = new URL(sUrl);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setDoOutput(false);
			urlConn.setRequestProperty("Content-type", "text/html");
			urlConn.setRequestProperty("Accept-Charset", "utf-8");
			urlConn.setRequestProperty("contentType", "utf-8");
			urlConn.setConnectTimeout(HTTP_TIME_OUT);

			String encoding = urlConn.getContentEncoding();
			if (urlConn.getResponseCode() == 200) {
				if ("gzip".equalsIgnoreCase(encoding)) {
					encoding = null;
					is = new GZIPInputStream(urlConn.getInputStream());
				} else {
					is = urlConn.getInputStream();
				}
				
				if(is != null){
//					BufferedReader in = new BufferedReader(new InputStreamReader(is));
//					StringBuffer linBuffer = new StringBuffer();
//					String tmp = "";
//					while ((tmp=in.readLine())!= null) {
//						linBuffer.append(tmp);
//					}
//					System.out.println(linBuffer.toString());
	//				encoding = getEncode(is, "UTF-8");
	//				inputReader = new InputStreamReader(is, encoding);
					
					inputReader = new InputStreamReader(is);
					bufReader = new BufferedReader(inputReader);
					
					String lineTmp = "";
					StringBuffer lineBuff = new StringBuffer();
					while (((lineTmp = bufReader.readLine()) != null)) {
						lineBuff.append(lineTmp);
					}
					sResult = lineBuff.toString();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally{
			try {
				if(bufReader != null) bufReader.close();
			} catch (IOException e1) {}
			try {
				if(inputReader != null) inputReader.close();
			} catch (IOException e) {}
			try {
				if(inputReader!=null) inputReader.close();
			} catch (IOException e) {}
			try {
				if(is != null) is.close();
			} catch (IOException e) {}
		}
		return sResult;
	}

	private static String getEncode(InputStream inputStream, String sDefaultCode) {
		String code = sDefaultCode;
		if (PublicUtil.ss(code).equals(" "))
			code = "gb2312";
		try {
			byte[] head = new byte[3];
			inputStream.read(head);
			if (head[0] == -17 && head[1] == -69 && head[2] == -65)
				code = "UTF-8";
			if (head[0] == -1 && head[1] == -2)
				code = "UTF-16";
			if (head[0] == -2 && head[1] == -1)
				code = "Unicode";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}

}
