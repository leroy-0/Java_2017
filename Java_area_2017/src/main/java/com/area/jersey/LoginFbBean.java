package com.area.jersey;

import bean.FbLoginProvider;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

@Path("/login")
public class LoginFbBean {

    private FacebookHelper helper = new FacebookHelper();
    public String tok;
    private String appId = FbLoginProvider.appId;
    private String appSecret = FbLoginProvider.appSecret;
    @GET
    @Path("fb")
    public Response getUserToken() throws Exception
    {
        Facebook facebook = new FacebookFactory().getInstance();

        facebook.setOAuthAppId(appId, appSecret);
        URI targetURIForRedirection = new URI(facebook.getOAuthAuthorizationURL("http://127.0.0.1:8080/regfb.jsp&scope=email,public_profile"));
        return (Response.seeOther(targetURIForRedirection).build());
    }

}
