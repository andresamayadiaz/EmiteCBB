package controllers;

import play.*;
import play.data.validation.*;
import play.data.validation.Error;
import play.data.validation.Validation.ValidationResult;
import play.db.jpa.Blob;
import play.mvc.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import net.sf.oval.guard.PostCheck;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void leerCBB(Blob entity){
    	Gson gson = new Gson();
    	CBB cbb = new CBB();
    	try {
    		Logger.info("CBB: ", entity.getUUID());
    		cbb.image = entity;
    		CBB cbb2 = new CBB();
			cbb2.setDatos(cbb.obtenerCadena());
			
			//Logger.debug("CBB VIGENTE: " + cbb.esVigente());
			Logger.debug("CBB VALIDO: " + cbb.esValido());
    		Logger.debug("CBB RECIBIDO: " + gson.toJson(cbb));
    		
			renderJSON(gson.toJson(cbb2));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderJSON(gson.toJson(new models.Error(2, e.toString())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderJSON(gson.toJson(new models.Error(3, e.toString())));
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderJSON(gson.toJson(new models.Error(4, e.toString())));
		} catch (Exception e){
			e.printStackTrace();
			renderJSON(gson.toJson(new models.Error(5, "Imposible leer imagen, verifique el formato y pruebe nuevamente")));
		}
    }
    
    public static void emitir(@Valid Comprobante comprobante) {
    	try {
    		validation.valid("emisor", comprobante.emisor);
    		validation.valid("cliente", comprobante.cliente);
    		
    		if(validation.hasErrors()) {
    			Logger.debug("Entra");
    			
    			params.flash(); // add http parameters to the flash scope
    			validation.keep(); // keep the errors for the next request
    			
    			for(Error error : validation.errors()) {
    				Logger.debug(error.getKey());
    			}
    			
    			index();
    	      }
    		
	    	// Verificar validación
	    	Logger.debug("COMPROBANTE RECIBIDO: " + new Gson().toJson(comprobante));
	    	//Logger.info("COMPROBANTE VALIDO: " + comprobante.esValido());
	    	
	    	// Leer CBB
	    	CBB myCbb = new CBB();
	    	myCbb.setDatos(comprobante.cbb.obtenerCadena());
	    	Logger.debug("CBB JSON: " + new Gson().toJson(myCbb));
	    	
	    	index();
    	} catch (Exception ex) {
    		Logger.info(ex.getMessage());
    		index();
    	}
    }
}