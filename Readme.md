# Excel to MySQL Data Loader

A Java-based utility that reads Excel files and inserts data into a MySQL database. Each Excel sheet maps to a corresponding database table, and column headers must match table column names.

## Features

- Supports `.xlsx` files (Apache POI based)
- Dynamic sheet and table name mapping
- Uses prepared statements (safe from SQL injection)
- Batch insert for performance
- Modular and extensible code structure

---

## Requirements

- Java 11+
- MySQL 5.7 or higher
- Maven or manual dependency setup for:
    - Apache POI
    - MySQL JDBC Driver

---

## Project Structure

```
.
├── Main.java               # Entry point, reads arguments and orchestrates
├── ExcelReader.java        # Reads data from Excel sheet
├── SheetData.java          # POJO to hold sheet data
├── SQLBuilder.java         # Builds dynamic INSERT SQL queries
├── DatabaseInserter.java   # Handles DB connection and batch insertion
```

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/excel-to-mysql-loader.git
cd excel-to-mysql-loader
```

### 2. Add Required JARs

If you're not using Maven, manually add:
- Apache POI libraries
- MySQL Connector/J

Or, if you're using Maven:

```xml
<!-- pom.xml dependencies -->
<dependencies>
  <dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.3</version>
  </dependency>
  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
  </dependency>
</dependencies>
```

---

## Usage

### Compile

```bash
javac Main.java ExcelReader.java SheetData.java SQLBuilder.java DatabaseInserter.java
```

### Run

```bash
java Main <ExcelFilePath> <SheetName> [<TableName>]
```

#### Examples:

```bash
# Uses sheet name as table name
java Main data.xlsx Student

# Uses custom table name
java Main data.xlsx Student student_archive
```

---

## Assumptions

- The Excel sheet's first row contains **column headers**.
- Each column header exactly matches a **column in the target table**.
- The sheet name is case-insensitive (converted to lowercase when building the SQL).
- The database schema is already created with matching tables.

---

## Enhancements (TODO)

- Add support for reading all sheets in a file
- Add command-line flags for DB config
- Export data from DB to Excel
- Add logging and error tracking
- Create GUI version

---

## License

MIT License — feel free to fork and contribute!

---

## Author

**Your Name**  
[GitHub](https://github.com/your-username)
