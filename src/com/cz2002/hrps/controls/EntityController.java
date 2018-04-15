package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.InputContainerBoundary;
import com.cz2002.hrps.boundaries.OutputBoundary;
import com.cz2002.hrps.entities.Entity;
import com.cz2002.hrps.models.ItemsList;
import com.cz2002.hrps.models.PromptModel;
import com.cz2002.hrps.models.PromptModelContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EntityController implements Control {

  public void index() { }

  public <T extends Entity> T create(T t) {
    T entity = (T) t.newInstance();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      entity.creationPromptModelContainer()
    );
    HashMap<String, String> hashMap = inputContainerBoundary.getInputContainer(true);
    entity.fromHashMap(hashMap);
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
    HashMap<String, String> queries = inputContainerBoundary.getInputContainer(false);
    T[] ts = (T[]) t.newInstance().findEntities(queries);
    return ts;
  }

  public <T extends Entity> T update(T t) {
    T entity = find(t);
    printEntity("Target Item", entity);
    HashMap<String, String> hashMap = entity.toHashMap();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      entity.editingPromptModelContainer()
    );
    HashMap<String, String> updatedHashMap = inputContainerBoundary.getInputContainer(false);
    for (Map.Entry<String, String> entry : updatedHashMap.entrySet()) {
      hashMap.replace(entry.getKey(), entry.getValue());
    }
    if (entity.update(hashMap)) {
      new Boundary().alertSuccessful();
      return entity;
    }
    new Boundary().alertFailed();
    return null;
  }


  public <T extends Entity> T delete(T t) {
    T entity = find(t);
    printEntity("Target Item", entity);
    if(new Boundary().inputBoolean(
      "Are you sure you want to delete it?",
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
    if (ts.length == 0) {
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
    String key = inputBoundary.getInput(true).getValue();
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
      "Find " + object,
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      promptModelContainer
    );
    HashMap<String, String> searchQueries = inputContainerBoundary.getInputContainer(false);
    for (Map.Entry<String, String> entry : queries.entrySet()) {
      searchQueries.put(entry.getKey(), entry.getValue());
    }
    T[] ts = (T[]) t.newInstance().findEntities(searchQueries);
    return ts;
  }

  protected <T extends Entity> void printAll(String object, T t) {
    T[] ts = (T[]) t.newInstance().findEntities(new HashMap<String, String>() {{
    }});
    printEntities("All " + object, ts);
  }

}
