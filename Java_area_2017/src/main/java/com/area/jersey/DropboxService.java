package com.area.jersey;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/dropbox")
public class DropboxService {

    @Path("list")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DropboxListObject list(@FormParam("token") String token) {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("Area").build();
        DbxClientV2 client = new DbxClientV2(config, token);
        DropboxListObject back = new DropboxListObject();

        try {
            ListFolderResult result = client.files().listFolder("");
            while (true) {
                for (Metadata metadata : result.getEntries())
                    back.info.add(metadata.getPathLower());
                if (!result.getHasMore())
                    break;
                result = client.files().listFolderContinue(result.getCursor());
            }
            return (back);
        }
        catch (DbxException a) {
            System.out.println("ERROR -> dropbox failed");
        }
        return (null);
    }

    @Path("upload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String   uploadFile(@FormDataParam("token") String token,
                               @FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileMetaData) {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("Area").build();
        DbxClientV2 client = new DbxClientV2(config, token);
        try {
            FullAccount account = client.users().getCurrentAccount();
            FileMetadata metadata = client.files().uploadBuilder("/" + fileMetaData.getFileName()).uploadAndFinish(uploadedInputStream);
        }
        catch (DbxException a) {
            return ("ERROR -> dropbox failed");
        }
        catch (IOException a) {
            return ("ERROR -> IO failed");
        }
        return ("Upload Successful");
    }

    @Path("upload-local")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String   uploadFile(@FormParam("token") String token,
                               @FormParam("file") String path,
                               @FormParam("dropbox-path") String dropboxName) {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("Area").build();
        DbxClientV2 client = new DbxClientV2(config, token);
        try (InputStream uploadedInputStream = new FileInputStream(path)) {
            FullAccount account = client.users().getCurrentAccount();
            FileMetadata metadata = client.files().uploadBuilder("/" + dropboxName).uploadAndFinish(uploadedInputStream);
        }
        catch (DbxException a) {
            return ("ERROR -> dropbox failed");
        }
        catch (IOException a) {
            return ("ERROR -> IO failed");
        }
        return ("Upload successful");
    }

    @Path("auth-webhook")
    @GET
    public String webhookInit(@QueryParam("challenge") String test)
    {
        return (test);
    }
}