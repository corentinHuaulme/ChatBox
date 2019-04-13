package UI;

import server.Client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Un PanelConnect est la 1ère partie de {@link FenetrePrincipale}
 * Elle contient les champs de connexions (nom, ip, port).
 */
public class PanelConnect extends JPanel{

	private static Client cl;
	private boolean isConnected;

	/**
	 * Constructeur d'un PanelConnect
	 */
	public PanelConnect(){
		
		JTextArea nomArea, ipArea, portArea;
		
		JPanel root = new JPanel();
		BoxLayout box = new BoxLayout(root, BoxLayout.PAGE_AXIS);
		
		root.setLayout(box);
		
		JPanel panTop = new JPanel();
		panTop.setLayout(new BorderLayout());
		
			JPanel panName = new JPanel();
			panName.setLayout(new BoxLayout(panName, BoxLayout.LINE_AXIS));
			
			JLabel nomLab = new JLabel("Nom ");
			panName.add(nomLab);
			
			panTop.add(Box.createRigidArea(new Dimension(30,panTop.getHeight())));
			
			nomArea = new JTextArea(0,20);
			
			panName.add(nomArea);
		
		panTop.add(panName, BorderLayout.WEST);
		
		JButton connect = new JButton("Connexion");
		connect.setEnabled(false);
		
		panTop.add(connect, BorderLayout.EAST);
		
		
		JPanel panBot = new JPanel();
		panBot.setLayout(new BorderLayout());
		
			JPanel panIP = new JPanel();
			panIP.setLayout(new BoxLayout(panIP, BoxLayout.LINE_AXIS));
			panIP.add(new JLabel("IP "));
			ipArea = new JTextArea(0,20);
			panIP.add(ipArea);
			
			JPanel panPort = new JPanel();
			panPort.setLayout(new BoxLayout(panPort, BoxLayout.LINE_AXIS));
			panPort.add(new JLabel("Port "));
			portArea = new JTextArea(0,20);
			panPort.add(portArea);
			
			panBot.add(panIP,BorderLayout.WEST);
			panBot.add(panPort,BorderLayout.EAST);
		
		
		root.add(panTop);
		root.add(Box.createRigidArea(new Dimension(root.getWidth(),20)));
		root.add(panBot);
		
		root.setPreferredSize(new Dimension(550,100));
		
		nomArea.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				//System.out.println(nomArea.getText());
				if(!nomArea.getText().equals("") && !ipArea.getText().equals("") && !portArea.getText().equals("")){
					connect.setEnabled(true);
				}else{
					connect.setEnabled(false);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
		
		ipArea.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				//System.out.println(nomArea.getText());
				if(!nomArea.getText().equals("") && !ipArea.getText().equals("") && !portArea.getText().equals("")){
					connect.setEnabled(true);
				}else{
					connect.setEnabled(false);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});

	portArea.addKeyListener(new KeyListener() {
	
		@Override
		public void keyTyped(KeyEvent e) {}
		
		@Override
		public void keyReleased(KeyEvent e) {
			//System.out.println(nomArea.getText());
			if(!nomArea.getText().equals("") && !ipArea.getText().equals("") && !portArea.getText().equals("")){
				connect.setEnabled(true);
			}else{
				connect.setEnabled(false);
			}
		}
		
		@Override
		public void keyPressed(KeyEvent e) {}
	});

	connect.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Thread t = null;
				if(connect.getText().equals("Connexion")){
					if(cl == null) {
						System.out.println("new client");
						cl = new Client(nomArea.getText(), ipArea.getText(), Integer.parseInt(portArea.getText()));
					}
					t = new Thread(cl);
					FenetrePrincipale.setChatVisible(true);
					connect.setText("Deconnexion");
					t.start();
				}else{
					connect.setText("Connexion");
					cl.close();
					cl = null;
					PanelChat.raz();
					FenetrePrincipale.setChatVisible(false);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	});
		
		this.add(root);
	}

	/**
	 * Méthode appelé après l'appui sur le bouton d'envoi de message du PanelChat.
	 * Méthode nécessaire car le PanelChat ne connait pas le client connecté
	 * @param message Le {@link server.Message} à envoyé.
	 */
	public static void sendMessage(String message){
		if(cl != null){
			try {
				cl.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
