package com.area.jersey;

import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

@Path("/RSS")
public class RSSRemoveService {


    @GET
    @Path("remove")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postString(@QueryParam("remove") String s) throws IOException {
        java.nio.file.Path path = Paths.get("./.FluxRSS.txt");
        RSSFileHandler.checkFile();
        File inputFile = new File("./.FluxRss.txt");
        File tempFile = new File("./.myTempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            if (currentLine.equals(s)) {
                continue;
            }
            else {
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }
        writer.close();
        reader.close();
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            System.err.println(x);
        }
        boolean successful = tempFile.renameTo(new File("./.FluxRSS.txt"));
        if (successful) {
            return Response.status(201).entity("link removed succesfully").build();
        }
        else {
            return Response.status(201).entity("link doesn't exist").build();
        }
    }
}
