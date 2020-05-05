package com.area.jersey;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class JerseyService
{

    //localhost:8080/area/hello
    @GET
    public String getMsg()
    {
        //Renvoie une string
        return "Hello World !! - Jersey 2";
    }

    //localhost:8080/area/hello/worldu
    @Path("worldu")
    @GET
    public String putMsg()
    {
        //Renvoie une string
        return "Yamete";
    }


    //C'est clairement cette partie à approfondir vu que l'API va être principalement contactée qu'avec des POST
    //Là c'est vraiment un exemple basique
    //Sur POSTMAN (téléchargement) tu dois   faire ça:
    //https://imgur.com/TtUcWa0
    //localhost:8080/area/hello
    //POST header
    @POST
    //Ici on précise le type de requête reçue (avec @Consumes)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postString(@FormParam("s") String s) {

        //Le @FormParam récupère le paramètre donné dans le formulaire POST
        String result = "Posted String : " + s;
        //Set un header de reponse (ici 201) et une entité
        return Response.status(201).entity(result).build();

    }
}
