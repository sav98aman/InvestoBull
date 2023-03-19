##	About this Project 
	
**How To Run the Program**
	
*	first step our database
*	create a database -- Name is -- investBull ,
*	in application.properties file change the database name,password,user
*	in applications.properties file configure some properties file

```
		
		#db specific properties --MYsql Properties Configure
		# investBull -- database name you can modfies the name and password and username according to 	your database 
		spring.datasource.url=jdbc:mysql://localhost:3306/investBull
		spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
		spring.datasource.username=root
		spring.datasource.password=root
		
		#ORM s/w specific properties
		spring.jpa.hibernate.ddl-auto=update
		spring.jpa.show-sql=true

```

**after that run the Project--**

** then call some api From PostMan**
		
	```
	 
		1. api save the data in database -POST Request-- http://localhost:8080/candles
		
		2. api get the data from database -Get Request-- http://localhost:8080/candles
		
		3. Combine the Candle Accord time- Get Request-- http://localhost:8080/candles/time
			
				at /time - you can inter time as min----   http://localhost:8080/candles/10
				
		4. Find Out Opening BreakOut Time- Get Request-- http://localhost:8080/openingbreakout/time
											        	  http://localhost:8080/openingbreakout/10
					
	```