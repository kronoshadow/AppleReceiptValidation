AppleReceiptValidation
======================

An example utility for validating Apple in app purchase receipts in Java.

Example
-------
```java
String receipt = "Receipt data from app here";
		
try{
	  //validate the receipt using the sandbox (or use false for production)
	  JsonObject responseJson = AppleReceiptValidation.validateReciept(receipt, true);
	  //prints response
	  System.out.println(responseJson);
}
catch(AppleReceiptValidationFailedException arvfEx){
	  arvfEx.printStackTrace();
	  //do something to handle API error or invalid receipt...
}
```

Requirements
------------
* Google Gson 2.2.4 from https://code.google.com/p/google-gson/
