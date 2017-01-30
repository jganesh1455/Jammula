package com.ganesh.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AdCampaign {
	private String partner;
	private String content;
	private long duration;
	private long createdTime;
	
	public AdCampaign(){}
	
	public AdCampaign(long duration,String partner,String content){
		
		this.duration=duration;
		this.partner=partner;
		this.content=content;
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

}
