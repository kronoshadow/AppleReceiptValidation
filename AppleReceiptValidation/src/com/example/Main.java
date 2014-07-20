package com.example;

import com.example.apple.AppleReceiptValidation;
import com.example.apple.AppleReceiptValidation.AppleReceiptValidationFailedException;
import com.google.gson.JsonObject;

public class Main {

	public static void main(String[] args) {
		String receipt = "Base64 encoded receipt data from app here";
		
		try{
			//validate the receipt
			JsonObject responseJson = AppleReceiptValidation.validateReciept(receipt, true);
			//prints response
			System.out.println(responseJson);
		}
		catch(AppleReceiptValidationFailedException arvfEx){
			arvfEx.printStackTrace();
			//do something to handle API error or invalid receipt...
		}
	}

}
