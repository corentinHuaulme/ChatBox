package UI;

import server.Message;
import server.MessageClearConnect;
import server.MessageListeConnecte;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.management.ListenerNotFoundException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolTip;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Un PanelChat est un JPanel correspondant à la 2ème partie de {@link FenetrePrincipale}
 * Il contient la liste des personnes connectés, la liste des messages envoyés et la zone de texte pour le message à envoyé
 */
public class PanelChat extends JPanel{

	private static JTextPane chat = new JTextPane();
	private static JTextPane listConnect = new JTextPane();

	/**
	 * Constructeur d'un PanelChat
	 */
	public PanelChat(){
		JPanel root = new JPanel();
		BorderLayout borderL = new BorderLayout(10,10);
		
		root.setLayout(borderL);
		
		JPanel connectes = new JPanel();
			connectes.setLayout(new BorderLayout());
			connectes.add(new JLabel("Connectés"),BorderLayout.NORTH);
			listConnect.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			connectes.add(listConnect, BorderLayout.CENTER);
		root.add(connectes,BorderLayout.WEST);
		
		JPanel right = new JPanel();
		
		right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
		
		JPanel discussion = new JPanel();
			chat.setMaximumSize(new Dimension(450,500));
			chat.setEditable(false);
			discussion.setLayout(new BorderLayout());
			discussion.add(new JLabel("Discussion"),BorderLayout.NORTH);
			chat.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			discussion.add(chat, BorderLayout.CENTER);
		JScrollPane scroll = new JScrollPane (chat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		discussion.add(scroll);
		
		
		right.add(discussion);
		
		JPanel message = new JPanel();
			message.setLayout(new BorderLayout());
			message.add(new JLabel("Message"),BorderLayout.NORTH);
			JTextArea mess = new JTextArea();
			mess.setMaximumSize(new Dimension(450,600));
			mess.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			message.add(mess, BorderLayout.CENTER);
		right.add(message);
		
		JPanel panButton = new JPanel();
		JButton send = new JButton("Envoyer");
		send.setSize(new Dimension(right.getWidth(),5));
		
		panButton.add(send);
		
		right.add(panButton);
		
		root.add(Box.createRigidArea(new Dimension(200,400)));
		
		root.add(right);
		
		
		root.setPreferredSize(new Dimension(550,400));

		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				PanelConnect.sendMessage(mess.getText());
				mess.setText("");
			}
		});

		this.add(root);
	}

	/**
	 * Reception d'un {@link MessageListeConnecte}, ajoute le nom de l'expéditeur dans la liste des personnes connectées
	 * @param m Le {@link MessageListeConnecte} reçu
	 */
	public static void messageReceived(MessageListeConnecte m){
		StyledDocument doc = listConnect.getStyledDocument();
		Style style = listConnect.addStyle("styleNom"+m.getColor(), null);
        StyleConstants.setForeground(style, m.getColor());
        try { 
        	doc.insertString(doc.getLength(), m.getNom()+"\n",style);
        }
        catch (BadLocationException e){}
	}

	/**
	 * Reception d'un {@link MessageClearConnect}, remets la liste des personnes connectés à zéro.
	 * @param m Le {@link MessageClearConnect} reçu
	 */
	public static void messageReceived(MessageClearConnect m){
		listConnect.setText(null);
	}

	/**
	 * Reception d'un {@link Message}, ajoute le message dans la liste des messages reçus.
	 * @param m
	 */
	public static void messageReceived(Message m){
		StyledDocument doc = chat.getStyledDocument();
		Style style = chat.addStyle("styleNom"+m.getColor(), null);
        StyleConstants.setForeground(style, m.getColor());
        
        System.out.println("Color nom : "+m.getColor().toString());
        
        Style styleText = chat.addStyle("styleText", null);
        StyleConstants.setForeground(styleText, Color.black);

        try { 
        	doc.insertString(doc.getLength(), m.getNom(),style);
        	doc.insertString(doc.getLength(), " : "+m.getContenu()+"\n", styleText);
        }
        catch (BadLocationException e){}
        
		
	}

	/**
	 * Mets la liste des messages reçus et des personnes connectés à zéro.
	 */
	public static void raz(){
		chat.setText(null);
		listConnect.setText(null);
	}
	
}
