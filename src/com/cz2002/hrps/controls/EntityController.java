package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.InputContainerBoundary;
import com.cz2002.hrps.boundaries.OutputBoundary;
import com.cz2002.hrps.entities.Entity;
import com.cz2002.hrps.models.InputModel;
import com.cz2002.hrps.models.ItemsList;
import com.cz2002.hrps.models.PromptModel;
import com.cz2002.hrps.models.PromptModelContainer;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class EntityController {

  public <T extends Entity> T create(T t) {
    T entity = (T) t.newInstance();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      entity.creationPromptModelContainer()
    );
    HashMap<String, String> hashMap = inputContainerBoundary.getInputContainer(true, true);
    if (hashMap == null) {
      return null;
    }
    entity.fromHashMap(hashMap);
    if (entity.isAlreadyExisted()) {
      new Boundary().alertAlreadyExist();
      return null;
    }
    if (entity.create()) {
      new Boundary().alertSuccessful();
      return entity;
    }
    new Boundary().alertFailed();
    return null;
  }

  public <T extends Entity> T find(T t) {
    T[] ts = findList(t);
    return findWith(ts, "Search Results", t);
  }

  public <T extends Entity> T[] findList(T t) {
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      t.newInstance().findingPromptModelContainer()
    );
    HashMap<String, String> queries = inputContainerBoundary.getInputContainer(false, true);
    if (queries == null) {
      return null;
    }
    T[] ts = (T[]) t.newInstance().findEntities(queries);
    return ts;
  }

  public <T extends Entity> T update(T t) {
    T entity = find(t);
    if (entity == null) {
      return null;
    }
    printEntity("Selected Item", entity);
    HashMap<String, String> hashMap = entity.toHashMap();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      entity.editingPromptModelContainer()
    );
    HashMap<String, String> updatedHashMap = inputContainerBoundary.getInputContainer(false, true);
    if (updatedHashMap == null) {
      return null;
    }
    hashMap.putAll(updatedHashMap);
    if (entity.update(hashMap)) {
      new Boundary().alertSuccessful();
      return entity;
    }
    new Boundary().alertFailed();
    return null;
  }


  public <T extends Entity> T delete(T t) {
    T entity = find(t);
    if (entity == null) {
      return null;
    }
    printEntity("Selected Item", entity);
    if(new Boundary().inputBoolean(
      "Are you sure you want to delete it?",
      true,
      true).getValue()
      ) {
      if (entity.delete()) {
        new Boundary().alertSuccessful();
        return entity;
      } else {
        new Boundary().alertFailed();
      }
    }
    return null;
  }

  protected void printEntity(String title, Entity entity) {
    new OutputBoundary().printHashMap(title, entity.toHashMap());
  }

  protected void printEntities(String title, Entity[] entities) {
    HashMap<String, String>[] hashMaps = (HashMap<String, String>[]) new HashMap<?,?>[entities.length];
    for (int i = 0; i < entities.length; i++) {
      hashMaps[i] = entities[i].toHashMap();
    }
    new OutputBoundary().printHashMaps(title, hashMaps);
  }

  private <T extends Entity> T findWith(T[] ts, String object, T t) {
    if (ts == null) {
      return null;
    } else if (ts.length == 0) {
      new Boundary().alertNotFound();
      return null;
    } else if (ts.length == 1) {
      return ts[0];
    }
    printEntities(object, ts);
    String[] items = new String[ts.length];
    for (int i = 0; i < ts.length; i++) {
      items[i] = ts[i].itemsListKey();
    }
    InputBoundary inputBoundary = new InputBoundary(
      new PromptModel("find", new ItemsList(
        "Choose the item",
        items
      ))
    );
    InputModel<String> input = inputBoundary.getInput(true, true);
    if (!input.isSucceed()) {
      return null;
    }
    String key = input.getValue();
    for (T entity: ts) {
      if (entity.itemsListKey().equals(key)) {
        return entity;
      }
    }
    return null;
  }

  protected <T extends Entity> T findWith(HashMap<String, String> queries, String object, T t) {
    T[] ts = findListWith(queries, object, t);
    return findWith(ts, object, t);
  }

  protected <T extends Entity> T[] findListWith(HashMap<String, String> queries, String object, T t) {
    ArrayList<PromptModel> promptModels = new ArrayList<>();
    for (PromptModel promptModel: t.newInstance().findingPromptModelContainer().getPromptModels()) {
      if (queries.keySet().contains(promptModel.getKey())) {
        continue;
      }
      promptModels.add(promptModel);
    }
    PromptModelContainer promptModelContainer = new PromptModelContainer(
      "Search for " + object,
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      promptModelContainer
    );
    HashMap<String, String> searchQueries = inputContainerBoundary.getInputContainer(false, true);
    if (searchQueries == null) {
      return null;
    }
    searchQueries.putAll(queries);
    T[] ts = (T[]) t.newInstance().findEntities(searchQueries);
    return ts;
  }

  protected <T extends Entity> void printAll(String object, T t) {
    T[] ts = (T[]) t.newInstance().findEntities(new HashMap<String, String>() {{
    }});
    if (ts == null) {
      return;
    } else if (ts.length == 0) {
      new Boundary().alertEmpty();
    } else {
      printEntities("All " + object, ts);
    }
  }

}
