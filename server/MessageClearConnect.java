package server;

import java.awt.Color;

import UI.PanelChat;

/**
 * Un MessageClearConnect est un Message indiquant à l'interface de remettre la liste des connectés à zéro
 */
public class MessageClearConnect extends Message{

	/**
	 * Constructeur d'un MessageClearConnect
	 * @param contenu Le corps du message
	 * @param nom Le nom de l'expéditeur
	 * @param c La couleur associé à l'expéditeur
	 */
	public MessageClearConnect(String contenu, String nom, Color c) {
		super(contenu, nom, c);
	}

	/**
	 * Méthode appelé quand un MessageClearConnect est reçu
	 * Informe l'interface d'effacer la liste des clients connectés
	 */
	public void messageRecu(){
	
		PanelChat.messageReceived(this);
	}
	
}
