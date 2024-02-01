package com.example.azienda.models;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;

@Entity
public class Dipendente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codice_dipendente;
    private String nome;
    private String cognome;
    private String competence;
    private String mansione;
    private double stipendio;
    private Date data_nascita;
    private String stato_nascita;
    private String provincia_nascita;
    private String comune_nascita;
    private String codice_fiscale;
    private String numero_cellulare;
    private String email;
    private String password;
 
	/*    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "assegnazioni",
            joinColumns = @JoinColumn(name = "id_sviluppatore"),
            inverseJoinColumns = @JoinColumn(name = "id_progetto")
    )
    private Set<Progetto> progetti = new HashSet<>();
   
     * collezione Set di oggetti Progetto all'interno dell'entità Sviluppatore, 
     * che rappresenta tutti i progetti associati a 
     * uno specifico sviluppatore.
    */
    public Dipendente(){
    	
    };
    
    public Dipendente(Long codice_dipendente, String nome, String cognome, String competence, String mansione,
			double stipendio, Date data_nascita, String stato_nascita, String provincia_nascita, String comune_nascita,
			String codice_fiscale, String numero_cellulare, String email_aziendale) {
		super();
		this.codice_dipendente = codice_dipendente;
		this.nome = nome;
		this.cognome = cognome;
		this.competence = competence;
		this.mansione = mansione;
		this.stipendio = stipendio;
		this.data_nascita = data_nascita;
		this.stato_nascita = stato_nascita;
		this.provincia_nascita = provincia_nascita;
		this.comune_nascita = comune_nascita;
		this.codice_fiscale = codice_fiscale;
		this.numero_cellulare = numero_cellulare;
		this.email = email_aziendale;
	}
    
    public Dipendente(String nome, String cognome, String competence, Double stipendio) {
		this.cognome = cognome;
		this.nome = nome;
		this.competence = competence;
		this.stipendio = stipendio;
	}
    
	//get and set methods
	public Long getCodice_dipendente() {
		return codice_dipendente;
	}
	public void setCodice_dipendente(Long codice_dipendente) {
		this.codice_dipendente = codice_dipendente;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCompetence() {
		return competence;
	}
	public void setCompetence(String competence) {
		this.competence = competence;
	}
	public String getMansione() {
		return mansione;
	}
	public void setMansione(String mansione) {
		this.mansione = mansione;
	}
	public double getStipendio() {
		return stipendio;
	}
	public void setStipendio(double stipendio) {
		this.stipendio = stipendio;
	}
	public Date getData_nascita() {
		return data_nascita;
	}
	public void setData_nascita(Date data_nascita) {
		this.data_nascita = data_nascita;
	}
	public String getStato_nascita() {
		return stato_nascita;
	}
	public void setStato_nascita(String stato_nascita) {
		this.stato_nascita = stato_nascita;
	}
	public String getProvincia_nascita() {
		return provincia_nascita;
	}
	public void setProvincia_nascita(String provincia_nascita) {
		this.provincia_nascita = provincia_nascita;
	}
	public String getComune_nascita() {
		return comune_nascita;
	}
	public void setComune_nascita(String comune_nascita) {
		this.comune_nascita = comune_nascita;
	}
	public String getCodice_fiscale() {
		return codice_fiscale;
	}
	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}
	public String getNumero_cellulare() {
		return numero_cellulare;
	}
	public void setNumero_cellulare(String numero_cellulare) {
		this.numero_cellulare = numero_cellulare;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    
    
}
