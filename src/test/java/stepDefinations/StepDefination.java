package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefination extends Utils {
	RequestSpecification Resp;
	ResponseSpecification res;
	Response response;
	TestDataBuild data= new TestDataBuild();
	static String place_id;
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		//below is request and response in a single command
		Resp=given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String httpmethod) {
		
		APIResources resourceAPI=APIResources.valueOf(resource); //Constructor will be called with value of resource which you pass
		System.out.println(resourceAPI.getResource());
		
		res= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(httpmethod.equalsIgnoreCase("POST")){
	     response=Resp.when().post(resourceAPI.getResource());
	     }
		else if(httpmethod.equalsIgnoreCase("GET")){
		response=Resp.when().get(resourceAPI.getResource());
		}
			
	}

	@Then("the API call is success with status code {int}")
	public void the_api_call_is_success_with_status_code(Integer int1) {

	    assertEquals(response.getStatusCode(),200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
		
	    String resp2=response.asString();
	    JsonPath js=new JsonPath(resp2);
	    assertEquals(js.get(keyValue).toString(),expectedValue);
	    
	}
	
	@Then("verify place_Id created does map to {string} using {string}")
	public void verify_place_Id_created_does_map_to_using(String expectedName, String resource) throws IOException {
	    String resp3=response.asString();
		JsonPath js1=new JsonPath(resp3);
	    place_id=js1.get("place_id");
	    
		//reusing request Spec from Utils
		Resp=given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource,"GET");
		
		 String resp4=response.asString();
		 JsonPath js2=new JsonPath(resp4); 
		 String actualName=js2.get("name").toString();
		 //or//String actualName=getJsonPath(response,"name");//created method in utils to get direct jsonpath
	    
		 assertEquals(actualName,expectedName);
	}
	
	@Given("DeletePlace Payload")
	public void deleteplace_Payload() throws IOException {
		Resp=given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}
	
}
