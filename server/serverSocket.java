package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import UI.PanelChat;

/**
 * Un serverSocket et le point d'entrée de toutes les connexions au Serveur.
 * A chaque nouvelle connexion, un ServerThread est crée et le Socket lié au Client lui est associé.
 * Il hérite de Thread afin de le rendre parallèle à l'éxécution
 */
public class serverSocket extends Thread{

    private int port;
    private ArrayList<ServerThread> listSockets = new ArrayList<ServerThread>();

	/**
	 * Constructeur d'un serverSocket
	 * @param port Le port du serveur
	 */
	public serverSocket(int port){
       this.port =port;
    }

	/**
	 * Méthode appelé au lancement du Thread
	 * Attend une connexion, crée un ServerThread lors d'une connexion et l'ajoute à la liste des connexions.
	 */
	public void run(){

       ServerSocket server = null;
       try {
           server = new ServerSocket(this.port);
       } catch (IOException e) {
           e.printStackTrace();
       }


	   while(true){
		
			   //creating socket and waiting for client connection
			Socket socket = null;
			try {
			    socket = server.accept();
			} catch (IOException e) {
			    e.printStackTrace();
			}
			ServerThread st = new ServerThread(socket, this);
			listSockets.add(st);
			st.start();
			/*try {
				//sendListeConnectees();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	   }
    }

	/**
	 * Envoie un message à tous les clients connectés au chat
	 * @param mess Le Message à envoyé
	 * @throws IOException
	 */
	public void sendMessage(Message mess) throws IOException {
       for(ServerThread st : listSockets){
           ObjectOutputStream oos = st.getOos();
           if(oos != null){
        	   oos.flush();
        	   oos.writeObject(mess);
        	   oos.flush();
           }
       }
    }

    /**
	 * Envoie la liste des clients connecté à tous les clients connectés à chaque nouvelle connexion.
     */
    public void sendListeConnectees() throws IOException{
    	for(ServerThread st : listSockets){
    		ObjectOutputStream oos = st.getOos();
    		if(oos != null && st.getNom() != null){
    			oos.flush();
    			oos.writeObject(new MessageClearConnect(".serveur", st.getNom(), st.getColor()));
    			oos.flush();
    			for(ServerThread n : listSockets) {
    				System.out.println("ServeurSocket listeConnect : "+n.getColor());
	    			oos.flush();
	    			oos.writeObject(new MessageListeConnecte(".serveur",n.getNom(),n.getColor()));
	    			oos.flush();
    			}
    		}
    	}
    }

	/**
	 * Supprime un ServerThread de la liste des connexions.
	 * Appelé quand un Client se déconnecte du Chat
	 * @param st Le ServerThread à supprimer
	 */
	public void removeServerThread(ServerThread st){
    	this.listSockets.remove(st);
    }
}