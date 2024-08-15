/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.server;

import foldersharing.bl.FileInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.server.SocketServer.dal;

/**
 *
 * @author Osama_Alathwari
 */
public class ServerServices {
    private int userID;

    public ServerServices() {
        userID = 0;
    }
    
    
    
    public void getRequest(int request, ObjectOutputStream oos, ObjectInputStream ois) {
            switch (request) {

                case 1 ->
                    login(oos,ois);

                case 2 ->
                    signUp(oos,ois);

                case 3 ->
                    receive(oos,ois);

                case 4 ->
                    send(oos,ois);

                case 11 ->
                    getAllFiles(oos);

                case 12 ->
                    getAllVideos(oos);

                case 13 ->
                    getAllAudios(oos);

                case 14 ->
                    getAllImages(oos);

                case 15 ->
                    getAllDocuments(oos);

                case 16 ->
                    getOthers(oos);

            }
        }
    
    private void login(ObjectOutputStream oos, ObjectInputStream ois) {
            try {
                oos.flush();
                oos.writeBoolean(true);
                oos.flush();
                String userName = ois.readUTF();
                String email = ois.readUTF();
                String password = ois.readUTF();
                Object[] obj = {userName, email, password};
                ResultSet rs = dal.read(SqlQuery.signInQuery, obj);
                while (rs.next()) {
                    userID = rs.getInt("user_id");
                    oos.writeBoolean(true);
                    oos.flush();
                    return;

                }

                oos.writeBoolean(false);
                oos.flush();

            } catch (IOException | SQLException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    private void signUp(ObjectOutputStream oos, ObjectInputStream ois) {
            try {
                oos.writeBoolean(true);
                oos.flush();
                String userName = ois.readUTF();
                String email = ois.readUTF();
                String password = ois.readUTF();

                Object[] obj = {userName, email, password};
                boolean check = dal.write(SqlQuery.signUpQuery, obj);
                if (check) {
                    ResultSet rs = dal.read(SqlQuery.signInQuery, obj);
                    while (rs.next()) {
                        userID = rs.getInt("user_id");
                    }
                }
                oos.writeBoolean(check);
                oos.flush();

            } catch (IOException | SQLException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    private void receive(ObjectOutputStream oos, ObjectInputStream ois) {
            try {
                oos.flush();
                oos.writeBoolean(true);
                oos.flush();

                FileInfo file = (FileInfo) ois.readObject();
                byte[] fileContent = (byte[]) ois.readObject();
                file.setExtention(getFileExtention(file.getFileName()));
                file.setFileType(getFileType(file.getExtention()));
                file.setFileLocation("Media\\" + file.getFileType() + "\\" + file.getFileName());
                File fileToDownload = new File(file.getFileLocation());
                FileOutputStream fos = new FileOutputStream(fileToDownload);
                fos.write(fileContent);
                fos.close();

                Object[] fileinfo = {file.getFileName(), file.getFileType(), file.getExtention(), file.getFileSize(), file.getUploadDate(), file.getFileLocation(), userID};
                boolean checkInsertion = dal.write(SqlQuery.insertIntoFileTableQuery, fileinfo);

            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    private void send(ObjectOutputStream oos, ObjectInputStream ois) {
            byte[] fcontentBytes;
            try {
                oos.writeBoolean(true);
                oos.flush();
                FileInfo fileinfo = (FileInfo) ois.readObject();
                File file = new File(fileinfo.getFileLocation());
                fcontentBytes = Files.readAllBytes(Path.of(file.getAbsolutePath()));
                oos.writeObject(fcontentBytes);
                oos.flush();

            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    private void getAllFiles(ObjectOutputStream oos) {
            try {
                oos.writeBoolean(true);
                oos.flush();
                ResultSet res = dal.read(SqlQuery.getAllFilesInfoQuery, null);
                ArrayList<FileInfo> fileInfoArrayList = new ArrayList<>();
                try {
                    while (res.next()) {
                        fileInfoArrayList.add(new FileInfo(res.getString(2), res.getString(3), res.getString(4), res.getDouble(5), res.getDate(6), res.getString(7), res.getString(8)));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                oos.writeObject(fileInfoArrayList);
                oos.flush();
            } catch (IOException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        private void getAllImages(ObjectOutputStream oos) {
            try {
                oos.writeBoolean(true);
                oos.flush();
                Object[] type = {"Images"};
                ResultSet res = dal.read(SqlQuery.getAllFilesInfoByTypeQuery, type);
                ArrayList<FileInfo> fileInfoArrayList = new ArrayList<>();
                while (res.next()) {
                    fileInfoArrayList.add(new FileInfo(res.getString(2), res.getString(3), res.getString(4), res.getDouble(5), res.getDate(6), res.getString(7), res.getString(8)));
                }
                oos.writeObject(fileInfoArrayList);
                oos.flush();
            } catch (IOException | SQLException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void getAllVideos(ObjectOutputStream oos) {
            try {
                oos.writeBoolean(true);
                oos.flush();
                Object[] type = {"Videos"};
                ResultSet res = dal.read(SqlQuery.getAllFilesInfoByTypeQuery, type);
                ArrayList<FileInfo> fileInfoArrayList = new ArrayList<>();
                while (res.next()) {
                    fileInfoArrayList.add(new FileInfo(res.getString(2), res.getString(3), res.getString(4), res.getDouble(5), res.getDate(6), res.getString(7), res.getString(8)));
                }
                oos.writeObject(fileInfoArrayList);
                oos.flush();
            } catch (IOException | SQLException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void getAllAudios(ObjectOutputStream oos) {
            try {
                oos.writeBoolean(true);
                oos.flush();
                Object[] type = {"Audios"};
                ResultSet res = dal.read(SqlQuery.getAllFilesInfoByTypeQuery, type);
                ArrayList<FileInfo> fileInfoArrayList = new ArrayList<>();
                while (res.next()) {
                    fileInfoArrayList.add(new FileInfo(res.getString(2), res.getString(3), res.getString(4), res.getDouble(5), res.getDate(6), res.getString(7), res.getString(8)));
                }
                oos.writeObject(fileInfoArrayList);
                oos.flush();
            } catch (IOException | SQLException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void getAllDocuments(ObjectOutputStream oos) {
            try {
                oos.writeBoolean(true);
                oos.flush();
                Object[] type = {"Documents"};
                ResultSet res = dal.read(SqlQuery.getAllFilesInfoByTypeQuery, type);
                ArrayList<FileInfo> fileInfoArrayList = new ArrayList<>();
                while (res.next()) {
                    fileInfoArrayList.add(new FileInfo(res.getString(2), res.getString(3), res.getString(4), res.getDouble(5), res.getDate(6), res.getString(7), res.getString(8)));
                }
                oos.writeObject(fileInfoArrayList);
                oos.flush();
            } catch (IOException | SQLException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void getOthers(ObjectOutputStream oos) {
            try {
                oos.writeBoolean(true);
                oos.flush();
                Object[] type = {"Others"};
                ResultSet res = dal.read(SqlQuery.getAllFilesInfoByTypeQuery, type);
                ArrayList<FileInfo> fileInfoArrayList = new ArrayList<>();
                while (res.next()) {
                    fileInfoArrayList.add(new FileInfo(res.getString(2), res.getString(3), res.getString(4), res.getDouble(5), res.getDate(6), res.getString(7), res.getString(8)));
                }
                oos.writeObject(fileInfoArrayList);
                oos.flush();
            } catch (IOException | SQLException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    
    
    private String getFileExtention(String fileName) {
            int i = fileName.lastIndexOf('.');
            return i > 0 ? fileName.substring(i + 1) : "No Extention Found";
        }

        private static String getFileType(String FileExtention) {
            switch (FileExtention.toLowerCase()) {
                case "png", "jpg", "jpeg", "gif", "bmp", "tiff", "tif", "svg" -> {
                    return "Images";
                }

                case "mp4", "mkv", "avi", "mov", "wmv", "flv", "mpg", "mpeg", "3gp", "webm" -> {
                    return "Videos";
                }

                case "mp3", "wav", "flac", "aac", "ogg", "wma", "aiff", "m4a" -> {
                    return "Audios";
                }

                case "docx", "pdf", "txt", "ppt", "xlsx", "doc", "rtf", "xls", "pptx", "csv" -> {
                    return "Documents";
                }

                default -> {
                    return "Others";
                }
            }
        }
}
