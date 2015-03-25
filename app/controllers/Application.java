package controllers;

import models.LoginFacebook;
import models.Template;
import models.Usuario;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

    //private static GenericDAO dao = new GenericDAO();
    private static LoginFacebook loginFacebook = new LoginFacebook();

    @Transactional
    public static Result index() {
        Usuario u = Sistema.getUsuarioPorEmail(session().get("email"));
        return ok(index.render("", Sistema.getListaTodosTemplates(), u));
    }

    @Transactional
    public static Result viewTemplate(String id) {
        Long idt = Long.parseLong(id);
        Template t = Sistema.getTemplate(idt);
        return ok(viewitem.render("", t));
    }

    public static Result logarComFacebook() {
        return redirect(loginFacebook.getLoginRedirectURL());
    }

    @Transactional
    public static Result logarComFace(String code) throws IOException {
        Logger.info("CODE:" + code);
        Usuario ufb = loginFacebook.obterUsuarioFacebook(code);
        Usuario us = Sistema.getUsuarioPorEmail(ufb.getEmail());
        if (us == null) {
            Sistema.addUsuario(ufb.getNome(), ufb.getEmail(), "12345", ufb.getUrl_picture());
        }
        if (us != null) {
            session().clear();
            session("email", us.getEmail());
            return ok();
        } else {
            return ok(login.render(""));
        }
    }

    public static Result renderLogin() {
        return ok(login.render(""));
    }

    @Transactional
    public static Result registerUsuarioNovo() {
        DynamicForm r = Form.form().bindFromRequest();
        String nome = r.get("nome");
        String senha = r.get("senha");
        String email = r.get("email");
        String pic = "https://en.opensuse.org/images/0/0b/Icon-user.png";
        if (Sistema.addUsuario(nome, email, senha, pic)) {
            return ok(login.render(""));
        } else {
            return renderRegister();
        }
    }

    @Transactional
    public static Result signout() {
        session().clear();
        return index();
    }

    @Transactional
    public static Result authenticate() {
        DynamicForm r = Form.form().bindFromRequest();
        String email = r.get("email");
        String senha = r.get("senha");
        Usuario u = Sistema.getUsuarioPorEmail(email);
        if (u != null) {
            if (u.getSenha().equals("admin") && u.getEmail().equals("admin@admin.com")) {
                session().clear();
                session().put("email", email);
                return renderDashboard();
            }
            if (u.getSenha().equals(senha)) {
                session().clear();
                session().put("email", email);
                return ok(index.render("", Sistema.getListaTodosTemplates(), u));
            }
        }
        return redirect("/login");
    }

    public static Result renderRegister() {
        return ok(register.render(""));
    }

    @Transactional
    public static Result renderDashboard() {
        Usuario u = Sistema.getUsuarioPorEmail(session().get("email"));
        return ok(dashboard.render("", Sistema.getListaTodosTemplates(), Sistema.getListaTodosUsuarios(), u));
    }

    @Transactional
    public static Result addTema() {
        DynamicForm r = Form.form().bindFromRequest();
        if (Sistema.addTemplate(r.get("titulo"), r.get("preview"), r.get("download"), r.get("picture"),
                r.get("tag"), r.get("categoria"), r.get("texto"), Integer.parseInt(r.get("tipo")))) {

            return renderTable(1);
        }
        return renderAddTemplate();
    }
    @Transactional
    public static Result renderTable(int ind) {
        Logger.info("INDICE:"+ ind);
        Usuario u = Sistema.getUsuarioPorEmail(session().get("email"));
        List<Template> l = Sistema.getListaTodosTemplates();
        int resto = (l.size())%30;
        if (l.size() <= 30) {
            return ok(tablelist.render("", l, u));
        } else if(resto==0 && ind!=Sistema.getIndicesTable()){
            int ini = 30 * (ind - 1);
            int k = ini + 30;
            List<Template> ln = new ArrayList<>();
            for (int i = ini; i < k; i++){
                ln.add(l.get(i));
            }
            return ok(tablelist.render("",ln,u));
        }else if(resto!=0 && ind!=Sistema.getIndicesTable() ){
            int ini = 30 * (ind - 1);
            int k = ini + 30;
            List<Template> ln = new ArrayList<>();
            for (int i = ini; i < k; i++){
                ln.add(l.get(i));
            }
            return ok(tablelist.render("",ln,u));
        }else{
            int ini = 30 * (ind - 1);
            int k = ini + resto;
            List<Template> ln = new ArrayList<>();
            for (int i = ini; i < k; i++){
                ln.add(l.get(i));
            }
            return ok(tablelist.render("",ln,u));
        }

    }

    @Transactional
    public static Result deleteTemplate(String id){
        Long idt = Long.parseLong(id);
        if(Sistema.removeTemplate(idt)){
            return renderTable(1);
        }
        return renderDashboard();
    }

    @Transactional
    public static Result renderAddTemplate() {
        Usuario u = Sistema.getUsuarioPorEmail(session().get("email"));
        return ok(addTemplate.render("", u));
    }

}
