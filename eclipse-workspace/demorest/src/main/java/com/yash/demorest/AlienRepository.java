package com.yash.demorest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AlienRepository {

	List<Alien> aliens;
	public AlienRepository() {
		aliens = new ArrayList<Alien>();
	
		Alien a1 = new Alien();
		a1.setName("Sushant");
		a1.setAge(34);
		a1.setId(1);
		
		Alien a2 = new Alien();
		a2.setName("Poonam");
		a2.setAge(33);
		a2.setId(2);
		
		Alien a3 = new Alien();
		a3.setName("Parth");
		a3.setAge(1);
		a3.setId(3);
		
		aliens.add(a1);
		aliens.add(a2);
		aliens.add(a3);
	}

	public List<Alien> getAliens(){
		return aliens;
	}
	
	public Alien getAlien(int id) {
		Stream<Alien> alienStream = aliens.stream();
		return (Alien)alienStream.filter(a -> a.getId() == id).toArray()[0];
	}
	
	public Alien createAlien(Alien a) {
		aliens.add(a);
		return a;
	}
	
	public boolean updateAlien(Alien a) {
		return false;
	}
	
	public boolean deleteAlien(int id) {
		return false;
	}
	
}
