package com.ganesh.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ganesh.model.AdCampaign;
import com.ganesh.model.AdCampaigns;
import com.ganesh.services.AdcompaignService;

@Path("/adCampaign")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdCompaigncontroller {

	AdcompaignService adcompaignService = new AdcompaignService();

	@GET
	public List<AdCampaign> getAlladcampaigns() {
		return adcompaignService.getAllAdcompaigns();
	}

	@POST
	public AdCampaign saveAdcampaign(AdCampaign adCampaign) {
		return adcompaignService.saveAdcompaigns(adCampaign);
	}
	
	@Path("/multi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public List<AdCampaign> saveAdcampaigns(AdCampaigns adCampaigns) {
		try {
			return adcompaignService.saveMultipleCampaigns(adCampaigns.getAdCampaigns());
		} catch (InterruptedException e) {
			return null;
		}
	}

	@GET
	@Path("{partnerid}")
	public AdCampaign getAdcampaign(@PathParam("partnerid") String partnerid) {
		
		return adcompaignService.getAdcompaigns(partnerid);
	}

}
