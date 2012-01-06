package models;

import javax.persistence.Entity;

import play.data.validation.*;
import play.db.jpa.Model;

@Entity
public class Emisor extends Model {

	@Required
	@MaxSize(15)
	public String rfc;
	
	@Required
	@MaxSize(250)
	public String razonsocial;
	
	@Required
	@MaxSize(250)
	public String calle;
	
	@Required
	@MaxSize(50)
	public String noExterior;
	
	@MaxSize(50)
	public String noInterior;
	
	@Required
	@MaxSize(250)
	public String colonia;
	
	@Required
	@MaxSize(250)
	public String codigoPostal;
	
	@Required
	@MaxSize(250)
	public String municipio;
	
	@Required
	@MaxSize(250)
	public String estado;
	
	@Required
	@MaxSize(250)
	public String pais;
	
	@Required
	@Email
	@MaxSize(250)
	public String email;
	
	public boolean esValido(){
		return true;
	}
	
}
