package com.cz2002.hrps.utilities;

import com.cz2002.hrps.entities.Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Load and write to file
 */
public class DatabaseManager {

  private String database;

  /**
   * Constructor
   * @param database is the file to manage
   */
  public DatabaseManager(String database) {
    this.database = "./resources/database/" + database;
  }

  /**
   * Loads the file and converted into an Array of HashMap objects
   * @return ArrayList of HashMap.
   */
  private ArrayList<HashMap<String, String>> loadFile()
  {
    try {
      File file = new File(database);
      if (!file.exists())
        file.createNewFile();

      // Open the File for reading
      Scanner scanner = new Scanner(file);

      // Create an ArrayList to store the HashMap & HashMap to be a temporary buffer
      ArrayList<HashMap<String, String>> data = new ArrayList<>();
      HashMap<String, String> hashMap = new HashMap<>();

      // Loops to file EOL
      while (scanner.hasNextLine())
      {
        String line = scanner.nextLine();

        // Detection for the next Object
        if (line.length() == 0) {
          data.add(hashMap);
          hashMap = new HashMap<>();
          continue;
        }

        String arr[] = line.trim().split("\\s+", 2);
        hashMap.put(arr[0], arr.length == 1 ? "" : arr[1]);
      }
      if (hashMap.keySet().size() != 0)
        data.add(hashMap);

      scanner.close();

      return data;
    } catch (Exception e) {
      System.out.printf("File is invalid: %s\n", database);
    }

    return null;
  }


  /**
   * Write ArrayList of HashMap objects to a File
   * @param data ArrayList of HashMap objects
   * @return Success of the function
   */
  private boolean writeFile(ArrayList<HashMap<String, String>> data)
  {
    try {
      PrintWriter out = new PrintWriter(new FileWriter(new File(database)));
      for (int i = 0; i < data.size(); i++) {
        if (i > 0)
          out.print("\n\n");

        HashMap<String, String> hashMap = data.get(i);
        String keys[] = hashMap.keySet().toArray(new String[hashMap.size()]);
        String result = "";

        for (String key : keys)
          result += String.format("%s\t\t%s\n", key.toString(), hashMap.get(key));

        out.print(result.trim());
      }

      out.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return false;
  }


  /**
   * Convert a HashMap into an Object
   * @param entity is the current entity
   * @return ArrayList with object casting
   */
  public  ArrayList<Entity> load(Entity entity)
  {
    try {
      ArrayList<HashMap<String, String>> hashMapList = loadFile();
      ArrayList<Entity> entityList = new ArrayList<>();

      for (int i = 0; i < hashMapList.size(); i++) {
        Entity newEntity = entity.newInstance(hashMapList.get(i));
        entityList.add(newEntity);
      }

      return entityList;
    } catch (Exception e) {}

    return null;
  }

  /**
   * Convert an Object into HashMap to begin writing to file
   * @param entityList Entity Object Array
   * @param <T> Entity Class
   * @return
   */
  public <T> boolean write(ArrayList<T> entityList)
  {
    ArrayList<HashMap<String, String>> hashMapList = new ArrayList<>();

    for (int i = 0; i < entityList.size(); i++) {
      Entity id = (Entity) entityList.get(i);
      hashMapList.add(id.toHashMap());
    }

    return writeFile(hashMapList);
  }

}
