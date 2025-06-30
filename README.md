# 🩺 Doctor Appointment App — Built with Jetpack Compose

Welcome to *DoctorApp*, a sleek and modern Android application for scheduling doctor appointments, saving medical notes, chatting with an AI assistant, and more — all with a beautiful, intuitive UI.

<div align="center">
  <img src="https://img.shields.io/badge/Kotlin-%230095D5.svg?style=for-the-badge&logo=kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=android&logoColor=white"/>
  <img src="https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black"/>
</div>


## ✨ Features

- 🔐 *Custom Login & Registration*  
  Fully custom username/password login system using Firebase Firestore.

- 🏠 *Home Dashboard*  
  View scheduled appointments, medicine notes, and access features.

- 👨‍⚕ *Doctor List + Scheduling*  
  Browse doctors, check specialization and hours, and schedule appointments.

- 🧾 *Notepad for Medicines*  
  Add your own prescriptions in a minimal, beautiful notepad UI.

- 🧠 *DocChatBot (AI Chatbot)*  
  A fake-GPT styled AI assistant to answer basic health queries in a chat UI.

- 🏥 *Nearby Hospitals*  
  Static (or location-based) list of nearby hospitals with one-click call.

- 🎨 *Aesthetic UI with Jetpack Compose*  
  Smooth animations, clean layout, and modern Android Material Design 3.

---

## 📱 Screenshots

> Add screenshots here (or keep placeholders for now)

- Home Screen
    ![Screenshot 2025-06-30 232549](https://github.com/user-attachments/assets/75fd45f5-c3fc-43ca-886d-d4afb721f924)

- Doctor List
  ![Screenshot 2025-06-30 232615](https://github.com/user-attachments/assets/5a01ff66-5970-4516-b567-f1c8e2702e69)

- Medicine Notes
   ![Screenshot 2025-06-30 232714](https://github.com/user-attachments/assets/388072ea-50e0-449c-be44-d204ed54c511)

- ChatBot
![Screenshot 2025-06-30 232700](https://github.com/user-attachments/assets/9d434399-91b6-480a-99b7-2e38b283eb23)

---

## 🛠 Built With

| Tool/Library             | Purpose                              |
|--------------------------|---------------------------------------|
| *Kotlin*               | Base programming language             |
| *Jetpack Compose*      | UI development                        |
| *Firebase Firestore*   | User authentication & data storage    |
| *Navigation Compose*   | Screen navigation                     |
| *Material3*            | UI Components                         |
| *SharedPreferences*    | Storing medicine notes locally        |
| *Icons / Vector Assets*| Beautiful custom icons                |

---

## 🔐 Authentication Flow

- New users register with:
  - Name, Username, Email, Phone, Password
- Existing users login with:
  - Username & Password
- Data stored under:  
  /users/{username} in Firebase Firestore

---

## 🧠 DocChatBot (AI Chat)

- Chat UI simulates a ChatGPT-like assistant.
- Responds with pre-set replies for demo purposes.
- Clean conversational flow and scrollable chat window.

---


