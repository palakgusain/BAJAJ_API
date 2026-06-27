# BFHL REST API

Spring Boot REST API for the Chitkara Qualifier (27 June 2026).

## Endpoint

**POST** `/bfhl`

### Request

```json
{
  "data": ["a", "1", "334", "4", "R", "$"]
}
```

### Response (200 OK)

```json
{
  "is_success": true,
  "user_id": "john_doe_17091999",
  "email": "john@xyz.com",
  "roll_number": "ABCD123",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

## Run locally

```bash
mvn spring-boot:run
```

```bash
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data": ["a", "1", "334", "4", "R", "$"]}'
```

## Configuration

Set these environment variables before deploying:

| Variable | Description | Default |
|----------|-------------|---------|
| `USER_FULL_NAME` | Full name (lowercase in response) | `john doe` |
| `USER_DOB` | Date of birth `ddmmyyyy` | `17091999` |
| `USER_EMAIL` | Email address | `john@xyz.com` |
| `USER_ROLL_NUMBER` | College roll number | `ABCD123` |
| `PORT` | Server port | `8080` |

## Deploy to Railway

1. Push this repo to GitHub.
2. Go to [railway.app](https://railway.app) → **New Project** → **Deploy from GitHub**.
3. Select this repository.
4. Railway auto-detects the `Dockerfile`.
5. Add environment variables (`USER_FULL_NAME`, `USER_DOB`, `USER_EMAIL`, `USER_ROLL_NUMBER`) with your details.
6. After deploy, open **Settings → Networking → Generate Domain**.
7. Submit: `https://<your-domain>/bfhl`

## Deploy to Render

1. Push to GitHub.
2. Go to [render.com](https://render.com) → **New +** → **Web Service**.
3. Connect the repo and choose **Docker**.
4. Set environment variables and create the service.
5. Use the generated URL + `/bfhl`.

## Tests

```bash
mvn test
```
