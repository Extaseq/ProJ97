package com.nichga.proj97;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserManagement {

    private String fileName = "User.txt";

    private void clearFile() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(this.fileName, false))) {
            fileWriter.write("");
        } catch (IOException e) {
            System.err.println("An error occurred while clearing the file: " + e.getMessage());
        }
    }

    public CopyOnWriteArrayList<Users> getUsers() {
        CopyOnWriteArrayList<Users> users = new CopyOnWriteArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))){
            String lineInfo;

            while ((lineInfo = fileReader.readLine()) != null) {
                List<String> usersInfoList;
                usersInfoList = List.of(lineInfo.split(","));
                Users user = new Users(usersInfoList.get(0), usersInfoList.get(1));
                users.add(user);
            }

        }catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }

        return users;
    }

    public HashMap<String, Users> getUserLoginInfo() {
        HashMap<String, Users> userLoginInfo = new HashMap<>();
        CopyOnWriteArrayList<Users> users = getUsers();
        for (Users user : users) {
            userLoginInfo.put(user.getUsername(), user);
        }
        return userLoginInfo;
    }

    public void addUser(Users user) {
        String userInfo = user.getUsername() + "," + user.getPassword() + "\n";
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName, true))) {

            if (!userInfo.equals(System.lineSeparator())) {
                fileWriter.write(userInfo);
            } else {
                System.out.println("Nothing to do.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing the document information to the file: " + e.getMessage());
        }
    }

}
