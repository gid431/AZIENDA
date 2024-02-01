package com.example.azienda.session;

import java.util.ArrayList;
import java.util.List;

import com.example.azienda.models.Dipendente;

//classe di utenti in sessione, ovvero attivi con la sessione aperta. Con il cookie attivo, cioè ognuno ha un cookie che identifica la sua sessione
public class UtentiAttivi {
	public List<Dipendente> utenti;

    public UtentiAttivi() {
        utenti = new ArrayList<Dipendente>();
    }

	public List<Dipendente> getUtenti() {
		return utenti;
	}

	public void setUtenti(List<Dipendente> utenti) {
		this.utenti = utenti;
	}
	
	public void addUtenti(Dipendente utente) {
		this.utenti.add(utente);
	}
    
}
