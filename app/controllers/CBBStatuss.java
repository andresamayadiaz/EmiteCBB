package controllers;

import java.util.List;
import models.CBBStatus;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;

public class CBBStatuss extends Controller {
    public static void index() {
        List<CBBStatus> entities = models.CBBStatus.all().fetch();
        render(entities);
    }

    public static void create(CBBStatus entity) {
        render(entity);
    }

    public static void show(java.lang.Long id) {
        CBBStatus entity = CBBStatus.findById(id);
        render(entity);
    }

    public static void edit(java.lang.Long id) {
        CBBStatus entity = CBBStatus.findById(id);
        render(entity);
    }

    public static void delete(java.lang.Long id) {
        CBBStatus entity = CBBStatus.findById(id);
        entity.delete();
        index();
    }
    
    public static void save(@Valid CBBStatus entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@create", entity);
        }
        entity.save();
        flash.success(Messages.get("scaffold.created", "CBBStatus"));
        index();
    }

    public static void update(@Valid CBBStatus entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@edit", entity);
        }
        
              entity = entity.merge();
        
        entity.save();
        flash.success(Messages.get("scaffold.updated", "CBBStatus"));
        index();
    }
}
