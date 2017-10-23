package com.frank.containerx.aop.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("aop:aspect-list")
public class AspectListElement {
	// 隐式集合，加上这个注解可以去掉book集合最外面的<list></list>这样的标记
    @XStreamImplicit
    private List<AspectElement> list;

	public List<AspectElement> getList() {
		return list;
	}
	public void setList(List<AspectElement> list) {
		this.list = list;
	}
}
