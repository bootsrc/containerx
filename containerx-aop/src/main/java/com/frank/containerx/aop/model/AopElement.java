package com.frank.containerx.aop.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("aop")
public class AopElement {
	@XStreamAsAttribute()
	@XStreamAlias("id")
	private String id;
	
	@XStreamAsAttribute()
	@XStreamAlias("interceptor")
	private String interceptor;
	
	@XStreamAlias("before")
	private String before;
	
	@XStreamAlias("after")
	private String after;
	
	public String getBefore() {
		return before;
	}
	public void setBefore(String before) {
		this.before = before;
	}
	public String getAfter() {
		return after;
	}
	public void setAfter(String after) {
		this.after = after;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInterceptor() {
		return interceptor;
	}
	public void setInterceptor(String interceptor) {
		this.interceptor = interceptor;
	}
}
