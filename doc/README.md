# StudySpace Application

## Documentation

- [outline](./Project_Outline.pdf)
- [Alpha milestone report](./Project_Alpha_submission.pdf) 
- Click [here](https://youtu.be/0Scy31VG_Yc) to watch the Alpha milestone video.
- [Beta milestone report](./Project_Beta_submission.pdf)
- Click [here](https://youtu.be/p8YCdtdoTZA) to watch the Beta milestone video.

## Database

### ER

```mermaid
erDiagram
    User ||--o{ StudyTime : Records
    StudyRoom ||--o{ StudyTime: Records
    

User {
    INTEGER UserID PK
    TEXT Username
    TEXT Password
    TEXT Email
}

StudyRoom {
     INTEGER RoomID PK
     TEXT Room
     TEXT Building
     INTEGER MorningAvailability 
     INTEGER AfternoonAvailability 
     INTEGER EveningAvailability 
}

StudyTime {
    INTEGER TimeID PK
    TEXT Time
    TEXT TimeOfDay
    TEXT Date
    INTEGER UserID FK
    INTEGER RoomID FK
}
```

