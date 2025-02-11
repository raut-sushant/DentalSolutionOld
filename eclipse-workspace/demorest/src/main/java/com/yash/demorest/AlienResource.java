package com.yash.demorest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("aliens")
public class AlienResource {

	AlienRepository repo;
	
	public AlienResource() {
		repo = new AlienRepository();
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Alien> getAliens() {

		System.out.println("Alien called..");
		return repo.getAliens();
	}
	
	@GET
	@Path("alien/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Alien getAlien(@PathParam("id") int id) {
		System.out.println("get Alien called..");
		return repo.getAlien(id);
	}
	
	@POST
	@Path("addAlien")
	public List<Alien> createAlien(Alien a) {
		System.out.println("add Alien called..");
		repo.createAlien(a);
		return repo.getAliens();
	}
}
