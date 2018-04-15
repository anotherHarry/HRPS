package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.OutputBoundary;
import com.cz2002.hrps.entities.Entity;

import java.util.HashMap;

public interface Control {

  default public void index() {}

  default public Entity create() {
    return null;
  }

  default public Entity find() {
    return null;
  }

  default public Entity[] findList() {
    return null;
  }

  default public Entity update() {
    return null;
  }

  default public Entity delete() {
    return null;
  }

  default public void printEntity(String title, Entity entity) {
    new OutputBoundary().printHashMap(title, entity.toHashMap());
  }

  default public void printEntities(String title, Entity[] entities) {
    HashMap<String, String>[] hashMaps = (HashMap<String, String>[]) new HashMap<?,?>[entities.length];
    for (int i = 0; i < entities.length; i++) {
      hashMaps[i] = entities[i].toHashMap();
    }
    new OutputBoundary().printHashMaps(title, hashMaps);
  }

}
