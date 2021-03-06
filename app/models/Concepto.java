package models;

import javax.persistence.Entity;

import play.data.validation.*;
import play.db.jpa.Model;

@Entity
public class Concepto extends Model {
	
	@Required(message = "Especifique concepto")
	@MaxSize(250)
	public String concepto;
	
	@Required(message = "Especifique unidad")
	@MaxSize(50)
	public String unidad;
	
	@Required(message = "Especifique cantidad")
	@Min(0)
	public Double cantidad;
	
	@Required(message = "Especifique precio unitario")
	@Min(0)
	public Double precioUnitario;
	
	@Min(0)
	private Double importeIVATrasladado;
	
	@Min(0)
	public Double porcentajeIVATrasladado;
	
	@Required(message = "Especifique importe")
	@Min(0)
	private Double importe;
	
	public Concepto(String concepto, String unidad, Double cantidad, Double precioUnitario){
		this.concepto = concepto;
		this.unidad = unidad;
		this.cantidad = (cantidad.isNaN()) ? 0.00 : cantidad ;
		this.precioUnitario = (precioUnitario.isNaN()) ? 0.00 : precioUnitario ;
		this.porcentajeIVATrasladado = 0.00;
		this.importeIVATrasladado = getImporteIVATrasladado();
		this.importe = getImporte();	
	}
	
	public Concepto(String concepto, String unidad, Double cantidad, Double precioUnitario, Double porcentajeIVATrasladado){
		this.concepto = concepto;
		this.unidad = unidad;
		this.cantidad = (cantidad.isNaN()) ? 0.00 : cantidad ;
		this.precioUnitario = (precioUnitario.isNaN()) ? 0.00 : precioUnitario ;
		this.porcentajeIVATrasladado = (porcentajeIVATrasladado.isNaN()) ? 0.00 : porcentajeIVATrasladado ;
		this.importeIVATrasladado = getImporteIVATrasladado();
		this.importe = getImporte();	
	}
	
	public Double getImporteIVATrasladado(){
		validar();
		return (this.cantidad * this.precioUnitario) * this.porcentajeIVATrasladado / 100;
	}
	
	public Double getImporte(){
		validar();
		return (this.cantidad * this.precioUnitario);
	}
	
	public boolean esValido(){
		validar();
		return (getImporte() > 0 && this.cantidad > 0 && this.precioUnitario > 0);
	}
	
	public void validar(){
		this.cantidad = (this.cantidad == null) ? 0.00 : this.cantidad ;
		this.precioUnitario = (this.precioUnitario == null) ? 0.00 : this.precioUnitario ;
		this.porcentajeIVATrasladado = (this.porcentajeIVATrasladado == null) ? 0.00 : this.porcentajeIVATrasladado ;
	}
	
}
