package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Serveur basique UDP
 */
public class ServeurUDP
{

	public static void main(String[] args) throws Exception
	{
		ServeurUDP serveurUDP = new ServeurUDP();
		serveurUDP.execute();
		
	}
		
		

	private void execute() throws IOException
	{
		//
		System.out.println("Démarrage du serveur ...");
		
		// Le serveur se declare aupres de la couche transport
		// sur le port 5099
		DatagramSocket socket = new DatagramSocket(null);
		socket.bind(new InetSocketAddress(5099));

		// Attente du premier message
		byte[] bufR = new byte[2048];
		DatagramPacket dpR = new DatagramPacket(bufR, bufR.length);
		socket.receive(dpR);
		String message = new String(bufR, dpR.getOffset(), dpR.getLength());
		System.out.println("Message reçu = "+message);
		
		// Emission d'un message en retour
		byte[] bufE = new String("Je n'ai pas envie").getBytes();
		DatagramPacket dpE = new DatagramPacket(bufE, bufE.length, 
				dpR.getAddress(),dpR.getPort());
		socket.send(dpE);
		System.out.println("Message envoyé");
		
		// Fermeture de la socket
		socket.close();
		System.out.println("Arrêt du serveur .");
	}
		
}
