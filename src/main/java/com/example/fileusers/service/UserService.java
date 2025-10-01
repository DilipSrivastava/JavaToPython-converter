package com.example.fileusers.service;

import com.example.fileusers.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${user.file.path}")
    private String filePath;

    public void addUser(User user) throws IOException {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(user.getId() + "," + user.getName() + "," + user.getEmail());
            bw.newLine();
        }
    }

    public List<User> getAllUsers() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) return new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines()
                     .map(line -> {
                         String[] parts = line.split(",");
                         return new User(Integer.parseInt(parts[0]), parts[1], parts[2]);
                     })
                     .collect(Collectors.toList());
        }
    }

    public boolean updateUser(User updatedUser) throws IOException {
        List<User> users = getAllUsers();
        boolean updated = false;

        for (User user : users) {
            if (user.getId() == updatedUser.getId()) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                updated = true;
                break;
            }
        }

        if (updated) {
            writeUsers(users);
        }
        return updated;
    }

    public boolean deleteUser(int id) throws IOException {
        List<User> users = getAllUsers();
        boolean removed = users.removeIf(u -> u.getId() == id);

        if (removed) {
            writeUsers(users);
        }
        return removed;
    }

    private void writeUsers(List<User> users) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                bw.write(user.getId() + "," + user.getName() + "," + user.getEmail());
                bw.newLine();
            }
        }
    }
}
