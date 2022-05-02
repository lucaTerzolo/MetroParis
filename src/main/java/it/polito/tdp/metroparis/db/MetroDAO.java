package it.polito.tdp.metroparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metroparis.model.Connessione;
import it.polito.tdp.metroparis.model.CoppiaId;
import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Linea;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"),
						new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	public List<Linea> getAllLinee() {
		final String sql = "SELECT id_linea, nome, velocita, intervallo FROM linea ORDER BY nome ASC";

		List<Linea> linee = new ArrayList<Linea>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Linea f = new Linea(rs.getInt("id_linea"), rs.getString("nome"), rs.getDouble("velocita"),
						rs.getDouble("intervallo"));
				linee.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return linee;
	}
	
	public boolean isFermateConnesse(Fermata partenza, Fermata arrivo) {
		String sql="SELECT COUNT(*) as n_connessioni "+
				"From connessione "+
				"Where id_stazA=? and id_stazP=? ";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, partenza.getIdFermata());
			st.setInt(2, arrivo.getIdFermata());
			ResultSet rs = st.executeQuery();

			rs.first();
			int count=rs.getInt("n_connessioni");
			st.close();
			conn.close();
			return count>0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore databese", e);
		}
		
	}
	
	public List<Integer> getIdFermateConnesse(Fermata partenza){
		String sql="SELECT id_stazA "
				+ "From connessione "
				+ "Where id_stazP=? "
				+ "Group by id_stazA ";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, partenza.getIdFermata());
			ResultSet rs = st.executeQuery();

			List<Integer> result=new ArrayList<>();
			
			while(rs.next()) {
				result.add(rs.getInt("id_stazA"));
			}
			st.close();
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore databese", e);
		}
	}
	
	public List<Fermata> getFermateConnesse(Fermata partenza){

		final String sql = "SELECT id_fermata, nome, coordx, coordy "
				+ "FROM fermata "
				+ "WHERE id_fermata IN( "
				+ "SELECT id_stazA "
				+ "FROM connessione "
				+ " WHERE id_stazP=? "
				+ "GROUP BY stazP "
				+ "ORDER BY nome ASC ";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, partenza.getIdFermata());
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"),
						new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore", e);
		}

		return fermate;
	}
	
	public List<CoppiaId> getAllFermateConnesse(){

		String sql="Select Distinct id_stazP, id_stazA "
				+  "From connessione";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<CoppiaId> result=new ArrayList<>();
			while (rs.next()) {
				result.add(new CoppiaId(rs.getInt("id_stazP"),rs.getInt("id_stazA")));
			}

			st.close();
			conn.close();
			return result;	

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore", e);
		}

		
		}
	
	
	

}
