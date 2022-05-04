package it.polito.tdp.metroparis.model;

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
		if(this.fermate==null) {
			MetroDAO dao=new MetroDAO();
			this.fermate=dao.getAllFermate();
			
			this.fermateIdMap= new HashMap<Integer,Fermata> ();
			for(Fermata f:this.fermate)
				fermateIdMap.put(f.getIdFermata(), f);
		}
		return this.fermate;
	}
	
	public List<Fermata> calcoloPercorso(Fermata partenza, Fermata arrivo){
		creaGrafo();
		
	}
	public void creaGrafo() {
		grafo=new SimpleDirectedGraph<Fermata,DefaultEdge>(DefaultEdge.class);
		MetroDAO dao=new MetroDAO();
		
		List<Fermata> fermate=dao.getAllFermate();
		
		
		Graphs.addAllVertices(grafo, fermate);
		/*
		 
		// METODO 1: Itero su ogni coppia di vertici
		for(Fermata partenza: fermate) {
			for(Fermata arrivo: fermate) {
				if(dao.isFermateConnesse(partenza, arrivo)) {
					grafo.addEdge(partenza, arrivo);
				}
			}
		}
		
		//Per stampare tutto il grafo e il numero di vertici (Fermate) e archi (Collegamenti)
		System.out.println(grafo);
		System.out.println("Vertici= "+this.grafo.vertexSet().size());
		System.out.println("Archi= "+this.grafo.edgeSet().size());
		
		*/
		
		/*
		
		// METODO 2: dato ciascun vertice, trova i vertici ad esso adiacenti
		for(Fermata partenza:fermate) {
			List<Integer> idConnesse=dao.getIdFermateConnesse(partenza);
			for(Integer id:idConnesse) {
				Fermata arrivo=null;
				for(Fermata f:fermate) { //Avrei potuto inserire anche grafo.vertexSet()
					if(f.getIdFermata()==id) {
						arrivo=f;
						break;
					}
				}
				grafo.addEdge(partenza, arrivo);
				
			}
		}
		System.out.println(grafo);
		System.out.println("Vertici= "+this.grafo.vertexSet().size());
		System.out.println("Archi= "+this.grafo.edgeSet().size());
		
		*/
		
		/*

		// METODO 2B: il dao restituisce un elenco di oggetti fermata
		for(Fermata partenza: fermate) {
			List<Fermata> arrivi=dao.getFermateConnesse(partenza);
			for(Fermata arrivo:arrivi) {
				grafo.addEdge(partenza, arrivo);
			}
		}
		System.out.println(grafo);
		System.out.println("Vertici= "+this.grafo.vertexSet().size());
		System.out.println("Archi= "+this.grafo.edgeSet().size());
		
		*/
		
		/*
		// METODO 2C: il dao restituisce un elenco di ID numerici, che converto in oggetti 
		//    		  tramite una Map<Integer,Fermata> - "Identity Map"
		
		for(Fermata partenza: fermate) {
			List<Integer> idConnesse=dao.getIdFermateConnesse(partenza);
			for(int id:idConnesse) {
				Fermata arrivo = fermateIdMap.get(id);
				grafo.addEdge(partenza, arrivo);
			}
		}
		System.out.println(grafo);
		System.out.println("Vertici= "+this.grafo.vertexSet().size());
		System.out.println("Archi= "+this.grafo.edgeSet().size());
		*/
		
		// METODO 3: faccio una sola query che mi restituisca le coppie di fermate da 
		// 			 collegare
		// Preferisco 3C: usare Identity map
		
		List<CoppiaId> fermateDaCollegare = dao.getAllFermateConnesse();
		for(CoppiaId coppia:fermateDaCollegare) {
			grafo.addEdge(fermateIdMap.get(coppia.getIdPartenza()),
						  fermateIdMap.get(coppia.getIdArrivo())
						  );
		}
		System.out.println(grafo);
		System.out.println("Vertici= "+this.grafo.vertexSet().size());
		System.out.println("Archi= "+this.grafo.edgeSet().size());
		
		visitaGrafo(fermate.get(0));
		
	}
	
	public void visitaGrafo(Fermata partenza) {
		GraphIterator<Fermata,DefaultEdge> visita=
				new BreadthFirstIterator<>(grafo,partenza);
		int cnt=0;
		while(visita.hasNext()) {
			Fermata f=visita.next();
			System.out.println(f);
			cnt++;
		}
		System.out.println("\nVertici connessi sono: "+cnt);
	}
	
}

