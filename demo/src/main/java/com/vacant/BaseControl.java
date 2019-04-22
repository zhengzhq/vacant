package com.vacant;

public abstract class BaseControl {
	
	protected abstract String v();

	protected String v(String page) {
		return v() + "_" + page;
	}
}
