package com.area.jersey;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/RSS")
public class RSSAddService {

    @GET
    @Path("add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postString(@QueryParam("add") String s) {
        if (RSSFileHandler.checkFile()) {
            try {
                if (RSSFileHandler.addLine(s)) {
                    return Response.status(201).entity("Le flux a ete ajoute avec succes").build();
                }
                else {
                    return Response.status(201).entity("Le flux existe deja").build();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Response.status(201).entity("").build();
    }
}
