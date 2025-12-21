# JMMD Language Challenge Android App
***
An Android application for generating and taking language‑learning challenges via the OpenAI API.

## Features
***
- User signup / login
- Generate structured language challenges (multiple choice, write‑in)
- Persist challenges, questions, answers in SQLite database
- Assign challenges to users and track progress
- User profile update (info & password)

## Environment Variables
***
Enter a valid OpenAI API key

Open `gradle.properties` and set:

```
OPENAI_API_KEY=YOUR_OPENAI_API_KEY
```

