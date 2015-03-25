import controllers.Sistema;
import models.GenericDAO;
import models.Template;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

/**
 * Created by caio on 24/03/15.
 */

public class Global extends GlobalSettings {

    private static GenericDAO dao = new GenericDAO();

    @Override
    public void onStart(Application app) {
        Logger.info("inicializada...");

        JPA.withTransaction(new play.libs.F.Callback0() {

            public void invoke() throws Throwable {

                List<Template> lis = dao.findAllByClassName(Template.class.getName());
                if (lis.size() == 0) {
                    for(int i=0;i<24;i++){
                        Sistema.addTemplate("Titulo"+i,"","","assets/html5up-verti/images/pic02.jpg","","","",0);
                    }
                    Logger.info("Inserindo dados no BD.");
                }
            }
        });
    }

    public void onStop(Application app) {
        Logger.info("desligada...");
    }

}