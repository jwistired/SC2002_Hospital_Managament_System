```bash
javac -d bin -sourcepath src src/main/HMSApplication.java
java -cp bin main.HMSApplication
```

Testing the Application

Login Credentials

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

Print information inside database by
```bash
java -jar jdeserialize-1.2.jar src/database/users.ser
```
