package foldersharing.bl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private Socket socket;
    private final String host = "127.0.0.1";
    private final int port = 1234;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Client() {
        connectionToServer();
    }

    private void connectionToServer() {
        try {
            socket = new Socket(host, port);
            System.out.println("Connect To Server");
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean sendRequstNumber(int id) {
        boolean msgFromServer = false;
        try {
            oos.writeInt(id);//oos.readInt();
            System.out.println("successfully write int");
            oos.flush();

            msgFromServer = ois.readBoolean();
            System.out.println("successfully read Boolean");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return msgFromServer;
    }

    public boolean login(int id, String username, String email, String password) {
        boolean log = false;
        if (sendRequstNumber(id)) {
            try {
                System.out.println("login1");
                oos.writeUTF(username);
                oos.flush();
                System.out.println("login2");
                oos.writeUTF(email);
                oos.flush();
                System.out.println("login3");
                oos.writeUTF(password);
                oos.flush();
                System.out.println("login4");
                log = ois.readBoolean();
                if(log){
                    File file = new File("check.txt");
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(1);
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return log;
    }
    public boolean upload(String filePath, byte[] fcontentBytes) {
        File file = new File(filePath);
        String ex = Setting.getExtension(filePath);
        String filetype = Setting.getFileType(ex);
        FileInfo fi = new FileInfo(file.getName(),filetype,ex,file.length(),new Date(),filePath,"mm");
        boolean b = false;
        if (sendRequstNumber(3)) {
            try {
                oos.writeObject(fi);
                oos.flush();
                oos.writeObject(fcontentBytes);
                oos.flush();
                System.out.println("successfully write object");
                b = true;
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
        }
        return b;
    }

    public void sendObject() {
        //
    }

    public boolean download(FileInfo filei) {
        boolean down = false;
        if (sendRequstNumber(4)) {
            try {
                System.out.println("d1");
                oos.writeObject(filei);
                oos.flush();
                System.out.println("d2");
                byte[] fileContent = (byte[]) ois.readObject();
                System.out.println(fileContent.length);

                File fileToDownload = new File("Media\\" + filei.getFileType()+ "\\" + filei.getFileName());
                FileOutputStream fos = new FileOutputStream(fileToDownload);
                fos.write(fileContent);
                fos.close();
                down = true;

            } catch (IOException ex) {
                System.out.println("2");
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return down;
    }

    public ArrayList<FileInfo> getFiles(int id) {
        ArrayList<FileInfo> files = null;
        if (sendRequstNumber(id)) {
            try {
                files = (ArrayList<FileInfo>) ois.readObject();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return files;
    }

    public static void main(String[] args) {
        Client c = new Client();
//       String s = c.upload("C:\\Users\\dell\\Documents\\My Document\\assignment 3.docx");
    }
}
