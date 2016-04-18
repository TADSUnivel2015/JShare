package br.tezza.tela.cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class InterfaceGraficaCliente extends JFrame implements IServer{

	private JPanel contentPane;
	private JTextField txtNomeUsuario;
	private JTextField txtBuscaArquivo;
	private JTable tabelaResultadoBusca;
	private JTextField textField;
	private JTextField textField_1;

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
		panel.setBounds(5, 5, 526, 133);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 9, 39, 14);
		panel.add(lblNome);
		
		txtNomeUsuario = new JTextField();
		txtNomeUsuario.setBounds(59, 6, 136, 20);
		panel.add(txtNomeUsuario);
		txtNomeUsuario.setColumns(10);
		
		JButton btnDisponibilizarMeusArquivos = new JButton("Disponibilizar meus arquivos");
		btnDisponibilizarMeusArquivos.setBounds(205, 64, 206, 23);
		panel.add(btnDisponibilizarMeusArquivos);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.setBounds(421, 64, 95, 23);
		panel.add(btnConectar);
		
		txtBuscaArquivo = new JTextField();
		txtBuscaArquivo.setToolTipText("");
		txtBuscaArquivo.setColumns(10);
		txtBuscaArquivo.setBounds(10, 98, 309, 20);
		panel.add(txtBuscaArquivo);
		
		JButton btnNewButton = new JButton("Buscar Arquivo");
		btnNewButton.setBounds(322, 97, 194, 23);
		panel.add(btnNewButton);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(10, 40, 39, 14);
		panel.add(lblIp);
		
		textField = new JTextField();
		textField.setText("127.0.0.1");
		textField.setBounds(59, 37, 136, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setBounds(205, 39, 46, 14);
		panel.add(lblPorta);
		
		textField_1 = new JTextField();
		textField_1.setText("1818");
		textField_1.setBounds(261, 37, 86, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 149, 526, 250);
		contentPane.add(scrollPane);
		
		tabelaResultadoBusca = new JTable();
		scrollPane.setViewportView(tabelaResultadoBusca);
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
}
