package me.tomassetti;
 
import com.beust.jcommander.JCommander;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import me.tomassetti.handlers.*;
import me.tomassetti.model.Model;
import me.tomassetti.sql2omodel.Sql2oModel;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.UUID;
import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import static spark.Spark.port;

/**
 * <p>BlogService class.</p>
 *
 * @author ftomassetti
 * @author rajakolli
 * @version 1:0
 */
public class BlogService 
{

    private static final Logger LOGGER = Logger.getLogger(BlogService.class.getCanonicalName());
    
    private BlogService(){
        
    }

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main( String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, args);

        LOGGER.finest("Options.debug = " + options.debug);
        LOGGER.finest("Options.database = " + options.database);
        LOGGER.finest("Options.dbHost = " + options.dbHost);
        LOGGER.finest("Options.dbUsername = " + options.dbUsername);
        LOGGER.finest("Options.dbPort = " + options.dbPort);
        LOGGER.finest("Options.servicePort = " + options.servicePort);

        port(options.servicePort);

        Sql2o sql2o = new Sql2o("jdbc:postgresql://" + options.dbHost + ":" + options.dbPort + "/" + options.database,
                options.dbUsername, options.dbPassword, new PostgresQuirks() {
            {
                // make sure we use default UUID converter.
                converters.put(UUID.class, new UUIDConverter());
            }
        });

        Model model = new Sql2oModel(sql2o);
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration();
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(BlogService.class, "/"));
        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);

        // insert a post (using HTTP post method)
        post("/posts", new PostsCreateHandler(model));

        // get all post (using HTTP get method)
        get("/posts", new PostsIndexHandler(model));

        get("/posts/:uuid", new GetSinglePostHandler(model));

        put("/posts/:uuid", new PostsEditHandler(model));

        delete("/posts/:uuid", new PostsDeleteHandler(model));

        post("/posts/:uuid/comments", new CommentsCreateHandler(model));

        get("/posts/:uuid/comments", new CommentsListHandler(model));

        get("/alive", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "ok";
            }
        });
    }
}
