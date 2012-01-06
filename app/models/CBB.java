package models;

import java.sql.Date;

import javax.persistence.Entity;

import play.data.validation.*;
import play.db.jpa.Model;

@Entity
public class CBB extends Model {
	
	@Required
	public String cadena;
	
	@Required
	@MaxSize(50)
	public String serie;
	
	@Required
	public Integer noAprobacion;
	
	@Required
	public Integer folioInicial;
	
	@Required
	public Integer folioFinal;
	
	@Required
	public Integer folioSiguiente;
	
	@Required
	public CBBStatus status;
	
	@Required
	public Date fechaVigencia;
	
	@Required
	public Date fechaAprobacion;
	
	@Required
	public Date created;
	
	public String toString(){
		return "CBB (" + this.serie + this.folioInicial + "-" + this.folioFinal + ")";
	}
	
}
