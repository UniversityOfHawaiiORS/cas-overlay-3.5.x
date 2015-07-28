copy uh_cas.properties.sample into ~/kuali/main/cas/uh_cas.properties
configure
deploy war 


NOTE: for the uh back door CAS user admin pages to display the database you are using must have been granted access to the ORSTEST.uh_ors_test_users table
      to do this you must run this command as ORSTEST oracle user 
      	GRANT SELECT, INSERT, UPDATE, DELETE ON uh_ors_test_users TO {database_configured};


This project does not build in JAVA 8.   Use Java 7 to build underlying cas and this overlay.
