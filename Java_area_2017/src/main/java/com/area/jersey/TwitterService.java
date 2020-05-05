package com.area.jersey;

import com.google.api.services.gmail.Gmail;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/twitter")
public class TwitterService {

    private String          consumerKey = "XFtRqnlJQyI1ylhJKEjJHxEgc";
    private String          consumerSecret = "Mwi4GzW98NzBwbhD36ZN2Ayw2V6pO3wRa5uHYuFJdUMmpot5No";

    public TwitterService() throws TwitterException, IOException
    {

    }

    private Configuration getTwitterConfig(AccessToken  token)
    {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(token.getToken())
                .setOAuthAccessTokenSecret(token.getTokenSecret());
        return (cb.build());
    }

    @GET
    @Path("/login")
    public Response login(@Context HttpServletRequest req) throws TwitterException, IOException, URISyntaxException {
        Twitter twitter = new TwitterFactory().getInstance();

        twitter.setOAuthConsumer(consumerKey, consumerSecret);

        StringBuffer callbackURL = req.getRequestURL();
        int index = callbackURL.lastIndexOf("/");
        callbackURL.replace(index, callbackURL.length(), "").append("/logged");

        RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
        req.getSession().setAttribute("requestToken", requestToken);
        URI targetURIForRedirection = new URI(requestToken.getAuthorizationURL());
        return Response.seeOther(targetURIForRedirection).build();
    }

    @GET
    @Path("/logged")
    public TwitterTokenObject logged(@Context HttpServletRequest req, @QueryParam("oauth_token") String token, @QueryParam("oauth_verifier") String token_secret) throws TwitterException {
        Twitter     twitter = new TwitterFactory().getInstance();
        AccessToken accesstoken;
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        accesstoken = twitter.getOAuthAccessToken((RequestToken)req.getSession().getAttribute("requestToken"), token_secret);
        req.getSession().removeAttribute("requestToken");
        TwitterTokenObject obj = new TwitterTokenObject();
        obj.token = accesstoken.getToken();
        obj.token_secret = accesstoken.getTokenSecret();
        req.getSession().setAttribute("token_twitter", obj);
        return (obj);
    }

    @GET
    @Path("/postStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public String postStatus(@Context HttpServletRequest req, @QueryParam("msg") String msg) throws TwitterException, IOException, URISyntaxException {
        Twitter             twitter;
        TwitterTokenObject  token = (TwitterTokenObject)req.getSession().getAttribute("token_twitter");
        if (token == null)
            return ("{\"success\":false}");
        twitter = new TwitterFactory(getTwitterConfig(new AccessToken(token.token, token.token_secret))).getInstance();
        twitter.updateStatus(msg);
        return ("{\"success\":true}");
    }

    @GET
    @Path("/listenPost")
    @Produces(MediaType.APPLICATION_JSON)
    public String listenStatus(@Context HttpServletRequest req, @QueryParam("keyword") String keyword)
    {
        TwitterTokenObject  token = (TwitterTokenObject)req.getSession().getAttribute("token_twitter");
        if (token == null)
            return ("{\"success\":false}");
        TwitterStream stream = new TwitterStreamFactory(getTwitterConfig(new AccessToken(token.token, token.token_secret))).getInstance();

        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception e) {
                System.out.println("Exception occured:" + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onTrackLimitationNotice(int n) {
                System.out.println("Track limitation notice for " + n);
            }

            @Override

            public void onStatus(Status status) {

                System.out.println("Got tweet:" + status.getText());

                try {

                    Gmail service = GmailService.login();

                    GmailEmail.sendMessage(service, "me", GmailEmail.createEmail(

                            GmailEmail.getEmailAddress(service), "notifications-area@gmail.com",

                            GmailEmail.getSubject(keyword), GmailEmail.getBodyText(status.getText(), keyword)));

                }   catch (Exception e) {

                    e.printStackTrace();

                }

            }
            @Override
            public void onStallWarning(StallWarning arg0) {
                System.out.println("Stall warning");
            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                System.out.println("Scrub geo with:" + arg0 + ":" + arg1);
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                System.out.println("Status deletion notice");
            }
        };

        FilterQuery qry = new FilterQuery();
        String[] keywords = { keyword };

        qry.track(keywords);

        stream.addListener(listener);
        stream.filter(qry);
        return ("{\"success\":true}");
    }
}