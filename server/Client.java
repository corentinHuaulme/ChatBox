package server;

import UI.PanelChat;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Un Client est la partie communiquante de l'interface.
 * Un Client envoie et reçoie des messages du serveur.
 */
public class Client implements Runnable{

	private String nom;
	private int port;
	private String ip;
	private Socket connexion = null;
	private ObjectOutputStream writer = null;
	private ObjectInputStream reader = null;
	private volatile boolean exit=false;

	/**
	 * Constructeur du Client
	 * @param nom Le nom du Client
	 * @param ip L'IP du serveur
	 * @param port Le port du serveur
	 * @throws IOException
	 */
	public Client(String nom, String ip, int port) throws IOException {
		this.nom = nom;
		this.port = port;
		this.ip = ip;
		connexion = new Socket(ip,port);
		exit=false;
    }

	/**
	 * Méthode appelé au lancement du Thread
	 * Demande la connexion au serveur, Reçois la liste des clients connectés, Attend l'arrivée de message.
	 */
	public void run(){
		try{
			writer = new ObjectOutputStream(connexion.getOutputStream());
			reader = new ObjectInputStream(connexion.getInputStream());
			writer.flush();
			writer.writeObject(new Message(this.nom, this.nom,Color.BLACK));
			writer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(!exit) {
			
				try {
					Message nomEmetteur = (Message) reader.readObject();
					System.out.println(nomEmetteur);
					nomEmetteur.messageRecu();

				} catch (IOException e1) {
					e1.printStackTrace();
					System.exit(-1);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
	}

	/**
	 * Fermeture du Client
	 * Envoie le message de fermeture au serveur pour une déconnexion gracieuse
	 */
	public void close(){
		exit=true;
		try {
			writer.writeObject(new Message(".exit",this.nom,Color.black));
			reader.close();
			writer.close();
			connexion.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Envoie un message au serveur
	 * @param mess Le message à envoyé au serveur
	 * @throws IOException
	 */
	public void sendMessage(String mess) throws IOException{
		writer.writeObject(new Message(mess,this.nom,Color.BLACK));
		writer.flush();
		if(mess.equals(".exit")){
			exit=true;
			reader.close();
			writer.close();
			connexion.close();
		}
	}
}
