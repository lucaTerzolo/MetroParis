package it.polito.tdp.metroparis.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model=new Model();
		double start=System.currentTimeMillis();
		model.creaGrafo();
		double end=System.currentTimeMillis();
		System.out.println("Time= "+(end-start)*Math.pow(10, -3)+" s");
	}

}
