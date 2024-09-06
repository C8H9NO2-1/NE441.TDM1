//package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Client basique UDP
 * 
 */
public class PingPong
{

	public static void main(String[] args) throws Exception
	{
        PingPong pingPong = new PingPong();
		pingPong.play();
				
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
		InetSocketAddress adrDest = new InetSocketAddress("127.0.0.1", 29000);

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
            System.out.println("Le serveur a répondu " + reponse);

            // Ensuite on doit traiter la réponse du serveur
            if (reponse.equals(new String("PING"))) {
                System.out.println("Envoi d'un paquet UDP avec PONG");
                bufE = new String("PONG").getBytes();
                dpE = new DatagramPacket(bufE, bufE.length, adrDest);
                socket.send(dpE);
            } else {
                System.out.println("Envoi d'un paquet UDP avec PING");
                bufE = new String("PING").getBytes();
                dpE = new DatagramPacket(bufE, bufE.length, adrDest);
                socket.send(dpE);
            }

            // On récupère la réponse du serveur
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
