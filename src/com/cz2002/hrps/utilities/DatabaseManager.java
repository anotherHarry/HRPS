package com.cz2002.hrps.utilities;

import com.cz2002.hrps.entities.Entity;

import java.util.ArrayList;

public abstract class DatabaseManager {

  protected String database;

  public abstract ArrayList<Entity> load(Entity entity);

  public abstract  <T> boolean write(ArrayList<T> entityList);

}
