package it.polito.tdp.metroparis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	private Graph<Fermata,DefaultEdge> grafo;
	
	private List<Fermata> fermate;
	private Map<Integer,Fermata> fermateIdMap;
	
	public List<Fermata> getFermate(){
		if(this.fermate == null) {
			MetroDAO dao = new MetroDAO();
			this.fermate = dao.getAllFermate();
			
			this.fermateIdMap = new HashMap<Integer,Fermata> ();
			for(Fermata f:this.fermate)
				fermateIdMap.put(f.getIdFermata(), f);
		}
		return this.fermate;
	}
	
	
	public void creaGrafo() {
		grafo=new SimpleDirectedGraph<Fermata,DefaultEdge>(DefaultEdge.class);
		MetroDAO dao=new MetroDAO();
		
		List<Fermata> fermate = dao.getAllFermate();
		
		
		Graphs.addAllVertices(grafo, fermate);

		List<CoppiaId> fermateDaCollegare = dao.getAllFermateConnesse();
		for(CoppiaId coppia:fermateDaCollegare) {
			grafo.addEdge(fermateIdMap.get(coppia.getIdPartenza()),
						  fermateIdMap.get(coppia.getIdArrivo())
						  );
		}
	}
	
	public List<Fermata> calcoloPercorso(Fermata partenza, Fermata arrivo){
		creaGrafo();
		Map<Fermata,Fermata> alberoInverso=visitaGrafo(partenza);
		
		Fermata corrente=arrivo;
		List<Fermata> percorso=new ArrayList<>();
		
		while(corrente!=null) { //nodo di partenza ha come valore nell'albero inverso null
			percorso.add(0, corrente); //per aggiungere in testa alla lista
			corrente=alberoInverso.get(corrente);
		}
		return percorso;
	}
	
	public Map<Fermata,Fermata> visitaGrafo(Fermata partenza) {
		GraphIterator<Fermata,DefaultEdge> visita = new BreadthFirstIterator<>(grafo,partenza);
		
		Map<Fermata,Fermata> alberoInverso = new HashMap<>();
		alberoInverso.put(partenza, null); //Radice albero
		
		visita.addTraversalListener(new RegistroAlberoVisita(alberoInverso,this.grafo));
		int cnt = 0;
		
		while(visita.hasNext()) {
			Fermata f = visita.next();
		}
		
		return alberoInverso;
	}
	
}

