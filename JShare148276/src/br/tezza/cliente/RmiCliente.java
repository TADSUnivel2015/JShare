package br.tezza.cliente;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;
import br.tezza.simple.date.format.DateFormat;

public class RmiCliente {
	
	DateFormat dataFormat = new DateFormat();
	
	/**
	 * 
	 * Trecho de testes.............................................
	 * 
	 * 
	 */

	Cliente cliente = new Cliente();	
	
	/**
	 * 
	 * 
	 * Termina aqui.................................................
	 * 
	 */
	
	
	private SimpleDateFormat sdf = dataFormat.formatoData("Cliente");
	
	public RmiCliente() {
		
		mostraConsole("Iniciando cliente...");
		
		try {
			IServer iServer = (IServer) Naming.lookup("rmi://localhost:1818/" + IServer.NOME_SERVICO);
			
			/**
			 * 
			 * Trecho de testes.............................................
			 * 
			 * 
			 */
			
			cliente.setNome("Alex");
			cliente.setIp("127.0.0.1");
			cliente.setPorta(1818);
			
			iServer.registrarCliente(cliente);
			
			
			/**
			 * 
			 * 
			 * Termina aqui.................................................
			 * 
			 */
			
			
		} catch (Exception e) {
			System.err.println("\n\n-------------------------------------------------------\n"
					+ "ERRO: VERIFIQUE SE O SERVIDOR ESTÁ RODANDO, SE O IP E PORTA ESTÃO"
					+ " CORRETOS, SE NÃO HÀ BLOQUEIO DE FIREWALL OU ANTIVIRUS.\n"
					+ "-------------------------------------------------------------------\n\n");
			e.printStackTrace();
		}
		
	}
	
	private void mostraConsole(String mensagem){
		System.out.println(sdf.format(new Date())+ mensagem);
	}
	
	public static void main(String[] args) {
		new RmiCliente();
	}
}
