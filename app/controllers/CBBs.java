package controllers;

import play.Logger;
import play.db.jpa.Blob;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.ivy.plugins.repository.LazyResource;

import models.CBB;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;

import com.google.gson.Gson;
import com.google.zxing.*;
import com.google.zxing.client.*;
import com.google.zxing.client.j2se.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.sun.xml.internal.fastinfoset.Decoder;

public class CBBs extends Controller {
	
	public static void nuevo(CBB entity){
		render();
	}
	
	public static void leer(CBB entity){
		//Logger.info("Entity Type: "+entity.type());
		try {
			File file = entity.image.getFile();
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(file)))));
			
			Result result = new MultiFormatReader().decode(binaryBitmap);
			entity.setDatos(result.getText());
			//Gson gson = new Gson();
			//String entityJson = gson.toJson(entity);
			//Logger.info("JSON: " + entityJson);
			save(entity);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
    public static void index() {
        List<CBB> entities = models.CBB.all().fetch();
        render(entities);
    }
    
    public static void create(CBB entity) {
        render(entity);
    }
    
    public static void show(java.lang.Long id) {
        CBB entity = CBB.findById(id);
        render(entity);
    }
    
    public static void edit(java.lang.Long id) {
        CBB entity = CBB.findById(id);
        render(entity);
    }
    
    public static void delete(java.lang.Long id) {
        CBB entity = CBB.findById(id);
        entity.delete();
        index();
    }
    
    public static void save(@Valid CBB entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@create", entity);
        }
        entity.save();
        flash.success(Messages.get("scaffold.created", "CBB"));
        index();
    }
    
    public static void update(@Valid CBB entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@edit", entity);
        }
        
              entity = entity.merge();
        
        entity.save();
        flash.success(Messages.get("scaffold.updated", "CBB"));
        index();
    }
}
