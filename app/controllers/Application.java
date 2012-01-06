package controllers;

import play.*;
import play.db.jpa.Blob;
import play.mvc.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

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
    	
    	try {
    		
    		File file = entity.getFile();
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(file)))));
			Result result = new MultiFormatReader().decode(binaryBitmap);
			String[] tokens = StringUtils.splitPreserveAllTokens(result.getText(), '|');
			if(tokens.length<=0 || tokens.length>9){
				// existe un error con los datos leidos
				renderJSON(gson.toJson(new models.Error(1, "La imagen no tiene formato CBB correcto.")));
			}
			
			CBB cbb = new CBB();
			cbb.setDatos(result.getText());
			renderJSON(gson.toJson(cbb));
			
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
    
    public static void emitirComprobante(Comprobante comprobante){
    	
    	
    	
    }

}