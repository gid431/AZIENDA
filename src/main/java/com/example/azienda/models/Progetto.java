package com.example.azienda.models;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
public class Progetto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codice_progetto;
	private String nome;
	private double budget;
	private Date data_inizio;
	private Date data_fine_prevista;
	/*	
	@ManyToMany(mappedBy = "progetti")
	private Set<Dipendente> dipendenti = new HashSet<>();
	 
     * collezione Set di oggetti Dipendete all'interno dell'entità Progetto, 
     * che rappresenta tutti gli sviluppatori associati a 
     * uno specifico progetto.
    */
	
	//get and set methods
	public Long getCodice_progetto() {
		return codice_progetto;
	}
	/*public Set<Dipendente> getDipendenti() {
		return dipendenti;
	}
	public void setDipendenti(Set<Dipendente> dipendenti) {
		this.dipendenti = dipendenti;
	}*/
	public void setCodice_progetto(Long codice_progetto) {
		this.codice_progetto = codice_progetto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}
	public Date getData_inizio() {
		return data_inizio;
	}
	public void setData_inizio(Date data_inizio) {
		this.data_inizio = data_inizio;
	}
	public Date getData_fine_prevista() {
		return data_fine_prevista;
	}
	public void setData_fine_prevista(Date data_fine_prevista) {
		this.data_fine_prevista = data_fine_prevista;
	}
	
}
