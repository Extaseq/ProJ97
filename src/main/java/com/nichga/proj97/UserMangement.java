package com.nichga.proj97;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserMangement {
    private CopyOnWriteArrayList<Users> users = new CopyOnWriteArrayList<>();
    private String fileName = "User.txt";

    private void clearFile() {
        try (FileWriter fileWriter = new FileWriter(this.fileName, false)) {
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
}
