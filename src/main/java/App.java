import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args){

        ProcessBuilder process = new ProcessBuilder();
        Integer port;


        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);


        staticFileLocation("/public");
        String layout ="templates/layout.vtl";

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("stylists", Stylist.all());
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


        get("/client", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("stylists", Stylist.all());
            model.put("template", "templates/client.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/client", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            int phone = Integer.parseInt(request.queryParams("phone"));
            Stylist newStylist = new Stylist(name,phone);
            newStylist.save();
            model.put("stylists", Stylist.all());
            model.put("template", "templates/client.vtl");
            response.redirect("/client");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        get("/stylists/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Client client = Client.find(Integer.parseInt(request.params(":id")));
            model.put("client", client);
            model.put("template", "templates/clients.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        post("/stylists/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            try{
                Stylist stylistid = Stylist.find(Integer.parseInt(request.queryParams("stylistid")));
                String name = request.queryParams("name");
                Client newClient = new Client(name,stylistid.getId());
                newClient.save();
                String url = String.format("/stylists/%d", stylistid.getId());
                response.redirect(url);
            } catch(NumberFormatException e ) {
                Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
                String name = request.queryParams("name");
                stylist.update(name);
                String url = String.format("/stylists/%d", stylist.getId());
                response.redirect(url);
            }
            model.put("template", "templates/stylist.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/stylists/:id/delete", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
            stylist.delete();
            model.put("stylist", stylist);
            model.put("stylists", Stylist.all());
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        get("/stylists/:id/clients/:clientid", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
            Client client = Client.find(Integer.parseInt(request.params(":clientid")));
            model.put("stylist", stylist);
            model.put("client",client);
            model.put("template", "templates/client.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        post("/stylists/:id/clients/:clientid", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Client client = Client.find(Integer.parseInt(request.params(":clientid")));
            String name = request.queryParams("name");
            Stylist stylist = Stylist.find(client.getStylistId());
            client.update(name);
            String url = String.format("/stylists/%d/clients/%d", stylist.getId(), client.getId());
            response.redirect(url);
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        post("/stylists/:id/clients/:clientid/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Client client = Client.find(Integer.parseInt(request.params(":clientid")));
            Stylist stylist = Stylist.find(client.getStylistId());
            client.delete();
            String url = String.format("/stylists/%d", stylist.getId());
            response.redirect(url);
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
    }
}