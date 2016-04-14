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

	Cliente cliente1 = new Cliente();
	Cliente cliente2 = new Cliente();
	Cliente cliente3 = new Cliente();
	Cliente cliente4 = new Cliente();
	
	/**
	 * 
	 * 
	 * Termina aqui.................................................
	 * 
	 */
	
	
	private SimpleDateFormat sdf = dataFormat.formatoData();
	
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
			
			cliente1.setNome("Alex");
			cliente1.setIp("127.0.0.1");
			cliente1.setPorta(1818);
			
			iServer.registrarCliente(cliente1);
			
			cliente2.setNome("Maria");
			cliente2.setIp("127.0.0.2");
			cliente2.setPorta(1818);
			
			iServer.registrarCliente(cliente2);
			
			cliente3.setNome("José");
			cliente3.setIp("127.0.0.3");
			cliente3.setPorta(1818);
			
			iServer.registrarCliente(cliente3);
			
			cliente4.setNome("Andersom");
			cliente4.setIp("127.0.0.4");
			cliente4.setPorta(1818);
			
			iServer.registrarCliente(cliente4);
			
			System.out.println("paranaue");
			
			iServer.desconectar(cliente2);
			
			
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
