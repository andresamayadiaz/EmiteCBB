package models;

import javax.persistence.Entity;

import play.data.validation.*;
import play.db.jpa.Model;

@Entity
public class Cliente extends Model {
	
	@Required(message = "Especifique rfc")
	@MaxSize(15)
	public String rfc;
	
	@Required(message = "Especifique razón social")
	@MaxSize(250)
	public String razonsocial;
	
	@Required(message = "Especifique calle")
	@MaxSize(250)
	public String calle;
	
	@Required(message = "Especifique número exterior")
	@MaxSize(50)
	public String noExterior;
	
	@MaxSize(50)
	public String noInterior;
	
	@Required(message = "Especifique colonia")
	@MaxSize(250)
	public String colonia;
	
	@Required(message = "Especifique código postal")
	@MaxSize(250)
	public String codigoPostal;
	
	@Required(message = "Especifique municipio")
	@MaxSize(250)
	public String municipio;
	
	@Required(message = "Especifique estado")
	@MaxSize(250)
	public String estado;
	
	@Required(message = "Especifique país")
	@MaxSize(250)
	public String pais;
	
	@Required(message = "Especifique email")
	@Email
	@MaxSize(250)
	public String email;
	
	public boolean esValido(){
		return ((this.rfc.length() > 0) && 
				(this.razonsocial.length() > 0) &&
				(this.calle.length() > 0) &&
				(this.noExterior.length() > 0) &&
				(this.colonia.length() > 0) &&
				(this.codigoPostal.length() > 0) &&
				(this.municipio.length() > 0) &&
				(this.estado.length() > 0) &&
				(this.pais.length() > 0) &&
				(this.email.length() > 0));
	}

}
