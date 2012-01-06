package models;

import play.db.jpa.Model;

public class Error extends Model {
	
	public Integer codigoError;
	
	public String mensaje;
	
	public Error(Integer codigoError, String mensaje){
		this.codigoError = codigoError;
		this.mensaje = mensaje;
	}

}
