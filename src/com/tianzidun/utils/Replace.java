package com.tianzidun.utils;

public class Replace {
	public int type;
	public String orgin;
	public String replace;
	public int order;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOrgin() {
		return PublicUtil.ss(orgin).replaceAll("\\\\,",",").replaceAll("\\\\", "\\\\\\\\");
	}

	public void setOrgin(String orgin) {
		this.orgin = orgin;
	}

	public String getReplace() {
		return replace;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
