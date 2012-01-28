package controllers;

import play.*;

import static play.modules.pdf.PDF.*;
import play.data.validation.*;
import play.data.validation.Error;
import play.data.validation.Validation.ValidationResult;
import play.db.jpa.Blob;
import play.mvc.*;
import play.vfs.VirtualFile;

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
    		if (entity == null) {
    			throw new Exception();
    		}
    		
    		CBB recibido = new CBB();
    		recibido.image = entity;
    		recibido.setDatos(recibido.obtenerCadena());
    		renderJSON(recibido);
		} catch (Exception e){
			e.printStackTrace();
			renderJSON(new Gson().toJson(new models.Error(1, "Imposible leer imagen, verifique el formato y pruebe nuevamente")));
		}
    }
    
    public static void emitir(Comprobante comprobante) {
    	try {
    		if (comprobante.cbb == null) {
    			validation.addError("comprobante.cbb.image", "Seleccione su imagen CBB");
    			throw new Exception("Imagen CBB no seleccionada");
    		}
    		
    		comprobante.cbb.obtenerCadena();
    		
    		validation.valid(comprobante);
    		
    		if (!comprobante.emisor.rfc.equalsIgnoreCase((comprobante.cbb.obtenerRFC()))) {
    			validation.addError("comprobante.emisor.rfc", "RFC no coincide con CBB");
    		}
    		
    		if(validation.hasErrors()) {
    			for(Error error : validation.errors()) {
    				Logger.info("@ " + error.message());
    			}
    			
    			render("@index", comprobante);
    		}
    		
    		comprobante.save();
    		imprimir(comprobante.id);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Logger.error(ex.getMessage());
    		render("@index", comprobante);
    	}
    }
    
    public static void imprimir(Long id) {
    	Comprobante comprobante = Comprobante.findById(id);
    	Options options = new Options();
		options.filename = "emision.pdf";
    	renderPDF(comprobante, options, "prueba");
    }
    
    public static void leerImagen(Long id) {
    	CBB cbb = CBB.findById(id);
    	response.setContentTypeIfNotSet(cbb.image.type());
    	renderBinary(cbb.image.get());
    }
}