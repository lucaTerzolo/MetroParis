package it.polito.tdp.metroparis.model;

public class CoppiaId {
	int IdPartenza;
	int IdArrivo;
	public CoppiaId(int idPartenza, int idArrivo) {
		super();
		IdPartenza = idPartenza;
		IdArrivo = idArrivo;
	}
	public int getIdPartenza() {
		return IdPartenza;
	}
	public void setIdPartenza(int idPartenza) {
		IdPartenza = idPartenza;
	}
	public int getIdArrivo() {
		return IdArrivo;
	}
	public void setIdArrivo(int idArrivo) {
		IdArrivo = idArrivo;
	}
	
}
