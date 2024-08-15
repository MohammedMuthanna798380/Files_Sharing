/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.server.SocketServer.dal;

/**
 *
 * @author Osama_Alathwari
 */
public class ClientHandler implements Runnable{
    private Socket clientSocket;
        private ObjectOutputStream oos;
        private ObjectInputStream ois;
        private SocketServer socketServer;
        private ServerServices serverServices;

        public ClientHandler(Socket clientSocket, SocketServer socketServer) {
            this.clientSocket = clientSocket;
            this.socketServer = socketServer;
            this.socketServer = new SocketServer();
            try {
                this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
                this.ois = new ObjectInputStream(clientSocket.getInputStream());
                this.serverServices = new ServerServices();
            } catch (IOException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    @Override
    public void run() {
        dal = DAL.getConnectDAL();
            while (!clientSocket.isClosed()) {

                try {
                    int request = ois.readInt();
                    serverServices.getRequest(request,oos,ois);
                } catch (IOException ex) {
                    System.out.println("Client : " + clientSocket.getInetAddress().getHostName() +" With IP: " + clientSocket.getInetAddress().getHostAddress() +  " Disconnected");
                    try {
                        clientSocket.close();
                        socketServer.removeClient(this);
                    } catch (IOException ex1) {
                        Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
    }
}
