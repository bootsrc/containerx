package org.flylib.containerx.aop.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("aop:aspect")
public class AspectElement {
	@XStreamAsAttribute()
	@XStreamAlias("id")
	private String id;
	
	@XStreamAsAttribute()
	@XStreamAlias("bean")
	private String bean;
	
	@XStreamAsAttribute()
	@XStreamAlias("target")
	private String target;
	
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
	public String getBean() {
		return bean;
	}
	public void setBean(String bean) {
		this.bean = bean;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
}
