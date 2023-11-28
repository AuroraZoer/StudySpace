# StudySpace Application

## Documentation

### Outline

The [outline](./doc/Project Outline.pdf) is in the `doc` folder as a pdf file.

### Alpha milestone

The [Alpha milestone report](./doc/Project Alpha submission.pdf) is in the `doc` folder as a pdf file. Click [here](https://youtu.be/0Scy31VG_Yc) to watch the Alpha milestone video.

### Beta milestone

The [Beta milestone report](./doc/Project Beta submission.pdf) is in the `doc` folder as a pdf file. Click [here](https://youtu.be/p8YCdtdoTZA) to watch the Beta milestone video.

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

