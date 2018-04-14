package com.cz2002.hrps.controls;

import com.cz2002.hrps.entities.Entity;

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

}
