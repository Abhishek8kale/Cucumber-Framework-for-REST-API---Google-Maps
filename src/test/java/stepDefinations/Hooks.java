package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException{
		//we are using this class for hooking concept, we use hooks for setting up pre-conditions, to run tags independently (to avoid running previous tags (sometimes if needed))
		//execute this codeonly when placeid is Null
		//now write a code that will give you place_id
		StepDefination m =new StepDefination();
		if(StepDefination.place_id==null)
		{
		m.add_place_payload_with("NavCareCentre", "Marathi", "Satara");
		m.user_calls_with_http_request("AddPlaceAPI", "POST");
		m.verify_place_Id_created_does_map_to_using("NavCareCentre", "getPlaceAPI");
		}
	}

}
