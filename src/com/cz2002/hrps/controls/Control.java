package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.InputContainerBoundary;
import com.cz2002.hrps.boundaries.OutputBoundary;
import com.cz2002.hrps.entities.Entity;
import com.cz2002.hrps.entities.Room;

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

}
