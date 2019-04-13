package server;

/**
 * Classe de lancement du Serveur
 */
public class LauncherServer {

	/**
	 * Méthode main du serveur
	 * @param args Argument écrit dans la ligne de commande
	 */
	public static void main(String[] args) {
		serverSocket sock = new serverSocket(9876);
		sock.start();
	}

}
