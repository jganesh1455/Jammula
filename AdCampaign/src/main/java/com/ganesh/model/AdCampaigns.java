package com.ganesh.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AdCampaigns {
	private List<AdCampaign> adCampaigns;

	public AdCampaigns(){
		
	}
	
	public List<AdCampaign> getAdCampaigns() {
		return adCampaigns;
	}

	public void setAdCampaigns(List<AdCampaign> adCampaigns) {
		this.adCampaigns = adCampaigns;
	}
}
