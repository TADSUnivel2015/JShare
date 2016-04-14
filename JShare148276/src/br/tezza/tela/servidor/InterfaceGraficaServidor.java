package br.tezza.tela.servidor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;
import br.tezza.servidor.RmiServidor;
import br.tezza.simple.date.format.DateFormat;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;

public class InterfaceGraficaServidor extends JFrame implements IServer{

	private JPanel contentPane;
	private JTextField txtPorta;

	private JButton btnPararServidor;
	private JButton btnIniciarServidor;
	private JComboBox cbxIp;
	private JTextArea txtArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceGraficaServidor frame = new InterfaceGraficaServidor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfaceGraficaServidor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 571, 443);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 540, 28);
		contentPane.add(panel);

		JLabel lblServidorDeBusca = new JLabel("Servidor de busca de arquivos");
		lblServidorDeBusca.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.add(lblServidorDeBusca);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 44, 540, 33);
		contentPane.add(panel_1);

		JLabel lblIp = new JLabel("IP:");
		lblIp.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_1.add(lblIp);

		cbxIp = new JComboBox();
		panel_1.add(cbxIp);

		JLabel lblPorta = new JLabel("Porta: ");
		panel_1.add(lblPorta);

		txtPorta = new JTextField();
		txtPorta.setText("1818");
		panel_1.add(txtPorta);
		txtPorta.setColumns(10);

		btnIniciarServidor = new JButton("Iniciar Servidor");
		btnIniciarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		

			}

		});
		panel_1.add(btnIniciarServidor);

		btnPararServidor = new JButton("Parar Servidor");
		btnPararServidor.setEnabled(false);
		btnPararServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		panel_1.add(btnPararServidor);

		List<String> lista = buscaIp();
		cbxIp.setModel(new DefaultComboBoxModel<String>(new Vector<String>(lista)));
		cbxIp.setSelectedIndex(0);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 81, 540, 312);
		contentPane.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		txtArea = new JTextArea();
		txtArea.setLineWrap(true);
		txtArea.setForeground(Color.WHITE);
		txtArea.setFont(new Font("Consolas", Font.PLAIN, 15));
		txtArea.setBackground(Color.BLACK);
		scrollPane.setViewportView(txtArea);


		btnIniciarServidor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciando();
			}
		});

		btnPararServidor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				encerrarServidor();
			}
		});

	}

	/**
	 * ===================================================================================================
	 * 
	 * 										C�digo complementar	
	 * 
	 * ===================================================================================================
	 */

	private Map<String, Cliente> listaClientes = new HashMap<String, Cliente>();	
	private Map<Cliente, List<Arquivo>> listaArquivosCliente = new HashMap<Cliente, List<Arquivo>>();

	SimpleDateFormat dateFormat = new DateFormat().formatoData();

	private IServer iServer;

	private Registry registry;


	/**
	 * ===================================================================================================
	 * 
	 * 										M�todos da interface	
	 * 
	 * ===================================================================================================
	 */


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


	/**
	 * ===================================================================================================
	 * 
	 * 						 M�todos respons�veis por iniciar e encerrar o servidor
	 * 
	 * ===================================================================================================
	 */

	protected void iniciando() {
		
		String strPorta = txtPorta.getText().trim();
		int intPorta = Integer.parseInt(strPorta);

		validarCampoPorta(strPorta, intPorta);	
	}

	protected void iniciarServidor(int intPorta) {

		try {

			iServer  = (IServer) UnicastRemoteObject.exportObject(this, 0);
			registry = LocateRegistry.createRegistry(intPorta);
			registry.rebind(NOME_SERVICO, iServer);

			escreverTela("Servidor em execu��o.");

		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, "Um erro foi detectado, verifique se a porta informada"
					+ " j� est� sendo utilizada por outro processo.");
			e.printStackTrace();
		}

	}

	protected void encerrarServidor(){

		try {
			UnicastRemoteObject.unexportObject(this, true);
			UnicastRemoteObject.unexportObject(registry, true);

			escreverTela("O servidor est� sendo encerrado");

			bloquearBotoes(false);		
			bloquearCampos(true);

		} catch (NoSuchObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 * ===================================================================================================
	 * 
	 * 								M�todos que n�o fazem parte da interface
	 * 
	 * ===================================================================================================
	 */

	// M�todo que faz a busca por IPs dispon�veis.
	private List<String> buscaIp() {

		List<String> addrList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();

			while (ifaces.hasMoreElements()) {
				NetworkInterface ifc = ifaces.nextElement();
				if (ifc.isUp()) {
					Enumeration<InetAddress> addresses = ifc.getInetAddresses();
					while (addresses.hasMoreElements()) {

						InetAddress addr = addresses.nextElement();

						String ip = addr.getHostAddress();

						if (ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
							addrList.add(ip);
						}

					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		return addrList;
	}

	// M�todo utilizado para bloquear o comboBox e o textField.
	private void bloquearCampos(Boolean status) {

		cbxIp.setEnabled(status);
		txtPorta.setEnabled(status);

	}

	// M�todo utilizado para bloquear os bot�es.
	private void bloquearBotoes(Boolean status) {

		btnIniciarServidor.setEnabled(!status);
		btnPararServidor.setEnabled(status);

	}

	// M�todo que faz a valida��o do campo Porta.
	private void validarCampoPorta(String strPorta, int intPorta) {			

		if (!strPorta.matches("[0-9]+") || strPorta.length() > 5){
			JOptionPane.showMessageDialog(this, "O n�mero da porta deve ser um valor de no m�ximo 5 digitos!");
			return;
		}

		if (intPorta < 1024 || intPorta > 65535){
			JOptionPane.showMessageDialog(this, "O n�mero da porta deve estar entre 1024 e 65535");
			return;
		}

		bloquearBotoes(true);	

		bloquearCampos(false);
		
		iniciarServidor(intPorta);


	}

	private void escreverTela(String texto) {

		txtArea.append(dateFormat.format(new Date()));
		txtArea.append(" -> ");
		txtArea.append(texto);
		txtArea.append("\n");

	}
}