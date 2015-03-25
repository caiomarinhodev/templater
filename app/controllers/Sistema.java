package controllers;

import models.GenericDAO;
import models.Template;
import models.Usuario;
import play.db.jpa.Transactional;

import java.util.List;

/**
 * Created by caio on 24/03/15.
 */
public class Sistema {

    public static GenericDAO dao = new GenericDAO();

    @Transactional
    public static int getIndice(){
        return ((getListaTodosTemplates().size()/3)-1);
    }

    @Transactional
    public static int getIndicesTable(){
        List<Template> l = getListaTodosTemplates();
        int total = l.size();
        int resto = total%30;
        float conta = ((float)l.size())/(float)30;
        if(conta>1 && resto==0){
            return (getListaTodosTemplates().size()/30);
        }else if(conta>1 && resto!=0){
            return (getListaTodosTemplates().size()/30)+1;
        }else{
            return 1;
        }

    }

    @Transactional
    public static boolean addUsuario(String nome, String email, String senha, String url_picture){
        Usuario u = new Usuario(nome, email,senha,url_picture);
        if(!existeUsuario(u)){
            dao.persist(u);
            dao.flush();
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    public static List<Usuario> getListaTodosUsuarios(){
        return dao.findAllByClassName(Usuario.class.getName());
    }

    @Transactional
    public static Usuario getUsuarioPorId(Long id){
        List<Usuario> li = dao.findByAttributeName(Usuario.class.getName(), "idUsuario", String.valueOf(id));
        if(li.size()>0){
            return li.get(0);
        }
        return null;

    }

    @Transactional
    public static float porcentagemTemplates(){
        List<Template> lt = getListaTodosTemplates();
        List<Usuario> lu = getListaTodosUsuarios();
        float t = (float)lt.size();
        float u = (float)lu.size();
        return (100-(((t+u)*100)/10000));
    }

    @Transactional
    public static int totalDownloads(){
        List<Template> li = getListaTodosTemplates();
        int k = 0;
        for(Template t: li){
            k = k+t.getDownloads();
        }
        return k;
    }

    @Transactional
    public static Usuario getUsuarioPorEmail(String email){
        List<Usuario> li = dao.findByAttributeName(Usuario.class.getName(), "email", email);
        if(li.size()>0){
            return li.get(0);
        }
        return null;
    }

    @Transactional
    private static boolean existeUsuario(Usuario u){
        List<Usuario> li = dao.findAllByClassName(Usuario.class.getName());
        for (int i=0; i<li.size(); i++){
            if(u.getEmail().equals(li.get(i).getEmail()) && u.getNome().equals(li.get(i).getNome())){
                return true;
            }
        }return false;
    }

    /**
     * Aqui termina todos os metodos para Usuario.
     */

    @Transactional
    public static boolean addTemplate(String titulo, String url_preview,
                                      String url_download, String url_picture, String tag,
                                      String categorie, String text, int type){
        Template t = new Template(titulo, url_preview,url_download,url_picture, tag,categorie,text,type);
        if(!existeTemplate(t)){
            dao.persist(t);
            dao.flush();
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    public static List<Template> getListaTodosTemplates(){
        return dao.findAllByClassName(Template.class.getName());
    }

    @Transactional
    public static Template getTemplate(Long id){
        List<Template> li = dao.findByAttributeName(Template.class.getName(),"idTemplate",String.valueOf(id));
        if(li.size()>0){
            return li.get(0);
        }
        return null;
    }

    @Transactional
    public static List<Template> getListaTemplatesFiltrada(int type){
        return dao.findByAttributeName(Template.class.getName(),"type", String.valueOf(type));
    }

    @Transactional
    public static List<Template> getListaTemplatesPorCategoria(String categoria){
        return dao.findByAttributeName(Template.class.getName(), "categorie", categoria);
    }

    @Transactional
    public static boolean removeTemplate(Long id){
        Template t = getTemplate(id);
        if(t!=null){
            dao.remove(t);
            dao.flush();
            return true;
        }
        return false;
    }

    @Transactional
    private static boolean existeTemplate(Template t){
        List<Template> li = dao.findAllByClassName(Template.class.getName());
        for (int i=0; i<li.size(); i++){
            if(t.getTitulo().equals(li.get(i).getTitulo())){
                return true;
            }
        }return false;
    }


}
