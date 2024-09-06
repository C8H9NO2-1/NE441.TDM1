//package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Client basique UDP
 * 
 */
public class Multiplication
{

	public static void main(String[] args) throws Exception
	{
        Multiplication multiplication = new Multiplication();
		multiplication.play();
				
	}
				
				
	/**
	 * Le client cree une socket, envoie un message au serveur
	 * et attend la reponse 
	 * 
	 */
	private void play() throws IOException
	{
		// Il faut créer la socket
        DatagramSocket socket = new DatagramSocket();
		InetSocketAddress adrDest = new InetSocketAddress("127.0.0.1", 11000);

        for (int i = 1; i <= 10; i++) {
            System.out.println("==============================");
            System.out.println("Début de la partie " + i);

            // Il faut envoyer le message JOUER
            byte[] bufE = new String("JOUER").getBytes();
            DatagramPacket dpE = new DatagramPacket(bufE, bufE.length, adrDest);
            socket.send(dpE);
            System.out.println("Envoi d'un paquet UDP avec JOUER");

            // On attend la réponse du serveur
            byte[] bufR = new byte[2048];
            DatagramPacket dpR = new DatagramPacket(bufR, bufR.length);
            socket.receive(dpR);
            String reponse = new String(bufR, dpR.getOffset(), dpR.getLength());
            int premier = Integer.valueOf(reponse.substring(0, 1)).intValue();

            // On récupère la deuxième réponse du serveur
            bufR = new byte[2048];
            dpR = new DatagramPacket(bufR, bufR.length);
            socket.receive(dpR);
            reponse = new String(bufR, dpR.getOffset(), dpR.getLength());
            int second = Integer.valueOf(reponse.substring(0, 1)).intValue();
            System.out.println("Le serveur a répondu " + premier + " et " + second);

            //? On calcule le résultat à envoyer au serveur
            int resultat = premier * second; 

            // On envoie la réponse au serveur
            String temp = String.valueOf(resultat) + ";";
            bufE = temp.getBytes();
            dpE = new DatagramPacket(bufE, bufE.length, adrDest);
            socket.send(dpE);
            System.out.println("Envoi d'un paquet UDP avec " + temp);

            // On attend la réponse du serveur
            bufR = new byte[2048];
            dpR = new DatagramPacket(bufR, bufR.length);
            socket.receive(dpR);
            reponse = new String(bufR, dpR.getOffset(), dpR.getLength());
            System.out.println("Le serveur a répondu " + reponse);

            System.out.println("Fin de la partie " + i);
        }

        // Lorsque la partie est finie, il faut fermer la socket
        socket.close();
		
	}

}
