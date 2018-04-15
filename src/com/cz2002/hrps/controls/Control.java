package com.cz2002.hrps.controls;

import com.cz2002.hrps.entities.Entity;


public interface Control {

  void index();

  <T extends Entity> T create(T t);

  <T extends Entity> T find(T t);

  <T extends Entity> T[] findList(T t);

  <T extends Entity> T update(T t);

  <T extends Entity> T delete(T t);

}
