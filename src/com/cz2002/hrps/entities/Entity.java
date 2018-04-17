package com.cz2002.hrps.entities;

import com.cz2002.hrps.utilities.DatabaseManager;
import com.cz2002.hrps.utilities.FileDatabaseManager;
import com.cz2002.hrps.models.PromptModelContainer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Super class of all entities
 */
public abstract class Entity {

  private DatabaseManager databaseManager;
  private static HashMap<Class, ArrayList<Entity>> entities = new HashMap<>();;

  /**
   * Constructor
   * @param database is the name of database file
   */
  Entity(String database) {
    this.databaseManager = new FileDatabaseManager(database);
    loadEntitiesIfNeeded();
  }

  public abstract Entity newInstance();

  public abstract Entity newInstance(HashMap<String, String> data);

  /**
   * Load database for current class and put data in entities
   */
  private void loadEntitiesIfNeeded() {
    Class C = this.getClass();

    if (entities.get(C) != null) {
      return;
    }

    entities.put(C, new ArrayList());

    ArrayList entityList;
    try {
      entityList = databaseManager.load(this);
    } catch (Exception fe) {
      entityList = new ArrayList();
    }

    entities.put(C, entityList);
  }

  /**
   * Save current entity to the database
   * @return write success status
   */
  private boolean writeToDatabase() {
    ArrayList<Entity> entity = entities.get(this.getClass());
    return databaseManager.write(entity);
  }

  /**
   * Creates a new object and save to database
   * @return true if success, false otherwise
   */
  public boolean create() {
    ArrayList<Entity> entity =  entities.get(this.getClass());
    entity.add(this);
    return writeToDatabase();
  }

  /**
   * Find the first entity match queries
   * @param queries is the queries to search
   * @return null if not found, Object Array of size results if found
   */
  public Entity findEntity(HashMap<String, String> queries) {
    return findEntities(queries)[0];
  }

  /**
   * Find a list of entities
   * @param queries is the queries to search
   * @return null if not found, Object Array of size results if found
   */
  public Entity[] findEntities(HashMap<String, String> queries) {
    // Get Objects of Entities and create a resultList
    ArrayList<Entity> resultList = entities.get(this.getClass());
    ArrayList<Entity> bufferList = new ArrayList<>();

    for (Map.Entry<String, String> query : queries.entrySet()) {
      String queryKey = query.getKey();
      String queryValue = query.getValue();

      for (Entity entity : resultList) {
        String value = entity.toHashMap().get(queryKey);

        if (value.equals(queryValue)) {
          bufferList.add(entity);
        }
      }

      resultList = bufferList;
      bufferList = new ArrayList<>();
    }

    // Nothing was found
    if (resultList.size() == 0)
      return (Entity[]) Array.newInstance(this.getClass(), 0);

    // Convert ArrayList to Array
    Entity[] entityArray  = (Entity[]) Array.newInstance(this.getClass(), resultList.size());
    entityArray = resultList.toArray(entityArray);

    return entityArray;
  }

  /**
   * Creates a new object and save to database
   * @return true if success, false otherwise
   */
  public boolean update() {
    return writeToDatabase();
  }

  /**
   * Creates a new object and save to database
   * @return true if success, false otherwise
   */
  public boolean update(HashMap<String, String> hashMap) {
    fromHashMap(hashMap);
    return update();
  }

  /**
   * Delete this Object from the Database
   * @return true if success, false otherwise
   */
  public boolean delete() {
    ArrayList<Entity> entityList = entities.get(this.getClass());
    entityList.remove(this);
    return databaseManager.write(entityList);
  }

  /**
   * Converts an Entity object to a HashMap
   * @return HashMap Strng of Guest Data
   */
  public abstract HashMap<String, String> toHashMap();

  /**
   * Convert a HashMap to an Entity
   * @param hashMap is the hashmap
   */
  public abstract void fromHashMap(HashMap<String, String> hashMap);

  public boolean isAlreadyExisted() {
    Entity[] entities = findEntities(new HashMap<>() {{
    }});
    if (entities == null) {
      return false;
    }
    for (Entity entity: entities) {
      if (entity.itemsListKey().equals(itemsListKey())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Create input models for creating an entity
   * @return an array of input models
   */
  public abstract PromptModelContainer promptModelContainer();

  /**
   * Create input models for creating an entity
   * @return an array of input models
   */
  public abstract PromptModelContainer creationPromptModelContainer();

  /**
   * Create input models for creating an entity
   * @return an array of input models
   */
  public abstract PromptModelContainer findingPromptModelContainer();


  /**
   * Create input models for creating an entity
   * @return an array of input models
   */
  public abstract PromptModelContainer editingPromptModelContainer();

  public abstract String itemsListKey();

}
