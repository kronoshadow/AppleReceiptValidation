package com.example.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * A utility class for making HTTP/HTTPS connections
 * @author Michael Da Silva (kronoshadow)
 *
 */
public class HttpConnection {

	private static final int READ_TIMEOUT = 10000; //10 seconds
	
	private HttpConnection(){}

	/**
	 * Sends a request using HTTP.
	 * 
	 * @param url a <code>String</code> containing the full URL to send the request to
	 * @param requestMethod the {@link RequestMethod} to use for the request
	 * @param requestMediaType the {@link MediaType} of the content that is being sent with the request. It will be ignored if the request method is GET. This parameter is optional and can be <code>null</code>
	 * @param requestContent a <code>String</code> containing the content to send with the request. It will be ignored if the request method is GET. This parameter is optional and can be <code>null</code>
	 * @return a <code>String</code> containing the content from the body of the response
	 * @throws ConnectionFailedException if any exception was thrown during the execution of this method
	 */
	public static String sendRequest(String url, String requestMethod, String requestMediaType, String requestContent) throws ConnectionFailedException {
		
		HttpURLConnection con = null;
		StringWriter responseContent = new StringWriter();
		
		try {
			//setup HTTP connection
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setReadTimeout(READ_TIMEOUT);
			con.setRequestMethod(requestMethod);

			if (!requestMethod.equals(RequestMethod.GET) && requestMediaType != null && requestContent != null) {
				// write content to request body
				con.setDoOutput(true);
				con.setRequestProperty("Content-Type", requestMediaType);
				OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
				wr.write(requestContent);
				wr.flush();
				wr.close();
			}
			
			// read content from body of response
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseContent.append(line);
			}
			rd.close();
		} 
		catch (MalformedURLException muEx) {
			throw new ConnectionFailedException("A failure occured while making the HTTP request to " + url, muEx);
		} 
		catch (ProtocolException pEx) {
			throw new ConnectionFailedException("A failure occured while making the HTTP request to " + url, pEx);
		} 
		catch (IOException ioEx) {
			throw new ConnectionFailedException("A failure occured while making the HTTP request to " + url, ioEx);
		} 
		finally {
			// disconnect from server
			if (con != null)
				con.disconnect();
		}
		return responseContent.toString();
	}

	/**
	 * Sends a request using HTTPS.
	 * 
	 * @param url a <code>String</code> containing the full URL
	 * @param requestMethod the {@link RequestMethod} to use for the request
	 * @param requestMediaType the {@link MediaType} of the content that is being sent with the request. It will be ignored if the request method is GET. This parameter is optional and can be <code>null</code>
	 * @param requestContent a <code>String</code> containing the content to send with the request. It will be ignored if the request method is GET. This parameter is optional and can be <code>null</code>
	 * @return a <code>String</code> containing the content from the response
	 * @throws ConnectionFailedException if any exception was thrown during the execution of this method
	 */
	public static String sendSecureRequest(String url, String requestMethod, String requestMediaType, String requestContent) throws ConnectionFailedException {
		
		HttpURLConnection con = null;
		StringWriter responseContent = new StringWriter();
		
		try {
			// setup HTTPS connection
			con = (HttpURLConnection) new URL(url).openConnection();
			HttpsURLConnection sCon = (HttpsURLConnection) con;
			sCon.setReadTimeout(READ_TIMEOUT); 
			sCon.setRequestMethod(requestMethod);
			
			if (!requestMethod.equals(RequestMethod.GET) && requestMediaType != null && requestContent != null) {
				// write request content
				sCon.setDoOutput(true);
				sCon.setRequestProperty("Content-Type", requestMediaType);
				OutputStreamWriter wr = new OutputStreamWriter(
						sCon.getOutputStream());
				wr.write(requestContent);
				wr.flush();
				wr.close();
			}
			
			// get response
			BufferedReader rd = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseContent.append(line);
			}
			rd.close();
		} 
		catch (MalformedURLException muEx) {
			throw new ConnectionFailedException("A failure occured while making the HTTPS request to " + url, muEx);
		} 
		catch (ProtocolException pEx) {
			throw new ConnectionFailedException("A failure occured while making the HTTPS request to " + url, pEx);
		} 
		catch (IOException ioEx) {
			throw new ConnectionFailedException("A failure occured while making the HTTPS request to " + url, ioEx);
		} 
		finally {
			// disconnect from server
			if (con != null)
				con.disconnect();
		}
		return responseContent.toString();
	}
	
	/**
	 * Contains static declarations of HTTP request methods.
	 */
	public final class RequestMethod {
		private RequestMethod() {}
		public static final String POST = "POST";
		public static final String GET = "GET";
		public static final String PUT = "PUT";
		public static final String DELETE = "DELETE";
	}
	
	public static class ConnectionFailedException extends Exception {
		public ConnectionFailedException() { super(); }
		public ConnectionFailedException(String message) { super(message); }
		public ConnectionFailedException(String message, Throwable cause) { super(message, cause); }
		public ConnectionFailedException(Throwable cause) { super(cause); }
	}
	
}
