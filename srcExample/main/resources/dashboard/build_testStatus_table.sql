CREATE TABLE testStatus (
    	  test_id int(11) NOT NULL AUTO_INCREMENT,
    	  testClass varchar(128) NOT NULL,
    	  testName varchar(128) NOT NULL,
    	  description varchar(128) NOT NULL,
    	  status varchar(128) NOT NULL,
    	  startTime DATETIME NOT NULL,
    	  endTime DATETIME NOT NULL,
    	  executionId BIGINT(20) NOT NULL,
    	  browser varchar(32) NOT NULL,
    	  environment varchar(16) NOT NULL,
    	  testType varchar(32) NOT NULL,
    	  category varchar(32) NOT NULL,
    	  failureReason varchar(64) NOT NULL,

    	  PRIMARY KEY (test_id),
    	  UNIQUE KEY test_id_UNIQUE (test_id)
    	) ENGINE=InnoDB;