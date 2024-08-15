/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.server;

/**
 *
 * @author Osama_Alathwari
 */
public class SqlQuery {

    public static String signInQuery = "Select * from user where username = ? and email = ? and password = ?";
    public static String signUpQuery = "INSERT INTO user (username, email, password) VALUES(?, ?, ?)";
    public static String insertIntoFileTableQuery = "INSERT INTO file (name, type, extention, size, upload_date, path, user_id) VALUES(?,?,?,?,?,?,?)";
    public static String getAllFilesInfoQuery = "SELECT file.id, file.name, file.type, file.path, file.size, file.upload_date, user.username, file.extention FROM file JOIN user ON file.user_id = user.user_id";
    public static String getAllFilesInfoByTypeQuery = "SELECT file.id, file.name, file.type, file.path, file.size, file.upload_date, user.username, file.extention FROM file JOIN user ON file.user_id = user.user_id and file.type = ?";

}
