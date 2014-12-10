package info.angryNerds.waffleCode;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


public class DriveConnection {

	private static String CLIENT_ID = "210692037435.apps.googleusercontent.com";
	private static String CLIENT_SECRET = "xVlGZpT4-A5oqnMlXxhAiKBD";

	private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
	
	private Drive service;

	public DriveConnection() throws IOException {
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
		.setAccessType("online")
		.setApprovalPrompt("auto").build();

		String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
		System.out.println("Please open the following URL in your browser then type the authorization code:");
		System.out.println("  " + url);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String code = br.readLine();

		GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
		GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);

		//Create a new authorized API client
		service = new Drive.Builder(httpTransport, jsonFactory, credential).build();
		
		System.out.println("thank you");
	}
	
	public File insertFile(String title, String description,
						   String parentId, String mimeType, String fileName) throws IOException {
		  // File's metadata.
		  File file = new File();
		  file.setTitle(title);
		  file.setDescription(description);
		  file.setMimeType(mimeType);

		  file = service.files().insert(file).execute();

		  // Print the new file ID.
		  System.out.println("File ID: %s" + file.getId());
		  
		  return file;
	}
	
	public File insertFolder(String title, String description,
			   String parentId) throws IOException {
		return insertFile(title, description, parentId, "application/vnd.google-apps.folder", title);
	}
	
	public FileList listFiles(String parentId, String mimeType) throws IOException {
		File file = new File();
			ArrayList<ParentReference> refs = new ArrayList<ParentReference>();
			ParentReference ref = new ParentReference();
			ref.setId(parentId);
			refs.add(ref);
		file.setParents( refs );
		
		FileList files = service.files().list().execute();
		
		return files;
	}
}