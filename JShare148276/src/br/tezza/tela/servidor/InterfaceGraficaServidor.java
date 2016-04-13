package br.tezza.tela.servidor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class InterfaceGraficaServidor extends JFrame {

	private JPanel contentPane;
	private JTextField txtPorta;

	private int flag = 0;
	private JButton btnPararServidor;
	private JButton btnIniciarServidor;

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 424, 28);
		contentPane.add(panel);

		JLabel lblServidorDeBusca = new JLabel("Servidor de busca de arquivos");
		lblServidorDeBusca.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.add(lblServidorDeBusca);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 44, 424, 33);
		contentPane.add(panel_1);

		JLabel lblIp = new JLabel("IP:");
		lblIp.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_1.add(lblIp);

		JComboBox cbxIp = new JComboBox();
		panel_1.add(cbxIp);

		JLabel lblPorta = new JLabel("Porta: ");
		panel_1.add(lblPorta);

		txtPorta = new JTextField();
		panel_1.add(txtPorta);
		txtPorta.setColumns(10);

		btnIniciarServidor = new JButton("Iniciar Servidor");
		btnIniciarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				btnIniciarServidor.setVisible(false);
				btnPararServidor.setVisible(true);
			}
		});
		panel_1.add(btnIniciarServidor);

		btnPararServidor = new JButton("Parar Servidor");
		btnPararServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				btnPararServidor.setVisible(false);
				btnIniciarServidor.setVisible(true);

			}
		});
		panel_1.add(btnPararServidor);

		List<String> lista = buscaIp();
		cbxIp.setModel(new DefaultComboBoxModel<String>(new Vector<String>(lista)));
		cbxIp.setSelectedIndex(0);

	}

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

}
