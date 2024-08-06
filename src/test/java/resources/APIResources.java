package resources;

public enum APIResources {
	
	AddPlaceAPI("maps/api/place/add/json"),
	getPlaceAPI("maps/api/place/get/json"),
	deletePlaceAPI("maps/api/place/delete/json");

	private String resource;
	
	APIResources(String resource) {
		//  Auto-generated constructor 
		this.resource=resource;
	}
	
	public String getResource(){
		return resource;
	}

}
