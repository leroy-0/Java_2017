package com.area.jersey;

import bean.RegToken;
import com.google.api.services.gmail.Gmail;
import facebook4j.*;
import facebook4j.auth.*;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.*;
import java.util.ArrayList;

@Path("/fb")
public class FacebookService {

    private FacebookHelper helper = new FacebookHelper();
    private String appId = "1839753976335300";
    private String appSecret = "27a2ab088d11f66fb58ba3a41923a395";


    /**
     * Call facebook API to get User token, then get the configuration
     * Can be loooong
     * @return Facebook object
     * @param userToken
     */
    private Facebook getFacebook(String userToken)
    {
        ConfigurationBuilder confBuilder = new ConfigurationBuilder();

        confBuilder.setDebugEnabled(true);
        confBuilder.setOAuthAppId(appId);
        confBuilder.setOAuthAppSecret(appSecret);
        confBuilder.setUseSSL(true);
        confBuilder.setJSONStoreEnabled(true);
        Configuration configuration = confBuilder.build();
        FacebookFactory ff = new FacebookFactory(configuration);
        Facebook facebook = ff.getInstance();
        AccessToken token = null;
        facebook.setOAuthAccessToken(new AccessToken(userToken));
        return facebook;
    }


    @Path("post_group")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postOnGroup(FbJsonObject o) throws FacebookException {
        Facebook facebook = getFacebook(o.token);

        if (facebook == null)
            return "{ \"message\": \"Error, not logged to FB\"}";
        try {
            facebook.postGroupStatusMessage(o.id, o.string);
        } catch (FacebookException e)
        {
            e.printStackTrace();
            return "{ \"message\": \"Error\"}";
        }
        return "{ \"message\": \"OK, message posted\"}";
    }

    @Path("feed")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseList<Post> getFeed(FbJsonObject o) throws FacebookException {
        Facebook facebook = getFacebook(o.token);

        if (facebook == null)
            return null;
        try {
            return facebook.getHome();
        } catch (FacebookException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Path("like_post")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String likePost(FbJsonObject o) throws FacebookException {
        Facebook facebook = getFacebook(o.token);

        if (facebook == null)
            return null;
        try {
            facebook.likePost(o.id);
        } catch (FacebookException e)
        {
            e.printStackTrace();
            return null;
        }
        return "{ \"message\": \"OK, post liked\"}";
    }

    @Path("unlike_post")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String unlikePost(FbJsonObject o) throws FacebookException {
        Facebook facebook = getFacebook(o.token);

        if (facebook == null)
            return null;
        try {
            facebook.unlikePost(o.id);
        } catch (FacebookException e)
        {
            e.printStackTrace();
            return null;
        }
        return "{ \"message\": \"OK, post unliked\"}";
    }

    @Path("like_photo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String likePhoto(FbJsonObject o) throws FacebookException {
        Facebook facebook = getFacebook(o.token);

        if (facebook == null)
            return null;
        try {
            facebook.likePhoto(o.id);
        } catch (FacebookException e)
        {
            e.printStackTrace();
            return null;
        }
        return "{ \"message\": \"OK, photo liked\"}";
    }

    @Path("unlike_photo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String unlikePhoto(FbJsonObject o) throws FacebookException {
        Facebook facebook = getFacebook(o.token);

        if (facebook == null)
            return null;
        try {
            facebook.unlikePhoto(o.id);
        } catch (FacebookException e)
        {
            e.printStackTrace();
            return null;
        }
        return "{ \"message\": \"OK, photo unliked\"}";
    }

    @Path("comment_post")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String commentPost(FbJsonObject o) throws FacebookException {
        Facebook facebook = getFacebook(o.token);

        if (facebook == null)
            return null;
        try {
            facebook.commentPost(o.id, o.string);
        } catch (FacebookException e)
        {
            e.printStackTrace();
            return null;
        }
        return "{ \"message\": \"OK, comment send\"}";
    }

    @Path("comment_photo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String commentPhoto(FbJsonObject o) throws FacebookException {
        Facebook facebook = getFacebook(o.token);

        if (facebook == null)
            return null;
        try {
            facebook.commentPhoto(o.id, o.string);
        } catch (FacebookException e)
        {
            e.printStackTrace();
            return null;
        }
        return "{ \"message\": \"OK, comment send\"}";
    }

    @Path("search_group")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<FbObject> searchGroup(FbJsonObject o) throws FacebookException {
        Facebook facebook = getFacebook(o.token);

        if (facebook == null)
            return null;
        try {
            ResponseList<Group> re = facebook.searchGroups(o.string);
            ArrayList<FbObject> ret = new ArrayList<FbObject>();
            for (Group r:
                 re) {
                FbObject obj = new FbObject();
                obj.name = r.getName();
                obj.id = r.getId();
                ret.add(obj);
            }
            return ret;
        } catch (FacebookException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Path("post")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response postOnFb(@Context HttpServletRequest req, @Context HttpServletResponse response, @QueryParam("string") String url) throws FacebookException, Exception {
        try {
            Facebook facebook = getFacebook(RegToken.getTokenFb(req.getSession()));
        if (facebook == null)
            return Response.status(303).entity("Error while login to facebook").build();
            facebook.postStatusMessage(url);
        } catch (FacebookException e)
        {
            return Response.status(303).entity(e.getErrorMessage()).build();
        }
        try {

            Gmail service = GmailService.login();

            GmailEmail.sendMessage(service, "me", GmailEmail.createEmail(

                    GmailEmail.getEmailAddress(service), "notifications-area@gmail.com",

                    GmailEmail.getSubjectFb(url), GmailEmail.getBodyTextFb(url, url)));

        }   catch (Exception e) {

            e.printStackTrace();

        }
        response.sendRedirect("http://127.0.0.1:8080/area/twitter/postStatus?msg=" + url);
        return Response.status(201).entity("OK, string " + url + " posted").build();
    }


    @Path("getToken")
    @GET
    public Response getUserToken(@Context HttpServletRequest request) throws Exception
    {
        Facebook facebook = new FacebookFactory().getInstance();

        facebook.setOAuthAppId(appId, appSecret);
        URI targetURIForRedirection = new URI(facebook.getOAuthAuthorizationURL("http://localhost:8080/area/fb/tokeniser"));
        return (Response.seeOther(targetURIForRedirection).build());
    }

    @GET
    @Path("tokeniser")
    @Produces(MediaType.APPLICATION_JSON)
    public AccessToken publishUserToken(@Context HttpServletRequest req, @QueryParam("code") String c) throws FacebookException, URISyntaxException, Exception {
        Facebook facebook = new FacebookFactory().getInstance();

        facebook.setOAuthAppId(appId, appSecret);
        facebook.getOAuthAuthorizationURL("http://localhost:8080/area/fb/tokeniser&scope=email,public_profile");
        try {
            System.out.println("name : " + req.getSession().getAttribute("name"));
            AccessToken userToken = facebook.getOAuthAccessToken(c);
            RegToken.setTokenFb(req.getSession(), userToken.getToken());
            return (userToken);
        } catch (FacebookException e) {
            e.printStackTrace();
            return (null);
        }
    }

}
