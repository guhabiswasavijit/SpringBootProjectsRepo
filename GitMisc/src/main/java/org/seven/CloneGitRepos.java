package org.seven;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CloneGitRepos {

    public static void main(String[] args) throws IOException {
        String filename = "U:\\GitProjects\\output.json";
        String repoFile = "U:\\GitProjects\\GitRepos.txt";
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filename));
        JsonArray data = gson.fromJson(reader, JsonArray.class);
        for (int i = 0; i < data.size(); i += 1) {
            JsonObject jsonObject = data.get(i).getAsJsonObject();
            String repoUri = String.valueOf(jsonObject.get("html_url"));
            repoUri = repoUri.replace("\""," ");
            System.out.println(repoUri);
            FileWriter fileWriter = new FileWriter(repoFile,true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(repoUri);
            printWriter.close();
          }
    }
}
