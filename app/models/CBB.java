package models;

import play.Logger;
import play.db.jpa.Blob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import javax.persistence.Entity;

import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class CBB extends Model {
	
	public Blob image;
	
	@Required
	public String cadena;
	
	@MaxSize(50)
	public String serie;
	
	@Required
	public Integer noAprobacion;
	
	@Required
	public Integer folioInicial;
	
	@Required
	public Integer folioFinal;
	
	@Required
	public String fechaVigencia;
	
	@Required
	public String fechaAprobacion;
	
	public void setDatos(String cadena){
		this.cadena = cadena;
		
		this.fechaAprobacion = obtenerFechaAprobacion();
		this.fechaVigencia = obtenerFechaVigencia();
		this.folioFinal = Integer.parseInt(obtenerFolioFinal());
		this.folioInicial = Integer.parseInt(obtenerFolioInicial());
		this.noAprobacion = Integer.parseInt(obtenerNoAprobacion());
		this.serie = obtenerSerie();
		
	}
	
	public String obtenerCadena() throws Exception{
		String cadena = "";
		
		try {
			File file = this.image.getFile();
			BinaryBitmap binaryBitmap;
			binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(file)))));
			Result result = new MultiFormatReader().decode(binaryBitmap);
			cadena = result.getText();
			setDatos(cadena);
			
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (NotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		
		return cadena;
	}
	
	public String obtenerRFC(){
		if(this.cadena.isEmpty()){
			return null;
		}
		String[] tokens = StringUtils.splitPreserveAllTokens(this.cadena, '|');
		
		/*
		Logger.info("-----------");
		for(String tok : tokens){
			Logger.info(" >> " + tok);
		}
		Logger.info("-----------");
		*/
		
		return tokens[1];
	}
	
	public String obtenerNoAprobacion(){
		if(this.cadena.isEmpty()){
			return null;
		}
		String[] tokens = StringUtils.splitPreserveAllTokens(this.cadena, '|');
		return tokens[2];
	}
	
	public String obtenerFolioInicial(){
		if(this.cadena.isEmpty()){
			return null;
		}
		String[] tokens = StringUtils.splitPreserveAllTokens(this.cadena, '|');
		return tokens[3];
	}
	
	public String obtenerFolioFinal(){
		if(this.cadena.isEmpty()){
			return null;
		}
		String[] tokens = StringUtils.splitPreserveAllTokens(this.cadena, '|');
		return tokens[4];
	}
	
	public String obtenerSerie(){
		if(this.cadena.isEmpty()){
			return null;
		}
		String[] tokens = StringUtils.splitPreserveAllTokens(this.cadena, '|');
		return tokens[5];
	}
	
	public String obtenerFechaAprobacion(){
		if(this.cadena.isEmpty()){
			return null;
		}
		String[] tokens = StringUtils.splitPreserveAllTokens(this.cadena, '|');
		return tokens[6];
	}
	
	public String obtenerFechaVigencia(){
		if(this.cadena.isEmpty()){
			return null;
		}
		String[] tokens = StringUtils.splitPreserveAllTokens(this.cadena, '|');
		return tokens[7];
	}
	
	public String toString(){
		return "CBB (" + this.serie + this.folioInicial + "-" + this.folioFinal + ")";
	}
	
	public boolean esVigente(){
		Date fechaVigencia = new Date(obtenerFechaVigencia());
		Date fechaActual = new Date();
		return ( fechaActual.before(fechaVigencia) );
	}
	
	public boolean esValido(){
		return ((this.cadena.length() > 0) &&
				(obtenerFechaAprobacion().length() > 0) &&
				esVigente());
	}
	
}
