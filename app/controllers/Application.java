package controllers;

import play.*;

import static play.modules.pdf.PDF.*;
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
    	try {
    		CBB recibido = new CBB();
    		CBB respuesta = new CBB();
    		recibido.image = entity;
    		respuesta.setDatos(recibido.obtenerCadena());
    		renderJSON(respuesta);
		} catch (Exception e){
			e.printStackTrace();
			renderJSON(new Gson().toJson(new models.Error(1, "Imposible leer imagen, verifique el formato y pruebe nuevamente")));
		}
    }
    
    public static void emitir(@Valid Comprobante comprobante) {
    	try {
    		validation.valid("emisor", comprobante.emisor);
    		validation.valid("cliente", comprobante.cliente);
    		
    		if(validation.hasErrors()) {
    			params.flash();
    			validation.keep();
    			
    			for(Error error : validation.errors()) {
    	             Logger.debug(error.getKey() + ": " + error.message());
    	        }
    			
    			renderTemplate("Application/index.html", comprobante);
    		}
    		
    		Logger.info("Comprobante: " + new Gson().toJson(comprobante));
	    	
	    	impresion(comprobante);
    	} catch (Exception ex) {
    		Logger.info(ex.getMessage());
    		index();
    	}
    }
    
    public static void impresion(Comprobante comprobante) {
    	Options options = new Options();
		options.filename = "emision.pdf";
    	renderPDF("Application/emitir.html", comprobante);
    }
}