package UI;


import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Correspond à la Fenêtre principale de l'application.
 * Elle est découpée en 2 parties : un {@link PanelConnect}, et un {@link PanelChat}
 */
public class FenetrePrincipale extends JFrame{

	private PanelConnect panConnect;
	private static PanelChat panChat;

	/**
	 * Constructeur d'une FenetrePrincipale
	 *
	 */
	public FenetrePrincipale(){
		this.setTitle("Chat");
		this.setSize(600, 600);
		
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panConnect = new PanelConnect();
		panChat = new PanelChat();
		panChat.setVisible(false);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		getContentPane().add(panConnect);
		getContentPane().add(panChat);
		
		
		this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * Rend le {@link PanelChat} visible quand le client est connecté
	 * @param value vrai si le le client est connecté, faux sinon
	 */
	public static void setChatVisible(boolean value){
		panChat.setVisible(value);
	}
	
}
