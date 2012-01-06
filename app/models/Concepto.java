package models;

import javax.persistence.Entity;

import play.data.validation.*;
import play.db.jpa.Model;

@Entity
public class Concepto extends Model {
	
	@Required
	@MaxSize(150)
	public String concepto;
	
	@Required
	@MaxSize(50)
	public String unidades;
	
	@Required
	@Min(0)
	public Double cantidad;
	
	@Required
	@Min(0)
	public Double precioUnitario;
	
	@Min(0)
	private Double importeIVATrasladado;
	
	@Min(0)
	public Double porcentajeIVATrasladado;
	
	@Required
	@Min(0)
	private Double importe;
	
	public Concepto(String concepto, String unidades, Double cantidad, Double precioUnitario){
		this.concepto = concepto;
		this.unidades = unidades;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.porcentajeIVATrasladado = 0.00;
		this.importeIVATrasladado = getImporteIVATrasladado();
		this.importe = getImporte();	
	}
	
	public Concepto(String concepto, String unidades, Double cantidad, Double precioUnitario, Double porcentajeIVATrasladado){
		this.concepto = concepto;
		this.unidades = unidades;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.porcentajeIVATrasladado = porcentajeIVATrasladado;
		this.importeIVATrasladado = getImporteIVATrasladado();
		this.importe = getImporte();	
	}
	
	public Double getImporteIVATrasladado(){
		return (this.cantidad * this.precioUnitario) * this.porcentajeIVATrasladado / 100;
	}
	
	public Double getImporte(){
		return (this.cantidad * this.precioUnitario) + getImporteIVATrasladado();
	}
	
	public boolean esValido(){
		return (getImporte() > 0 && this.cantidad > 0);
	}
	
}
