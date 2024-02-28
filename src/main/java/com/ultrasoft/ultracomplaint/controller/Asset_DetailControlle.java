package com.ultrasoft.ultracomplaint.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Details;
import com.ultrasoft.ultracomplaint.mapper.Asset_DetailsMapper;
import com.ultrasoft.ultracomplaint.requestbody.Asset_Details_Request_Body;
import com.ultrasoft.ultracomplaint.responsebody.APIResponseBody;
import com.ultrasoft.ultracomplaint.responsebody.Asset_Details_Response;
import com.ultrasoft.ultracomplaint.service.Asset_Details_Service;

@RestController
@RequestMapping("/assetDetail")
@Tag(name = "ASSET DETAILS CONTROLLER")
@CrossOrigin("*")
public class Asset_DetailControlle {

	@Autowired
	private Asset_Details_Service service;
	
	@PostMapping(value = "/create",name = "Save")
    public ResponseEntity<APIResponseBody> create(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
    		@RequestBody Asset_Details_Request_Body body
    )
    {
		 try {
			 Tbl_Asset_Details response = service.createNewAsset(body);
	            return new ResponseEntity<>(new APIResponseBody(
	                    "Asset Detail created successfully",
	                   	          Asset_DetailsMapper.toShow(response)), HttpStatus.OK);		
	            
	        }
	        catch (Exception e){
	            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
	        }
	    }
	@GetMapping(value = "/getall",name = "Add")
    public ResponseEntity<APIResponseBody> getAll(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken
//    		@RequestBody Asset_Details_Request_Body body
    )
    {
		 try {
	            List<Asset_Details_Request_Body> response =  service.getAllAsset();
	            return new ResponseEntity<>(new APIResponseBody(
	                    "Asset Detail Fetched successfully",
	                   	          response), HttpStatus.OK);		
	            
	        }
	        catch (Exception e){
	            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
	        }
	    }
	
	@GetMapping("/getall/bycustomer")
	public ResponseEntity<APIResponseBody> getbyCustomer(@RequestParam String customerId)throws Exception{
		try {
			System.out.println("1");
			List<Asset_Details_Request_Body> response = service.getAllAssetByCustomer(customerId);
			return new ResponseEntity<>(new APIResponseBody(
                    "Asset Detail Fetched successfully",
                   	          response), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
		}
	}
	
	
	@GetMapping("/getall/bycategory")
	public ResponseEntity<APIResponseBody> getbyCategory(@RequestParam String categoryId)throws Exception{
		try {
			System.out.println("1");
			List<Asset_Details_Request_Body> response = service.getAllAssetByCategory(categoryId);
			return new ResponseEntity<>(new APIResponseBody(
                    "Asset Detail Fetched successfully",
                   	          response), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
		}
	}
	
	@GetMapping("/getall/byassettype")
	public ResponseEntity<APIResponseBody> getbyType(@RequestParam String assetType)throws Exception{
		try {
			
			List<Asset_Details_Request_Body> response = service.getAllAssetByType(assetType);
			return new ResponseEntity<>(new APIResponseBody(
                    "Asset Detail Fetched successfully",
                   	          response), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
		}
	}
	
	@GetMapping("/getall/byassetstatus")
	public ResponseEntity<APIResponseBody> getbyStatus(@RequestParam String assetStatus)throws Exception{
		try {
			
			List<Asset_Details_Request_Body> response = service.getAllAssetByStatus(assetStatus);
			return new ResponseEntity<>(new APIResponseBody(
                    "Asset Detail Fetched successfully",
                   	          response), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
		}
	}
}
