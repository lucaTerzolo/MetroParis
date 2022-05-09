package it.polito.tdp.metroparis.model;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;

public class RegistroAlberoVisita implements TraversalListener<Fermata,DefaultEdge>{

	private Map<Fermata,Fermata> alberoInverso;
	private Graph<Fermata,DefaultEdge> grafo;
	
	public RegistroAlberoVisita(Map<Fermata, Fermata> alberoInverso,Graph<Fermata,DefaultEdge> grafo) {
		super();
		this.alberoInverso = alberoInverso;
		this.grafo=grafo;
		
	}

	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
		
		Fermata source=this.grafo.getEdgeSource(e.getEdge());
		Fermata target=this.grafo.getEdgeTarget(e.getEdge());
		
		if(!alberoInverso.containsKey(target)) {
			alberoInverso.put(target, source);
			//System.out.println(target+" si raggiunge da "+source);
		}else if(!alberoInverso.containsKey(source)) {
			alberoInverso.put(source, target);
			//System.out.println(source+" si raggiunge da "+target);
		}
	}

	@Override
	public void vertexTraversed(VertexTraversalEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vertexFinished(VertexTraversalEvent e) {
		// TODO Auto-generated method stub
		
	}

}
