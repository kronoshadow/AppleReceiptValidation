package com.example.http;

/**
 * A utility class containing internet media types
 * @author Michael Da Silva (kronoshadow)
 *
 */
public class MediaType {

	private MediaType(){}
	
	public class Text{
		private Text(){}
		public static final String HTML = "text/html";
		public static final String CSS = "text/css";
		public static final String PLAIN = "text/plain";
		public static final String XML = "text/xml";
		public static final String CSV = "text/csv";
	}
	
	public class Application{
		private Application(){}
		public static final String JSON = "application/json";
		public static final String JAVASCRIPT = "application/javascript";
		public static final String X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
	}
	
	public class Image{
		private Image(){}
		public static final String JPEG = "image/jpeg";
		public static final String PNG = "image/png";
		public static final String GIF = "image/gif";
	}
	
	public class Video{
		private Video(){}
		public static final String MP4 = "video/mp4";
		public static final String OGG = "video/ogg";
		public static final String MPEG = "video/mpeg";
		public static final String WEBM = "video/webm";
	}
}
