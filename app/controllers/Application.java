package controllers;

import models.Template;
import models.Usuario;
import play.*;
import play.api.mvc.Session;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    @Transactional
    public static Result index() {

        return ok(index.render("",Sistema.getListaTodosTemplates(),null));
    }

    public static Result viewTemplate(Long id) {
        return ok(viewitem.render(""));
    }

    public static Result renderLogin() {
        return ok(login.render(""));
    }

    public static Result renderRegister() {
        return ok(register.render(""));
    }

    public static Result renderDashboard() {
        return ok(dashboard.render(""));
    }

    public static Result renderTable() {
        return ok(tablelist.render(""));
    }

    public static Result renderAddTemplate() {
        return ok(addTemplate.render(""));
    }





}
