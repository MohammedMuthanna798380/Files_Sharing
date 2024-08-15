package foldersharing.bl;

import java.io.Serializable;
import java.util.Date;

public class FileInfo implements Serializable {

    private int ID;
    private String fileName;
    private String fileType;
    private String fileLocation;
    private double fileSize;
    private Date uploadDate;
    private String fileUploader;
    private String extention;

    public FileInfo() {
    }

    public FileInfo(String fileName, String fileType, String fileLocation,
            double fileSize, Date uploadDate, String fileUploader, String extention) {
//        this.ID = ID;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileLocation = fileLocation;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
        this.fileUploader = fileUploader;
        this.extention = extention;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public double getFileSize() {
        return fileSize;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public String getFileUploader() {
        return fileUploader;
    }

    public int getID() {
        return ID;
    }

    public String getExtention() {
        return extention;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setFileUploader(String fileUploader) {
        this.fileUploader = fileUploader;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

}
