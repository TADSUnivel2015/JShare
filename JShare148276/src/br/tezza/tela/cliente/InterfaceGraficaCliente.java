package br.tezza.tela.cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;
import br.tezza.buscaIP.ListaIP;
import br.tezza.simple.date.format.DateFormat;
import br.tezza.tela.servidor.InterfaceGraficaServidor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;

public class InterfaceGraficaCliente extends JFrame implements IServer{

	private JPanel contentPane;
	private JTextField txtNomeUsuario;
	private JTextField txtBuscaArquivo;
	private JTable tabelaResultadoBusca;
	private JTextField txtIpServidor;
	private JTextField txtMinhaPorta;

	private ListaIP listaIP = new ListaIP();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceGraficaCliente frame = new InterfaceGraficaCliente();
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
	public InterfaceGraficaCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 557, 449);
		contentPane = new JPanel();
		contentPane.setToolTipText("Digite aqui sua busca...");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 526, 206);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(32, 9, 39, 14);
		panel.add(lblNome);

		txtNomeUsuario = new JTextField();
		txtNomeUsuario.setBounds(81, 6, 312, 20);
		panel.add(txtNomeUsuario);
		txtNomeUsuario.setColumns(10);

		btnDisponibilizarMeusArquivos = new JButton("Disponibilizar meus arquivos");
		btnDisponibilizarMeusArquivos.setBounds(10, 110, 278, 23);
		panel.add(btnDisponibilizarMeusArquivos);

		btnConectar = new JButton("Conectar");
		btnConectar.setBounds(298, 110, 95, 23);
		panel.add(btnConectar);

		txtBuscaArquivo = new JTextField();
		txtBuscaArquivo.setToolTipText("");
		txtBuscaArquivo.setColumns(10);
		txtBuscaArquivo.setBounds(10, 173, 278, 20);
		panel.add(txtBuscaArquivo);

		btnBuscarArquivo = new JButton("Buscar Arquivo");
		btnBuscarArquivo.setBounds(298, 172, 218, 23);
		panel.add(btnBuscarArquivo);

		JLabel lblIp = new JLabel("IP Servidor:");
		lblIp.setBounds(10, 40, 89, 14);
		panel.add(lblIp);

		txtIpServidor = new JTextField();
		txtIpServidor.setText("127.0.0.1");
		txtIpServidor.setBounds(81, 37, 103, 20);
		panel.add(txtIpServidor);
		txtIpServidor.setColumns(10);

		JLabel lblPorta = new JLabel("Porta Servidor:");
		lblPorta.setBounds(202, 43, 103, 14);
		panel.add(lblPorta);

		txtMinhaPorta = new JTextField();
		txtMinhaPorta.setText("1415");
		txtMinhaPorta.setBounds(298, 79, 95, 20);
		panel.add(txtMinhaPorta);
		txtMinhaPorta.setColumns(10);

		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnDesconectar.setBounds(403, 110, 113, 23);
		panel.add(btnDesconectar);

		JLabel lblSeuIp = new JLabel("Seu IP:");
		lblSeuIp.setBounds(32, 81, 46, 14);
		panel.add(lblSeuIp);

		cbxMeuIP = new JComboBox();
		cbxMeuIP.setBounds(81, 79, 103, 20);
		panel.add(cbxMeuIP);

		List<String> lista = listaIP.buscaIp();
		cbxMeuIP.setModel(new DefaultComboBoxModel<String>(new Vector<String>(lista)));
		cbxMeuIP.setSelectedIndex(0);

		JLabel lblPortaDisponvel = new JLabel("Porta Dispon\u00EDvel:");
		lblPortaDisponvel.setBounds(194, 82, 103, 14);
		panel.add(lblPortaDisponvel);

		txtPortaServidor = new JTextField();
		txtPortaServidor.setText("1818");
		txtPortaServidor.setBounds(298, 37, 95, 20);
		panel.add(txtPortaServidor);
		txtPortaServidor.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 222, 526, 177);
		contentPane.add(scrollPane);

		tabelaResultadoBusca = new JTable();
		scrollPane.setViewportView(tabelaResultadoBusca);

		btnConectar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conectar();
			}
		});

		btnDesconectar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				desconectarUsuario();
			}

		});

	}



	/**
	 * ===================================================================================================
	 * 
	 * 										Métodos da interface	
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
		// TODO,. Auto-generated method stub

	}

	/**
	 * ===================================================================================================
	 * 
	 * 										Código complementar	
	 * 
	 * ===================================================================================================
	 */


	// Formato de data, utilizando a classe DateFormat.
	private SimpleDateFormat dateFormat = new DateFormat().formatoData();

	private IServer iServer;

	private Registry registry;

	private InterfaceGraficaServidor servidor;

	private String meuNome;
	private JTextField txtPortaServidor;
	private JComboBox cbxMeuIP;
	private JButton btnConectar;
	private JButton btnDisponibilizarMeusArquivos;
	private JButton btnDesconectar;
	private JButton btnBuscarArquivo;
	private Cliente cliente;


	/**
	 * ===================================================================================================
	 * 
	 * 										Métodos complementares	
	 * 
	 * ===================================================================================================
	 */

	protected void conectar() {

		meuNome = txtNomeUsuario.getText().trim();
		if (meuNome.length() == 0) {
			JOptionPane.showMessageDialog(this, "Informe seu nome!");
			return;
		}

		String host = txtIpServidor.getText().trim();
		if (!host.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
			JOptionPane.showMessageDialog(this, "Endereço IP é inválido, por gentileza, informe outro.");
			return;
		}

		String strPorta = txtMinhaPorta.getText().trim();
		if (!strPorta.matches("[0-9]+") || strPorta.length() > 5){
			JOptionPane.showMessageDialog(this, "O número da porta deve ser um valor de no máximo 5 digitos!");
			return;
		}

		int intPorta = Integer.parseInt(strPorta);
		if (intPorta < 1024 || intPorta > 65535){
			JOptionPane.showMessageDialog(this, "O número da porta deve estar entre 1024 e 65535");
			return;
		}

		//Iniciando objetos para conexão.
		try {

			registry = LocateRegistry.getRegistry(host, 1818);

			iServer = (IServer) registry.lookup(IServer.NOME_SERVICO);

			cliente = new Cliente();

			cliente.setNome(txtNomeUsuario.getText());
			cliente.setIp(cbxMeuIP.getSelectedItem().toString());
			cliente.setPorta(Integer.parseInt(txtMinhaPorta.getText()));

			iServer.registrarCliente(cliente);

			JOptionPane.showMessageDialog(this, "conectado!");

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		configuraBotoes(false);
	}
	
	protected void desconectarUsuario() {
	
		try {
			
			UnicastRemoteObject.unexportObject(this, true);
		} catch (NoSuchObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(this, "Você se desconectou do Servidor...");
		
		configuraBotoes(true);	
		
		try {
			iServer.desconectar(cliente);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void configuraBotoes(Boolean status) {
		
		btnConectar.setEnabled(status);
		btnDisponibilizarMeusArquivos.setEnabled(status);
		
		btnBuscarArquivo.setEnabled(!status);
		btnDesconectar.setEnabled(!status);
		
		txtNomeUsuario.setEnabled(status);
		txtIpServidor.setEnabled(status);
		txtPortaServidor.setEnabled(status);
		cbxMeuIP.setEnabled(status);
		txtMinhaPorta.setEnabled(status);
		
		txtBuscaArquivo.setEnabled(!status);
	}
}
