package com.area.jersey;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;


@Path("/RSS")
public class RSSService {

    @GET
    @Path("aff")
    @Produces("text/html; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response aff(@QueryParam("rss") String s) {

        URL url = null;
        InputStream is = null;
        BufferedReader br;
        String line;
        String messages = null;
        List<String> list;

        if (RSSFileHandler.checkFile()) {
            list = RSSFileHandler.readFile("./.FluxRss.txt");
            for (int i = 0; i < list.size(); i++) {
                try {
                    if (list.get(i).contains(s)) {
                        url = new URL(list.get(i));
                        is = url.openStream();
                        br = new BufferedReader(new InputStreamReader(is));

                        line = br.readLine();
                        messages = line;
                        while ((line = br.readLine()) != null) {
                            if (line != null) {
                                messages += line;
                            }
                        }
                    }
                }
                catch(IOException ioe){
                    ioe.printStackTrace();
                }
                finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException ignored) {
                    }
                }
            }
        }
        if (messages == null) {
            return Response.status(201).entity("vous n'etes abonne a aucun flux comportant ce nom").build();
        } else {
            return Response.status(201).entity(messages).build();
        }
    }
}