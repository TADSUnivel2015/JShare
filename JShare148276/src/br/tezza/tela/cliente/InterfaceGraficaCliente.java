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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;
import br.dagostini.jshare.listarArquivo.ListarArquivos;
import br.tezza.buscaIP.ListaIP;
import br.tezza.simple.date.format.DateFormat;
import br.tezza.tableModel.ModeloTabela;
import br.tezza.tela.servidor.InterfaceGraficaServidor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class InterfaceGraficaCliente extends JFrame implements IServer{

	private JPanel contentPane;
	private JTextField txtNomeUsuario;
	private JTextField txtBuscaArquivo;
	private JTable tabelaResultadoBusca;
	private JTextField txtIpServidor;
	private JTextField txtMinhaPorta;

	private int flag = 0;

	private ListaIP listaIP = new ListaIP();
	private Cliente cliente = new Cliente();

	private ListarArquivos listarArquivos = new ListarArquivos();
	private List<Arquivo> listaArquivos;

	private Map<Cliente, List<Arquivo>> listaArquivosEncontrados; 

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
		setBounds(100, 100, 605, 449);
		contentPane = new JPanel();
		contentPane.setToolTipText("Digite aqui sua busca...");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 574, 191);
		contentPane.add(panel);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(32, 9, 39, 14);

		txtNomeUsuario = new JTextField();
		txtNomeUsuario.setBounds(81, 6, 312, 20);
		txtNomeUsuario.setColumns(10);

		btnDisponibilizarMeusArquivos = new JButton("Disponibilizar meus arquivos");
		btnDisponibilizarMeusArquivos.setBounds(0, 110, 278, 23);
		btnDisponibilizarMeusArquivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnConectar = new JButton("Conectar");
		btnConectar.setBounds(298, 110, 143, 23);

		txtBuscaArquivo = new JTextField();
		txtBuscaArquivo.setBounds(0, 159, 336, 20);
		txtBuscaArquivo.setEnabled(false);
		txtBuscaArquivo.setToolTipText("");
		txtBuscaArquivo.setColumns(10);

		btnBuscarArquivo = new JButton("Buscar Arquivo");
		btnBuscarArquivo.setBounds(356, 158, 218, 23);
		btnBuscarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBuscarArquivo.setEnabled(false);

		JLabel lblIp = new JLabel("IP Servidor:");
		lblIp.setBounds(10, 40, 89, 14);

		txtIpServidor = new JTextField();
		txtIpServidor.setBounds(81, 37, 103, 20);
		txtIpServidor.setText("127.0.0.1");
		txtIpServidor.setColumns(10);

		JLabel lblPorta = new JLabel("Porta Servidor:");
		lblPorta.setBounds(202, 40, 95, 14);

		txtMinhaPorta = new JTextField();
		txtMinhaPorta.setBounds(298, 79, 95, 20);
		txtMinhaPorta.setText("1415");
		txtMinhaPorta.setColumns(10);

		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.setBounds(461, 110, 113, 23);
		btnDesconectar.setEnabled(false);
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		JLabel lblSeuIp = new JLabel("Seu IP:");
		lblSeuIp.setBounds(32, 81, 46, 14);

		cbxMeuIP = new JComboBox();
		cbxMeuIP.setBounds(81, 79, 103, 20);

		List<String> lista = listaIP.buscaIp();
		cbxMeuIP.setModel(new DefaultComboBoxModel<String>(new Vector<String>(lista)));
		cbxMeuIP.setSelectedIndex(0);

		JLabel lblPortaDisponvel = new JLabel("Porta Dispon\u00EDvel:");
		lblPortaDisponvel.setBounds(194, 82, 103, 14);

		txtPortaServidor = new JTextField();
		txtPortaServidor.setBounds(298, 37, 95, 20);
		txtPortaServidor.setText("1818");
		txtPortaServidor.setColumns(10);
		panel.setLayout(null);
		panel.add(lblNome);
		panel.add(txtNomeUsuario);
		panel.add(btnDisponibilizarMeusArquivos);
		panel.add(btnConectar);
		panel.add(txtBuscaArquivo);
		panel.add(btnBuscarArquivo);
		panel.add(lblIp);
		panel.add(txtIpServidor);
		panel.add(lblPorta);
		panel.add(txtMinhaPorta);
		panel.add(btnDesconectar);
		panel.add(lblSeuIp);
		panel.add(cbxMeuIP);
		panel.add(lblPortaDisponvel);
		panel.add(txtPortaServidor);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 207, 574, 160);
		contentPane.add(scrollPane);

		tabelaResultadoBusca = new JTable();
		scrollPane.setViewportView(tabelaResultadoBusca);
		
		JButton btnFazerDownload = new JButton("Fazer Download");
		btnFazerDownload.setBounds(360, 371, 219, 30);
		contentPane.add(btnFazerDownload);


		btnConectar.addActionListener(e -> conectar());

		btnDesconectar.addActionListener(e -> desconectarUsuario());

		btnDisponibilizarMeusArquivos.addActionListener(e -> acoes());

		btnBuscarArquivo.addActionListener(e -> perquisarArquivos(txtBuscaArquivo.getText().toString()));

	}

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
		// TODO,. Auto-generated method stub

	}

	/**
	 * ===================================================================================================
	 * 
	 * 										C�digo complementar	
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


	/**
	 * ===================================================================================================
	 * 
	 * 										M�todos complementares	
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
			JOptionPane.showMessageDialog(this, "Endere�o IP � inv�lido, por gentileza, informe outro.");
			return;
		}

		String strPorta = txtMinhaPorta.getText().trim();
		if (!strPorta.matches("[0-9]+") || strPorta.length() > 5){
			JOptionPane.showMessageDialog(this, "O n�mero da porta deve ser um valor de no m�ximo 5 digitos!");
			return;
		}

		int intPorta = Integer.parseInt(strPorta);
		if (intPorta < 1024 || intPorta > 65535){
			JOptionPane.showMessageDialog(this, "O n�mero da porta deve estar entre 1024 e 65535");
			return;
		}

		// Verifica se o usu�rio j� disponibilizou seus arquivos.
		if (flag == 0) {

			JOptionPane.showMessageDialog(null, "Antes de se conectar, disponibilize seus arquivos!");
			return;
		} else {

			flag = 0;

			//Iniciando objetos para conex�o.
			try {

				registry = LocateRegistry.getRegistry(host, 1818);

				iServer = (IServer) registry.lookup(IServer.NOME_SERVICO);

				cliente = new Cliente();

				cliente.setNome(txtNomeUsuario.getText());
				cliente.setIp(cbxMeuIP.getSelectedItem().toString());
				cliente.setPorta(Integer.parseInt(txtMinhaPorta.getText()));

				iServer.registrarCliente(cliente);

				enviarListaArquivos();

				JOptionPane.showMessageDialog(this, "conectado!");

			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		configuraBotoes(false);
	}

	protected void desconectarUsuario() {

		try {

			if (servidor != null) {
				
				UnicastRemoteObject.unexportObject(this, true);

				servidor = null;
			}

		} catch (NoSuchObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(this, "Voc� se desconectou do Servidor...");

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

	private void enviarListaArquivos() {

		try {
			iServer.publicarListaArquivos(cliente, listaArquivos);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// M�todo utilizado no bot�o Disponibilizar meus Arquivos.
	private void acoes() {

		listaArquivos = new ArrayList<Arquivo>(listarArquivos.listarArquivo());

		JOptionPane.showMessageDialog(null, "Lista de arquivos foi publicada!");

		btnDisponibilizarMeusArquivos.setEnabled(false);

		flag = 1;	
	}

	private void perquisarArquivos(String nomeArquivo) {

		try {
			listaArquivosEncontrados = iServer.procurarArquivo(nomeArquivo);

			TableModel modelBusca = new ModeloTabela(listaArquivosEncontrados);

			tabelaResultadoBusca.setModel(modelBusca);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
