package com.area.jersey;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import com.google.api.services.gmail.Gmail;
import facebook4j.internal.org.json.JSONArray;
import javax.ws.rs.*;
import java.io.*;
import java.util.*;

@Path("/gmail")

public class GmailService {
    private static final String APPLICATION_NAME = "AREA";

    /**
     * Directory to store user credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/2/drive-java-quickstart.json");

    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart
     */
    private static final List<String> SCOPES = Arrays.asList(GmailScopes.MAIL_GOOGLE_COM);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }


    @Path("/login")
    @GET
    public static Gmail login() throws Exception {
        Gmail s = getGmailService();
        return s;
    }

    @Path("/email")
    @GET
    public static List<GmailObject> getParams() throws Exception {
        Gmail service = getGmailService();
        long maxResult = 5;
        List<GmailObject> resultObject = new ArrayList<>();
        //JSONArray resultJSON = new JSONArray();
        String user = "me";

        List<Message> messages = service.users().messages().list(user).setMaxResults(maxResult).setIncludeSpamTrash(false).execute().getMessages();
        for (Message message : messages) {
            boolean read = getReadProperties(service.users().messages().get(user, message.getId()).execute());
            String subject = getSubjectFromEmail(service.users().messages().get(user, message.getId()).execute().getPayload().getHeaders());

            GmailObject newItem = new GmailObject();
            newItem.read = read;
            newItem.subject = subject;
            resultObject.add(newItem);

           /* JSONObject json = new JSONObject();
            json.put("subject", subject);
            json.put("read", read);
            resultJSON.put(json);*/
        }
        return resultObject;
    }

    /**
     * Build and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail getGmailService() throws Exception {
        Credential credential = authorize();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static Credential authorize() throws IOException {
        // Load client secrets.
        String client_secret = "{\"web\":{\"client_id\":\"550799505721-rrusjglih44eiriaidl13q0heuuh6e90.apps.googleusercontent.com\",\"project_id\":\"hopeful-altar-184111\",\"auth_uri\":\"https://accounts.google.com/o/oauth2/auth\",\"token_uri\":\"https://accounts.google.com/o/oauth2/token\",\"auth_provider_x509_cert_url\":\"https://www.googleapis.com/oauth2/v1/certs\",\"client_secret\":\"veUTRfJPJfezFvG0q3u2b14i\"}}";
        InputStream in = new ByteArrayInputStream(client_secret.getBytes());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    private static boolean getReadProperties(Message email) {
        List<String> label = email.getLabelIds();
        for(int i = 0 ; i < label.size() ; i++){
            if (label.get(i).compareTo("UNREAD") == 0)
                return false;
        }
        return true;
    }

    private static String getSubjectFromEmail(List<MessagePartHeader> header) throws Exception {
        JSONArray array = new JSONArray(header);
        for(int i = 0 ; i < header.size() ; i++){
            if (array.getJSONObject(i).getString("name").compareTo("Subject") == 0) {
                return array.getJSONObject(i).getString("value");
            }
        }
        return null;
    }
}