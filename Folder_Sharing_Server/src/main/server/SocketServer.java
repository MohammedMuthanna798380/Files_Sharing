/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Osama_Alathwari
 */
public class SocketServer {

    private ServerSocket serverSocket;
    static DAL dal;
    private List<ClientHandler> connectedClients;
    
    public SocketServer() {
        connectedClients = new ArrayList<>();
    }

    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        socketServer.start(1234); // Start the server on port 1234
    }
    
     public synchronized void addClient(ClientHandler client) {
        connectedClients.add(client);
    }

    public synchronized void removeClient(ClientHandler client) {
        connectedClients.remove(client);
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostName() + " With IP: " + clientSocket.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket,this);
                addClient(clientHandler);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
                System.out.println("Server stopped");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
