package server;

import java.awt.Color;

import UI.PanelChat;

/**
 * Un MessageListeConnecte est un Message indiquant au client la connexion d'un nouveau client.
 * Un MessageListeConnecte correspond à la connexion d'une personne.
 */
public class MessageListeConnecte extends Message {

	/**
	 * Constructeur d'un MessageListeConnecte
	 * @param contenu Le corps du message
	 * @param nom Le nom de l'expéditeur
	 * @param c La couleur liée à l'expéditeur
	 */
	public MessageListeConnecte(String contenu, String nom, Color c) {
		super(contenu, nom,c);
	}

	/**
	 * Méthode appelé quand un MessageListeConnecte est reçu
	 * Informe l'interface d'ajouter l'expéditeur à la liste des clients connectés
	 */
	public void messageRecu(){
		
		PanelChat.messageReceived(this);
	}

}
