package com.vacant;

public class AjaxResponse {

	public static final String STATUS_CODE_OK = "200";
	public static final String STATUS_CODE_ERROR = "300";
	
	public static final String CALLBACK_TYPE_CLOSE_CURRENT = "closeCurrent";
	public static final String CALLBACK_TYPE_FORWARD = "forward";

	private String statusCode;
	private String message;
	private String navTabId;
	private String rel;
	private String callbackType;
	private String forwardUrl;

	public static AjaxResponse ok() {
		AjaxResponse r = new AjaxResponse();
		r.setStatusCode(STATUS_CODE_OK);
		r.setMessage("操作成功");
		return r;
	}
	
	public static AjaxResponse reload(String navTabId) {
		AjaxResponse r = new AjaxResponse();
		r.setStatusCode(STATUS_CODE_OK);
		r.setMessage("操作成功");
		r.setNavTabId(navTabId);
		return r;
	}

	public static AjaxResponse close() {
		AjaxResponse r = new AjaxResponse();
		r.setStatusCode(STATUS_CODE_OK);
		r.setMessage("操作成功");
		r.setCallbackType(CALLBACK_TYPE_CLOSE_CURRENT);
		return r;
	}
	
	public static AjaxResponse dialogCloseAndReload(String navTabId) {
		AjaxResponse r = new AjaxResponse();
		r.setStatusCode(STATUS_CODE_OK);
		r.setMessage("操作成功");
		r.setNavTabId(navTabId);
		r.setCallbackType(CALLBACK_TYPE_CLOSE_CURRENT);
		return r;
	}
	
	public static AjaxResponse navTabCloseAndReload(String navTabId) {
		AjaxResponse r = new AjaxResponse();
		r.setStatusCode(STATUS_CODE_OK);
		r.setMessage("操作成功");
		r.setCallbackType(CALLBACK_TYPE_CLOSE_CURRENT);
		r.setNavTabId(navTabId);
		return r;
	}

	public static AjaxResponse error() {
		return error("操作失败");
	}

	public static AjaxResponse error(String message) {
		AjaxResponse r = new AjaxResponse();
		r.setStatusCode(STATUS_CODE_ERROR);
		r.setMessage(message);
		return r;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
}
