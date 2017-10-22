package com.frank.containerx.aop.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("aop-list")
public class AopListElement {
	// 隐式集合，加上这个注解可以去掉book集合最外面的<list></list>这样的标记
    @XStreamImplicit
    private List<AopElement> list;

	public List<AopElement> getList() {
		return list;
	}
	public void setList(List<AopElement> list) {
		this.list = list;
	}
}
