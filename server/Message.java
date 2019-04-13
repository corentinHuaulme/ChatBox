package server;

import java.awt.Color;
import java.io.Serializable;

import UI.PanelChat;

/**
 * Un Message est l'entité envoyé entre le Client et le Serveur
 */
public class Message implements Serializable {

	private String contenu;
	private String nom;
	private Color c;

	/**
	 * Constructeur du Message
	 * @param contenu La chaîne de caractère qui correspond au corps du message
	 * @param nom Le nom de l'expéditeur
	 * @param c La couleur associé au Client
	 */
	public Message(String contenu, String nom, Color c) {
		this.contenu = contenu;
		this.nom = nom;
		this.c = c;
	}

	/**
	 * Retourne le corps du Message
	 * @return Le contenu du Message
	 */
	public String getContenu() {
		return contenu;
	}

	/**
	 * Retourne le nom de l'expéditeur du Message
	 * @return Le nom de l'expéditeur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Méthode toString de Message
	 * @return La chaîne de caractère contenant les informations du Message
	 */
	public String toString(){
		return this.nom +" : "+this.contenu+" couleur : "+this.c;
	}

	/**
	 * Méthode appelé quand un Message est reçu
	 * Sert à mettre à jour l'interface
	 */
	public void messageRecu(){
		PanelChat.messageReceived(this);
	}

	/**
	 * Retourne la couleur associé à l'expéditeur du Message
	 * @return La couleur de l'expéditeur du Message
	 */
	public Color getColor() {
		return this.c;
	}

	/**
	 * Initialise la couleur associé à l'expéditeur du Message
	 * @param c La couleur de l'expéditeur
	 */
	public void setColor(Color c) { this.c=c; }
	
	
}
