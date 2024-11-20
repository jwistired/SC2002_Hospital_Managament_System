# Hospital Management System

![Banner](Banner.png)


## Table of Content
- [Compile Instructions](#compile-instructions)
- [Testing the Application](#testing-the-application)
- [Printing Information](#printing-information)

## Compile Instructions
For Windows:
```
./compile
```

For Linux/Mac:
Compile.sh needs to be given executable permissions first. You can do that by:
```bash
chmod +x compile.sh
```
Then to run:
```bash
./compile.sh
```

## Testing the Application

### Login Credentials

Upon first run, use the following default login credentials:

	•	Administrator:
	•	User ID: admin
	•	Password: password (you will be prompted to change it upon first login)
	•	Doctor:
	•	User ID: doc1
	•	Password: password (change upon first login)
	•	Pharmacist:
	•	User ID: pharm1
	•	Password: password (change upon first login)
	•	Patient:
	•	User ID: patient1
	•	Password: password (change upon first login)

### Printing information

Use the following:
```bash
java -jar jdeserialize-1.2.jar src/database/users.ser
```
Javadoc file location:

	• src\doc
 
 .class file location:
 
	• bin
