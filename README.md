# Movie List Project
## Objective: Import a .csv file, extract information, transform and load into the database.

### Providing a REST API to query the consecutive, maximum and minimum intervals between the years in which producers were winners.

### Endpoint: Get Producer Award Intervals

**GET** `/movies/winners-interval`

Returns the producers with the smallest and largest intervals between awards.

**Response example:**

```json

{
  "min": [
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    }
  ],
  "max": [
    {
      "producer": "Matthew Vaughn",
      "interval": 13,
      "previousWin": 2002,
      "followingWin": 2015
    }
  ]
}

```

## How to Run the Project
The project was developed with Java version 22: https://download.oracle.com/java/22/archive/jdk-22.0.2_windows-x64_bin.zip

Unzip the file and point the JAVA_HOME environment variable to the package path. Example: C:\Java\jdk22.0.2\bin 

1. Before running the project, make sure your file is located in the import directory inside the project root folder or the .jar executable.
  ```shell

  /import/<your_file>.csv
  /movielist-0.0.1-SNAPSHOT.jar
  ```
### Import File Layout

The file must be in CSV format, separated by semicolons (`;`), with the following header and columns:

| year | title                | studios                | producers                                   | winner |
|------|----------------------|------------------------|----------------------------------------------|--------|
| 2006 | Basic Instinct 2     | MGM, C2 Pictures       | Mario Kassar, Joel B. Michaels and Andrew G. Vajna | yes    |
| 2006 | BloodRayne           | Romar Entertainment    | Uwe Boll, Dan Clarke and Wolfgang Herrold    |        |

- **producers**: If there is more than one producer, separate the names with a comma (`,`) and use `and` before the last name.


2. To run the project, execute the following commands according to your preference or environment:   

```bash

mvn clean package
mvn spring-boot:run
```
or, if you prefer, run the generated jar:

```

mvn clean package
java -jar target/movielist-0.0.1-SNAPSHOT.jar
```
3. Access the application through your browser at the following address:
```
http://localhost:8080/movies/winners-interval
```

4. Additional imported data is available at the address:
```
http://localhost:8080/movies
```
