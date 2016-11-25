use test;
CREATE TABLE if not EXISTS feature (
  item_id int(11) NOT NULL,
  description VARCHAR(200) NOT NULL,
  PRIMARY KEY (item_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into feature values(1, 'aaaa');

CREATE TABLE if not EXISTS item (
  id int(11) NOT NULL,
  name VARCHAR(200) NOT NULL,
  manu VARCHAR(200),
  weight int(6),
  price decimal(6,2),
  popularity int(1),
  includes int(1),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not EXISTS category (
  id int(11) NOT NULL,
  description VARCHAR(200),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not EXISTS item_category (
  item_id int(11) NOT NULL,
  category_id int(11) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not EXISTS mytest (
  id int(11) NOT NULL,
  name VARCHAR(200),
  description VARCHAR(200),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not EXISTS mytest_detail (
  id int(11) NOT NULL,
  name VARCHAR(200),
  description VARCHAR(200),
  mytest_id int(11) not null,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;