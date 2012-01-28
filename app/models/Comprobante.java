package models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.*;
import play.db.jpa.Model;

@Entity
public class Comprobante extends Model {
	
	public enum tiposDeComprobantes {FACTURA, RECIBO_HONORARIOS, RECIBO_ARRENDAMIENTO}
	
	public tiposDeComprobantes tipo;
	
	@Required
	@Valid
	@OneToOne(targetEntity = Emisor.class, cascade = CascadeType.ALL)
	public Emisor emisor;
	
	@Required
	@Valid
	@OneToOne(targetEntity = Cliente.class, cascade = CascadeType.ALL)
	public Cliente cliente;
	
	@Required
	@Valid
	@OneToOne(targetEntity = CBB.class, cascade = CascadeType.ALL)
	public CBB cbb;
	
	public String serie;
	
	public Integer folio;
	
	@Required
	@OneToMany(targetEntity = Concepto.class, cascade = CascadeType.ALL)
	public Collection<Concepto> conceptos;
	
	private Double subTotal = 0.00;
	private Double totalImpuestosRetenidos = 0.00;
	private Double totalImpuestosTrasladados = 0.00;
	private Double total;
	
	public Comprobante() {
		
	}
	
	public Comprobante(Emisor emisor, Cliente cliente, Collection<Concepto> conceptos, CBB cbb, String Serie, Integer folio){
		this.emisor = emisor;
		this.cliente = cliente;
		this.conceptos = conceptos;
		this.cbb = cbb;
		this.tipo = tiposDeComprobantes.FACTURA;
		this.serie = Serie;
		this.folio = folio;
		
		this.subTotal = getSubTotal();
		this.totalImpuestosRetenidos = getTotalImpuestosRetenidos();
		this.totalImpuestosTrasladados = getTotalImpuestosTrasladados();
		this.total = getTotal();
	}
	
	public Comprobante(Emisor emisor, Cliente cliente, Collection<Concepto> conceptos, CBB cbb, String Serie, Integer folio, tiposDeComprobantes tipo){
		this.emisor = emisor;
		this.cliente = cliente;
		this.conceptos = conceptos;
		this.cbb = cbb;
		this.tipo = tipo;
		this.serie = Serie;
		this.folio = folio;
		
		this.subTotal = getSubTotal();
		this.totalImpuestosRetenidos = getTotalImpuestosRetenidos();
		this.totalImpuestosTrasladados = getTotalImpuestosTrasladados();
		this.total = getTotal();
	}
	
	public Double getTotalImpuestosRetenidos(){
		// No Implementado
		return 0.00;
	}
	
	public Double getTotalImpuestosTrasladados(){
		Double sumaImpuestosTrasladados = 0.00;
		for(Concepto concepto : this.conceptos){
			sumaImpuestosTrasladados += concepto.getImporteIVATrasladado();
		}
		return sumaImpuestosTrasladados;
	}
	
	public Double getSubTotal(){
		Double sumaImportes = 0.0;
		for(Concepto concepto : this.conceptos ){
			sumaImportes += concepto.getImporte();
		}
		return sumaImportes;
	}
	
	public Double getTotal(){
		return getSubTotal() + getTotalImpuestosTrasladados() - getTotalImpuestosRetenidos();
	}
	
	public String getTipoAsString(){
		switch(this.tipo){
		case FACTURA:
			return "FACTURA";
		case RECIBO_HONORARIOS:
			return "RECIBO DE HONORARIOS";
		case RECIBO_ARRENDAMIENTO:
			return "RECIBO DE ARRENDAMIENTO";
		default:
			return "";
		}
	}
	
	public boolean esValido() throws Exception{
		for(Concepto concepto : this.conceptos){
			if(!concepto.esValido()) return false;
		}
		
		if (this.emisor.rfc != this.cbb.obtenerRFC())
			return false;
		
		return (getTotal() > 0 && this.conceptos.size() > 0 && this.cliente.esValido() && this.emisor.esValido() && this.cbb.esValido());
	}
}
