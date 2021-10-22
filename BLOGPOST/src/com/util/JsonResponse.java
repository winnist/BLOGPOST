package com.util;

public class JsonResponse {
	private String status;
	private Object result;
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setResult(Object result) {
		this.result = result;
	}
	
	public Object getResult() {
		return this.result;
	}
}
