package com.github.gatekeeper4j;

import org.eclipse.microprofile.config.Config;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.io.UnsupportedEncodingException;
import java.net.*;

@Path("/github")
public class GitHubGateKeeper {

    @Inject
    private Config config;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/")
    public String getCode(@QueryParam("code") String code) throws UnsupportedEncodingException {
        Client client = ClientBuilder.newClient();
        Form form = new Form();
        form.param("client_id", config.getValue("github.oauth_client_id", String.class));
        form.param("client_secret", config.getValue("github.oauth_client_secret", String.class));
        form.param("code", code);
        String result =  client.target(getUrl())
                .request(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .accept("text/plain")
                .post(Entity.form(form), String.class);
        return paramJson(URLDecoder.decode(result, "UTF-8"));
    }



    @GET
    @Path("/autenticar")
    public Response autenticar() throws UnsupportedEncodingException, URISyntaxException {
        Client client = ClientBuilder.newClient();
        String code = client.target(getUrl())
                .queryParam("client_id", config.getValue("github.oauth_client_id", String.class))
                .queryParam("redirect_uri", URLDecoder.decode(config.getValue("github.redirect_uri", String.class), "UTF-8") )
                .getUri().toString();
        return Response.status(Response.Status.MOVED_PERMANENTLY).location(new URI(code)).build();
    }

    public String  getUrl(){
       return config.getOptionalValue("github.oauth_host", String.class).get()+
                ""+config.getOptionalValue("github.oauth_path", String.class).get();

    }

    public static String paramJson(String paramIn) {
        paramIn = paramIn.replaceAll("=", "\":\"");
        paramIn = paramIn.replaceAll("&", "\",\"");
        return "{\"" + paramIn + "\"}";
    }
}