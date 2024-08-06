package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	//this class we will utilize for storing reusable methods 
	
	public static RequestSpecification req;
	
	public RequestSpecification requestSpecification() throws IOException {
		//implementing login functionality on a global level
			if(req==null)  //we have implemented this (req==null) for passing multiple input Data sets in feature file, so that in output logging.txt we have multiple sets of output.
	   {
		PrintStream log=new PrintStream(new FileOutputStream("logging.txt")); //this will generate file named logging.txt
         req= new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addQueryParam("key", "qaclick123")
        		 .addFilter(RequestLoggingFilter.logRequestTo(log)) // does the work of log().all() , but generates a file for better understanding
        		 .addFilter(ResponseLoggingFilter.logResponseTo(log))  //we are trying to log both request and response
        		 .setContentType(ContentType.JSON).build();
         return req;
	}
	return req;
	}
	
	public static String getGlobalValue(String key) throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\Users\\Abhishek Kale\\eclipse-workspace\\APIFramework\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);		
	}
	
	public String getJsonPath(Response response, String key) {
		String resp=response.asString();
		JsonPath js=new JsonPath(resp);
		return js.get(key).toString();
	}
	
	
}
