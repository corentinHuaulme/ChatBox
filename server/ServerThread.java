package server;


import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Un ServerThread est crée quand un Client se connecte au Chat.
 * Il est également un Thread, lui permettant de s'éxécuter en parallèle du Serveur
 * Quand le Client se déconnecte, le ServerThread est détruit.
 */
public class ServerThread extends Thread{

    private Socket socket;
    private serverSocket parent;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private String nomClient;
    private Color stringColor;
    private boolean running;

    /**
     * Constructeur du ServerThread
     * @param sock Le Socket qui le lie à un client
     * @param parent Le serverSocket mère
     */
    public ServerThread(Socket sock, serverSocket parent){

        this.socket = sock;
        this.parent = parent;
        this.stringColor = new Color((int) (Math.random()*255),(int) (Math.random()*255), (int) (Math.random()*255));
        this.running = true;

    }

    /**
     * Méthode appelé au lancement du Thread.
     * Informe les clients connectés au chat de l'arrivée d'un nouveau client.
     * Attend que le client envoie un message pour le transmettre au autres clients
     */
    public void run() {
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                this.nomClient = message.getNom();
                System.out.println("NOM CLIENT : "+this.nomClient);
                //this.parent.sendMessage(message);
                this.parent.sendListeConnectees();
                while (this.running) {

                    oos.flush();
                    message = (Message) ois.readObject();
                    message.setColor(this.getColor());
                    System.out.println("message reçu : "+message);
                    if(message.getContenu().equals(".exit")){

                    	this.oos.close();
                    	this.ois.close();
                    	this.socket.close();
                    	this.parent.removeServerThread(this);
                    	this.running = false;

                    }
                    else{

                    	this.nomClient = message.getNom();
                    	this.parent.sendMessage(message);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    /**
     * Retourne l'ObjectInputStream du ServerThread
     * @return L'ObjectInputStream du ServerThread
     */
    public ObjectInputStream getOis(){
        return this.ois;
    }

    /**
     * Retourne l'ObjectOutputStream du ServerThread
     * @return L'ObjectOutputStream du ServerThread
     */
    public ObjectOutputStream getOos(){
        return this.oos;
    }

    /**
     * Retourne le nom du Client
     * @return Le nom du Client associé au ServerThread
     */
    public String getNom(){
    	return this.nomClient;
    }

    /**
     * Retourne la couleur du Client
     * @return La couleur du Client associé au ServerThread
     */
    public Color getColor() {
    	return this.stringColor;
    }


}
