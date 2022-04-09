package io.dxmrs.crwler.controller;

import io.quarkus.logging.Log;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("")
public class HelloResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance hello(String name);
        public static native TemplateInstance search(String name);
        public static native TemplateInstance results(String name, String body);
    }

    @GET
    @Path("")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance homePage() {
        Log.info("GET: HOME PAGE");
        return Templates.hello("toi");
    }

    @GET
    @Path("/search")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance search(@QueryParam("name") String name) {
        Log.info("GET /search : name=" + name);
        if (null == name || name.isEmpty()){
            return Templates.search("Anon");
        }
        return Templates.search(name);
    }

    @GET
    @Path("/result/{name}")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_PLAIN)
    public TemplateInstance getResults(@PathParam("name") String name, @QueryParam("target") String target){
        Log.info("GET /results from user " + name + ", target= " + target);
        return Templates.results(name, target);
    }
}
