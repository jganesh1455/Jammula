package com.ganesh.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ganesh.model.AdCampaign;
import com.ganesh.model.ErrorMessage;

public class AdcompaignService {

	static Map<String, AdCampaign> adList = new HashMap<>();

	public AdcompaignService() {
		// adList.put("1", new Adcompaign(System.currentTimeMillis(), "1",
		// "Sample content1"));
		// adList.put("2", new Adcompaign(System.currentTimeMillis(), "2",
		// "Sample content2"));
	}

	public List<AdCampaign> getAllAdcompaigns() {
		if (adList.isEmpty()) {
			ErrorMessage error = new ErrorMessage("No Adcampaign  Found", 404);
			Response response = Response.status(Status.NOT_FOUND).entity(error).build();
			throw new WebApplicationException(response);
		}
		return new ArrayList<AdCampaign>(adList.values());

	}

	public AdCampaign getAdcompaigns(String partnerid) {
		AdCampaign adcompaign = adList.get(partnerid);
		if (adcompaign == null) {
			ErrorMessage error = new ErrorMessage("No ad campaign for partnerid", 404);
			Response response = Response.status(Status.NOT_FOUND).entity(error).build();
			throw new WebApplicationException(response);
		}
		long currtime = ((System.currentTimeMillis() / 1000) - (adcompaign.getCreatedTime()));
		System.out.println("currtime::" + currtime + "Create Time::" + adcompaign.getCreatedTime() + "Duration::"
				+ adcompaign.getDuration());
		if (currtime > adcompaign.getDuration()) {
			adList.remove(partnerid);
			ErrorMessage error = new ErrorMessage("Adcampaign got expired", 404);
			Response response = Response.status(Status.NOT_FOUND).entity(error).build();
			throw new WebApplicationException(response);

		}
		return adList.get(partnerid);

	}

	public AdCampaign saveAdcompaigns(AdCampaign adCampaign){
		AdCampaign existingCampaign = adList.get(adCampaign.getPartner());
		if(existingCampaign != null){			
			long currtime = ((System.currentTimeMillis() / 1000) - (existingCampaign.getCreatedTime()));
			if(currtime > existingCampaign.getDuration()) {
				adList.put(adCampaign.getPartner(), adCampaign);
				adCampaign.setCreatedTime(System.currentTimeMillis() / 1000);
			} else {
				ErrorMessage error = new ErrorMessage("Active adcampaign exists", 404);
				Response response = Response.status(Status.CONFLICT).entity(error).build();
				throw new WebApplicationException(response);
			}
		}
		adCampaign.setCreatedTime(System.currentTimeMillis() / 1000);
		adList.put(adCampaign.getPartner(), adCampaign);
		return adCampaign;
	}
	
	/*
	 * This method takes multiple adCampaigns but publish it once at a time. 
	 * The second ad will only be published when the first ad duration finishes and so on.
	 */
	public List<AdCampaign> saveMultipleCampaigns(List<AdCampaign> adCampaigns) throws InterruptedException {
		for(AdCampaign adCampaign : adCampaigns){
			//If we already have the active ad for the partner then wait until the duration is finished.
			AdCampaign existingCampaign = adList.get(adCampaign.getPartner());
			if(existingCampaign != null){
				long currTime = ((System.currentTimeMillis() / 1000) - (existingCampaign.getCreatedTime()));
				Thread.sleep(currTime%60 - existingCampaign.getDuration());
			}
			adCampaign.setCreatedTime(System.currentTimeMillis() / 1000);
			adList.put(adCampaign.getPartner(), adCampaign);
			Thread.sleep(adCampaign.getDuration() * 1000);
		}
		return adCampaigns;
	}

}
