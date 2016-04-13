package br.tezza.servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;
import br.tezza.simple.date.format.DateFormat;

public class RmiServidor extends UnicastRemoteObject implements Runnable,IServer {
	
	private static final long serialVersionUID = 1L;
	
	protected RmiServidor() throws RemoteException {
		super();
		
		new Thread(this).start();
	}
	
	private Map<String, Cliente> listaClientes = new HashMap<String, Cliente>();
	
	private Map<Cliente, List<Arquivo>> listaArquivosCliente = new HashMap<Cliente, List<Arquivo>>();
	

	// Inst�nciando um objeto do tipo 'DateFormat' para poder utilizar o m�todo dele,
	// que faz a formata��o das datas.
	DateFormat dateFormat = new DateFormat();

	// Porta na qual o cliente ir� se conectar.
	private static final int PORTA_TCPIP = 1818;
	
	// Formatador de data por meio do m�todo da classe 'DateFormat'.
	private SimpleDateFormat sdf = dateFormat.formatoData("Servidor de Pesquisa");
	
	@Override
	public void run() {
		
		mensagemConsoleServidor("Servidor em execu��o.");
		
		try {
		
			Registry registry = LocateRegistry.createRegistry(PORTA_TCPIP);
			
			registry.rebind(IServer.NOME_SERVICO, this);
			
			mensagemConsoleServidor("Aguardando conex�es.");
			
		} catch (RemoteException e) {
			System.err.println("\n\n-------------------------------------------"
					+ "------------------------------------------------------\n"
					+ "Pode ser que o Servidor j� esteja em execu��o ou algum"
					+ " outro programa esteja usando a mesma porta"
					+ "\n-------------------------------------------------------"
					+ "------------------------------------------\n");
			e.printStackTrace();
		}
		
	}
	
	// M�todo que informa que o servidor est� em execu��o.
	private void mensagemConsoleServidor(String mensagem) {
		
		System.out.println(sdf.format(new Date()) + mensagem);
		
	}


	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
			
		listaClientes.put(c.getIp(), c);
 
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
		
		listaClientes.remove(c.getIp());			

	}
	
	
	public static void main(String[] args) {
		
		try {
			new RmiServidor();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
