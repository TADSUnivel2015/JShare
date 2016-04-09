package br.tezza.servidor;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;

public class Servidor extends Thread implements Runnable,IServer {

	// Porta na qual o cliente irá se conectar.
	private static final int PORTA_TCPIP = 1818;
	
	// Formatador de data.
	private SimpleDateFormat sdf = new SimpleDateFormat("'[Servidor] 'dd/mm/yy H:mm:ss:SSS' -> '");
	
	@Override
	public void run() {
		
		mensagemConsoleServidor("Servidor em execução...");
		
		super.run();
	}
	
	
	// Método que informa que o servidor está em execução.
	private void mensagemConsoleServidor(String mensagem) {
		
		System.out.println(sdf.format(new Date()) + mensagem);
		
	}


	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void publicarListaArquivos(Cliente c, List<Arquivo> lista) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<Cliente, List<Arquivo>> procurarArquivo(String nome) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] baixarArquivo(Arquivo arq) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void desconectar(Cliente c) throws RemoteException {
		// TODO Auto-generated method stub

	}

}
