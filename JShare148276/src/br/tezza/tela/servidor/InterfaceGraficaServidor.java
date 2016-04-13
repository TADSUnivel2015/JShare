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

import br.tezza.servidor.RmiServidor;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;

public class InterfaceGraficaServidor extends JFrame {

	private JPanel contentPane;
	private JTextField txtPorta;

	private int flag = 0;
	private JButton btnPararServidor;
	private JButton btnIniciarServidor;
	private JComboBox cbxIp;
	private JTextArea txtArea;
	
	private RmiServidor servidor;

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
		panel_1.add(txtPorta);
		txtPorta.setColumns(10);

		btnIniciarServidor = new JButton("Iniciar Servidor");
		btnIniciarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (txtPorta.getText().equals("")) {
					
					JOptionPane.showMessageDialog(null, "Por gentileza, informe a porta!");
					
				} else {
					
					validarCampos();
					
				}
				
				
			}

		});
		panel_1.add(btnIniciarServidor);

		btnPararServidor = new JButton("Parar Servidor");
		btnPararServidor.setEnabled(false);
		btnPararServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				bloquearBotoes(false);
				
				bloquearCampos(true);
				
				txtArea.setText("Desligando servidor...");
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

	}

	// Método que faz a busca por IPs disponíveis.
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
	
	// Método utilizado para bloquear o comboBox e o textField.
	private void bloquearCampos(Boolean status) {
		
		cbxIp.setEnabled(status);
		txtPorta.setEnabled(status);
		
	}

	// Método utilizado para bloquear os botões.
	private void bloquearBotoes(Boolean status) {
		
		btnIniciarServidor.setEnabled(!status);
		btnPararServidor.setEnabled(status);
		
	}
	
	// Método que faz a validação do campo Porta.
	private void validarCampos() {
		
		int numPorta = Integer.parseInt(txtPorta.getText());
		
		if (txtPorta.getText().matches("[0-9]{4,5}") && numPorta > 1024) {
			
			txtArea.setText("Os Dados informados estão corretos.");
			
			bloquearBotoes(true);	
			
			bloquearCampos(false);
		} else { 
			
			JOptionPane.showMessageDialog(null, "Por gentileza, a porta deve conter entre 4 e 5 digitos e ser maior que 1024!");
			txtArea.setText("Aguardando...");

		}
		
	}
}
