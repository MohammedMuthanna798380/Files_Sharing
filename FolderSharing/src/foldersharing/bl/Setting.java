/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foldersharing.bl;

import foldersharing.pl.Properties;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class Setting {

    private static ArrayList<FileInfo> filesSharing = new ArrayList<>();

    public static void setFilesSharing(Client client, int requestNumber) {
        Setting.filesSharing = client.getFiles(requestNumber);
    }

    public static ArrayList<FileInfo> getFilesSharing() {
        return filesSharing;
    }

    public static FileInfo searchFile(String fileName) {
        if (!filesSharing.isEmpty()) {
            for (FileInfo f : filesSharing) {
                if (f.getFileName().equals(fileName)) {
                    return f;
                }
            }
        }
        return null;
    }

    public static void openFileInBrowser(File file) {
        try {
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String checkLoginFile() {
        File file = new File("check.txt");
        String username = "";
        if (file.length() != 0) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                username = br.readLine();
                br.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return username;
    }

    public static String getExtension(String path) {
        String extension = "";
        int lastIndexOfDot = path.lastIndexOf(".");
        if (lastIndexOfDot != -1) {
            extension = path.substring(lastIndexOfDot + 1);
        } else {
            System.out.println("The file has no extension");
        }
        return extension;
    }

    public static String getFileExtention(String fileName) {
        int i = fileName.lastIndexOf('.');
        return i > 0 ? fileName.substring(i + 1) : "No Extention Found";
    }

    public static String getFileType(String FileExtention) {
        switch (FileExtention) {
            case "png", "jpg", "PNG", "JPG" -> {
                return "Images";
            }

            case "mp4", "MP4", "mkv", "MKV" -> {
                return "Videos";
            }

            case "mp3", "MP3" -> {
                return "Audios";
            }

            case "docs", "pdf", "txt" -> {
                return "Documents";
            }

            default -> {
                return "Others";
            }
        }
    }

    public static void getDetailsFile(javax.swing.JFrame parent, File file) {
        if (file.exists()) {
            String extension = getExtension(file.getName());
            String lastModified = new Date(file.lastModified()).toString();
            String[] details = {
                file.getName(),
                extension,
                file.getAbsolutePath(),
                (file.length() / 1024) + " Kb",
                lastModified,
                "............"
            };
            Properties p = new Properties(parent, true, details);
            p.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(parent, "The is not exists");
        }
    }

    public static void main(String[] args) {
        openFileInBrowser(new File("C:\\Users\\dell\\Documents\\My Document\\SORT.wmv"));
    }
}
