package com.ssm.entity;

public class JSON {
	//成功
	public static Integer SUCCESS=0;
	//失败
	public static Integer ERROR=1;
	//警告
	public static Integer WARN=2;
	
	private boolean state;
	private Integer stateId;
	private Object obj;
	private String describe;
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public Integer getStateId() {
		return stateId;
	}
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	@Override
	public String toString() {
		return "Json [state=" + state + ", stateId=" + stateId + ", obj=" + obj + ", describe=" + describe + "]";
	}
	
}
