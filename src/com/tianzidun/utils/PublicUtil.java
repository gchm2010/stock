package com.tianzidun.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublicUtil {
	static Logger mylog = Logger.getLogger(PublicUtil.class.getName());

	public PublicUtil() {
	}

	// 写调试信息
	public static void writelog(String str) {
		mylog.info(str);
		// System.out.println(str);
	}

	// 处理字符的转换，主要是为了处理空值
	public static String ss(String str) {
		if ((str == null) || (str.equals("")))
			return (" ");
		else
			return (str);
	}

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 读txt文件
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static StringBuffer readTextFile(String fileName) {
		StringBuffer sRet = new StringBuffer();
		try {
			InputStream is = new FileInputStream(fileName);
			InputStreamReader inputReader;
			BufferedReader bufReader;
			String line = "";
			inputReader = new InputStreamReader(is, "GBK");
			bufReader = new BufferedReader(inputReader);
			while (((line = bufReader.readLine()) != null)) {
				// 我们在每一行后面加上一个"\n"来换行
				sRet.append(line);
				sRet.append("\n");
			}
			bufReader.close();
			inputReader.close();
			is.close();
		} catch (Exception e) {
		}
		return sRet;
	}

	/**
	 * 去以sSep分割的字符串的第iCount个子串，返回子串
	 * 
	 * @param sInput
	 *            输入的字符串
	 * @param sSep
	 *            分割字符串
	 * @param iCount
	 *            第iCount个子串
	 * @return 子字符串
	 */
	public static String GetRetString(String sInput, String sSep, int iCount) {
		try {
			String stmp = sInput + sSep;
			int iIndexBegin = 0;
			int iIndexEnd = 0;
			for (int i = 0; i < iCount; i++) {
				stmp = stmp.substring(iIndexEnd + sSep.length());
				iIndexEnd = stmp.indexOf(sSep);
			}
			return stmp.substring(iIndexBegin, iIndexEnd);
		} catch (Exception e) {
			return ("");
		}
	}

	// 把一个字符转化成大写
	public static String toBigChar(char c) {
		String ss = "" + c;
		if (c == '0')
			ss = "○";
		else if (c == '1')
			ss = "一";
		else if (c == '2')
			ss = "二";
		else if (c == '3')
			ss = "三";
		else if (c == '4')
			ss = "四";
		else if (c == '5')
			ss = "五";
		else if (c == '6')
			ss = "六";
		else if (c == '7')
			ss = "七";
		else if (c == '8')
			ss = "八";
		else if (c == '9')
			ss = "九";
		return ss;
	}

	// 把一个数转成中文
	public static String numtoBigString(String s) {
		if (s == null)
			return ("");
		String ss = "";
		if (s.length() == 1) {
			ss = toBigChar(s.charAt(0));
		} else if (s.charAt(1) == '0') {
			ss = "十";
			if (s.charAt(0) != '1') {
				ss = toBigChar(s.charAt(0)) + ss;
			}
		} else {
			ss = toBigChar(s.charAt(1));
			if (s.charAt(0) == '1') {
				ss = "十" + ss;
			} else if (s.charAt(0) == '0') {
				ss = ss;
			} else {
				ss = toBigChar(s.charAt(0)) + "十" + ss;
			}

		}
		return ss;
	}

	// 把一个字符转化成大写
	public static String toBigString(String s) {
		if (s == null)
			return ("");
		String ss = "";
		for (int i = 0; i < s.length(); i++) {
			ss = ss + toBigChar(s.charAt(i));
		}
		return ss;
	}

	// 字符填充到指定的长度
	public static String lpad(String str, int iLength, char c) {
		try {
			String stmp = ss(str);
			for (int i = 0; i < iLength; i++) {
				stmp = stmp + c;
			}
			// System.out.print(stmp);
			return (stmp.substring(0, iLength));
		} catch (Exception e) {
			return str;
		}
	}

	// 字符串的中文转换
	public static String getCHSStr(String str) {
		try {
			if (str == null || str.equals("")) {
				return str;
			}
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("ISO8859-1");
			String temp = new String(temp_t);
			return temp;
		} catch (Exception e) {
			return str;
		}
	}

	// 处理四舍五入
	public static long sswr(double dd) {
		return ((new Double(dd + 0.5)).longValue());
	}

	// 金额转换与显示，显示金额为999,999.00，999,999的格式，如果金额为零，显示为空；
	// money_type :-4 表示万元，-3 表示千元,0表示元（没有角分），2 表示元（包括角分）
	public static String MoneyDisplay(String sInput, int money_type) {
		double d;
		long ll;
		String stmp;
		try {

			if (money_type == -4) {
				d = Double.parseDouble(sInput);
				ll = sswr(d / 10000);
				stmp = "" + ll;
			} else if (money_type == -3) {
				d = Double.parseDouble(sInput);
				ll = sswr(d / 1000);
				stmp = "" + ll;
			} else if (money_type == 0) {
				d = Double.parseDouble(sInput);
				ll = sswr(d);
				stmp = "" + ll;
			} else {
				stmp = sInput;
			}
			if (stmp.charAt(0) == '.')
				stmp = "0" + stmp;
			if (stmp.charAt(0) == '-' && (stmp.length() > 1)
					&& (stmp.charAt(1) == '.'))
				stmp = "-0" + stmp.substring(1);
			int iPos = stmp.indexOf(".");
			String sRet = "";
			if (iPos == -1)
				iPos = stmp.length();
			iPos = iPos - 1;
			for (int i = iPos; i >= 0; i--) {
				if (((iPos - i) % 3 == 0) && (i != iPos))
					sRet = "," + sRet;
				sRet = stmp.charAt(i) + sRet;
			}
			if ((sRet.charAt(0) == '-') && (sRet.length() > 1)
					&& (sRet.charAt(1) == ',')) // 去除-,335的现象
				sRet = sRet.charAt(0) + sRet.substring(2);
			if (money_type == 2) {
				if (iPos == stmp.length() - 1)
					sRet = sRet + ".00";
				else if (iPos == stmp.length() - 2)
					sRet = sRet + ".00";
				else if (iPos == stmp.length() - 3)
					sRet = sRet + stmp.substring(iPos + 1) + "0";
				else if (iPos == stmp.length() - 4)
					sRet = sRet + stmp.substring(iPos + 1);
			}
			// if(sRet.equals("0")||sRet.equals("0.00"))
			// sRet = "&nbsp;";
			// sRet = sRet;
			return (sRet);
		} catch (Exception E) {
			return ("&nbsp;");
		}
	}

	// 转换回车为;nreturn;标记
	public static String returnToTag(String sStr) {
		if (sStr == null || sStr.equals("")) {
			return sStr;
		}

		StringBuffer sTmp = new StringBuffer();
		int i = 0;
		while (i <= sStr.length() - 1) {
			if (sStr.charAt(i) == '\n') {
				sTmp.append(";nreturn;");
			}
			if (sStr.charAt(i) == '\r') {
				// sTmp.append("");跳过其中一个
			} else {
				sTmp.append(sStr.substring(i, i + 1));
			}
			i++;
		}
		String S1;
		S1 = sTmp.toString();
		return S1;
	}

	// 转换回车为< br >标记
	public static String returnToBr(String sStr) {
		if (sStr == null || sStr.equals("")) {
			return sStr;
		}

		StringBuffer sTmp = new StringBuffer();
		int i = 0;
		while (i <= sStr.length() - 1) {
			if (sStr.charAt(i) == '\n') {
				sTmp.append("<br>");
			}
			if (sStr.charAt(i) == '\r') {
				// sTmp.append("");跳过其中一个
			} else {
				sTmp.append(sStr.substring(i, i + 1));
			}
			i++;
		}
		String S1;
		S1 = sTmp.toString();
		return S1;
	}

	// 转换回车为空格
	public static String returnToSpace(String sStr) {
		if (sStr == null || sStr.equals("")) {
			return sStr;
		}

		StringBuffer sTmp = new StringBuffer();
		int i = 0;
		while (i <= sStr.length() - 1) {
			if (sStr.charAt(i) == '\n' || sStr.charAt(i) == '\r') {
				sTmp.append(" ");
			} else {
				sTmp.append(sStr.substring(i, i + 1));
			}
			i++;
		}
		String S1;
		S1 = sTmp.toString();
		return S1;
	}

	// 转换回车为< br >标记
	public static String returnToBrAndSpace(String sStr) {
		if (sStr == null || sStr.equals("")) {
			return "&nbsp;";
		}

		StringBuffer sTmp = new StringBuffer();
		int i = 0;
		boolean ifchange = true;
		while (i <= sStr.length() - 1) {
			if (sStr.charAt(i) == '\n') {
				sTmp.append("<br>");
				ifchange = true;
			} else if (sStr.charAt(i) == '\r') {
				// sTmp.append("");跳过其中一个
			} else if ((sStr.charAt(i) == 0x20) && ifchange) {
				sTmp.append("&nbsp;");
			} else {
				sTmp.append(sStr.substring(i, i + 1));
				ifchange = false;
			}
			i++;
		}
		String S1;
		S1 = sTmp.toString();
		return S1;
	}

	// 空格显示
	public static String SpaceToNbsp(String sStr) {
		if (sStr == null || sStr.equals("")) {
			return sStr;
		}

		StringBuffer sTmp = new StringBuffer();
		int i = 0;
		boolean ifchange = true;
		while (i <= sStr.length() - 1) {
			if ((sStr.charAt(i) == 0x20) && ifchange) {
				sTmp.append("&nbsp;");
			} else {
				sTmp.append(sStr.substring(i, i + 1));
				ifchange = false;
			}
			i++;
		}
		String S1;
		S1 = sTmp.toString();
		return S1;
	}

	// 转换WEB特殊字符串
	public static String ConvWebStr(String sStr) {
		String ss = ss(sStr);
		ss = ss.replaceAll("&", "&amp;");
		ss = ss.replaceAll("<", "&lt;");
		ss = ss.replaceAll(">", "&gt;");
		ss = ss.replaceAll("'", "&apos;");
		ss = ss.replaceAll("\"", "&quot;");
		return (ss);
	}

	// 输出表格
	public StringBuffer writeTable(ResultSet rs) {
		StringBuffer rlt = new StringBuffer();
		try {
			rlt.append("<table border='0' cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#0089B6\" width=\"100%\">");
			rlt.append("<tr bgcolor=\"#8EDEED\" height=\"10\">");
			ResultSetMetaData md = rs.getMetaData();
			int iTitleCount = md.getColumnCount();
			for (int j = 1; j <= iTitleCount; j++) {
				rlt.append("<th><font style='font-size:10pt;font-family:宋体' color=\"blue\">"
						+ md.getColumnName(j) + "</font></th>");
			}
			rlt.append("</tr>");
			int i = 0;
			while (rs.next()) {
				i++;
				if ((i % 2) == 0) {
					rlt.append("<tr bgcolor=\"#E3FDFF\" height=\"10\">");
				} else {
					rlt.append("<tr bgcolor=\"#F1F1F1\" height=\"10\">");
				}
				for (int j = 1; j <= iTitleCount; j++) {
					if (rs.getString(j) == null)
						rlt.append("<td>&nbsp;</td>");
					else
						rlt.append("<td nowrap>" + PublicUtil.ss(rs.getString(j))
								+ "</td>");
				}
				rlt.append("</tr>");
			}
			rlt.append("</table>");
			return (rlt);
		} catch (Exception e) {
			return (null);
		} finally {
		}
	}

	/**
	 * 把浮点型数字转换为大写 使用示例：changeToChinese(1000.20);
	 * 
	 * @param dou
	 *            ：浮点数，要转换的数字
	 * @return 返回数字的中文描述
	 */
	public static String changeToChinese(double dou) {
		String sStr = String.valueOf(dou);
		return changeToChinese(sStr);
	}

	/**
	 * 功能：把金额转换成大写 使用示例：changeToChinese("1000.20");
	 * 
	 * @param sStr
	 *            :要转换的字符处
	 * @return 数字的中文描述
	 */
	public static String changeToChinese(String sStr) {
		String chinese = "";
		if (sStr.equals("") || sStr == null) {
			return "";
		}
		if (sStr.indexOf(".") > 0) {
			String integerPart = sStr.substring(0, sStr.indexOf("."));// 得到整数部分。
			String decimalPart = sStr.substring(sStr.indexOf(".") + 1,
					sStr.length());// 得到小数部分。
			String decimalP = "";
			if (decimalPart.length() == 2) {// 小数只有2位,
				if (decimalPart.equals("00")) {// 因为只保留2位小数
					decimalP = "";
				} else {
					if (decimalPart.charAt(0) == '0') {
						decimalP = "";
					} else {
						decimalP = changeToBigChinese(decimalPart.charAt(0))
								+ "角";
					}
					if (decimalPart.charAt(1) == '0') {
						decimalP += "整";
					} else {
						decimalP += changeToBigChinese(decimalPart.charAt(1))
								+ "分";
					}
				}
			}
			if (decimalPart.length() == 1) {
				if (decimalPart.equals("0")) {// 因为只保留2位小数
					decimalP = "整";
				} else {
					decimalP = changeToBigChinese(decimalPart.charAt(0)) + "角整";
				}
			}
			if (integerPart.equals("0")) {// 判断整数部分是否为“0”，如果为“0”整数部分不进行转换
				if (decimalPart.length() == 1 && decimalPart.equals("0")) {
					chinese = "";
				} else if (chinese.length() == 2 && chinese.equals("00")) {
					chinese = "";
				} else {
					chinese = decimalP;
				}
			} else {
				chinese = getChinese(integerPart, 0) + "元" + decimalP;
			}
		} else {
			String integerPart = sStr;
			chinese = getChinese(integerPart, 0) + "元整";
		}
		return chinese;
	}

	/**
	 * 功能：进行小数部分的转换。
	 * 
	 * @param ch
	 *            :要转换的字符
	 * @return 转换成繁体数字
	 */
	protected static String changeToBigChinese(char ch) {// 转换小数位数时用。
		String ss = "" + ch;
		if (ch == 0) {
			ss = "零";
		} else if (ch == '1') {
			ss = "壹";
		} else if (ch == '2') {
			ss = "贰";
		} else if (ch == '3') {
			ss = "叁";
		} else if (ch == '4') {
			ss = "肆";
		} else if (ch == '5') {
			ss = "伍";
		} else if (ch == '6') {
			ss = "陆";
		} else if (ch == '7') {
			ss = "柒";
		} else if (ch == '8') {
			ss = "捌";
		} else if (ch == '9') {
			ss = "玖";
		}
		return ss;
	}

	/**
	 * 获得阿拉伯数字对应的中文 最大只支持到9千9百九十九亿9千9百九十九万9千9百九十九， 是进行整数部分的转换
	 * 
	 * @param number
	 *            要转换的数字
	 * @param depth
	 *            递归深度()普通使用直接传0.
	 * @return 数字的中文描述
	 */
	protected static String getChinese(String number, int depth) {
		if (depth < 0) {
			depth = 0;
		}
		String chinese = "";
		String src = number + "";
		if (src.charAt(src.length() - 1) == 'l'
				|| src.charAt(src.length() - 1) == 'L') {
			src = src.substring(0, src.length() - 1);
		}
		if (src.length() > 4) {
			chinese = getChinese(src.substring(0, src.length() - 4), depth + 1)
					+ getChinese(src.substring(src.length() - 4, src.length()),
							depth - 1);
		} else {
			char prv = 0;
			for (int i = 0; i < src.length(); i++) {
				switch (src.charAt(i)) {
				case '0':
					if (prv != '0')
						chinese = chinese + "零";
					break;
				case '1':
					chinese = chinese + "壹";
					break;
				case '2':
					chinese = chinese + "贰";
					break;
				case '3':
					chinese = chinese + "叁";
					break;
				case '4':
					chinese = chinese + "肆";
					break;
				case '5':
					chinese = chinese + "伍";
					break;
				case '6':
					chinese = chinese + "陆";
					break;
				case '7':
					chinese = chinese + "柒";
					break;
				case '8':
					chinese = chinese + "捌";
					break;
				case '9':
					chinese = chinese + "玖";
					break;
				}
				prv = src.charAt(i);

				switch (src.length() - 1 - i) {
				case 1:// 十
					if (prv != '0')
						chinese = chinese + "拾";
					break;
				case 2:// 百
					if (prv != '0')
						chinese = chinese + "佰";
					break;
				case 3:// 千
					if (prv != '0')
						chinese = chinese + "仟";
					break;

				}
			}
		}
		while (chinese.length() > 0
				&& chinese.lastIndexOf("零") == chinese.length() - 1) {
			chinese = chinese.substring(0, chinese.length() - 1);
		}
		if (depth == 1) {
			if (chinese.indexOf("億") == chinese.length() - 1) {// 除去万位都为零时去除“萬”
				chinese += "";
			} else {
				chinese += "萬";
			}
		}
		if (depth == 2) {
			chinese += "億";
		}
		return chinese;
	}

	/**
	 * 替换全部
	 * 
	 * @param input
	 * @param replace
	 * @param substitute
	 * @return
	 */
	public static String replaceAll(String input, String replace,
			String substitute) {
		String sRet = input;
		int iPos = 0;
		while (true) {
			iPos = sRet.indexOf(replace, iPos);
			if (iPos < 0)
				break;
			sRet = sRet.substring(0, iPos) + substitute
					+ sRet.substring(iPos + replace.length());
			iPos = iPos + substitute.length();
		}
		return sRet;
	}
	
	public static List<String> matcherNumFromString(String sSource) {
		List<String> slResult = new ArrayList<String>();

		Pattern p = Pattern.compile("(\\d+(.\\d*))");
		Matcher m = p.matcher(sSource);
		while (m.find()) {
			slResult.add(m.group(1));
//			System.out.println(m.group(1));
		}
		return slResult;
	}
}
