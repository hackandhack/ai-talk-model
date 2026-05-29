LOCAL DEVELOPMENT

This file describes how to run the application and the monitoring stack locally using docker compose.

Preparations
- Create a file named `.env` in the repo root (do NOT commit it). Add:

  HF_TOKEN=your_huggingface_token
  OPENAI_API_KEY=your_openai_api_key (optional, only if using openai provider)

- If the image is private on GHCR, login locally:

  echo <PERSONAL_ACCESS_TOKEN> | docker login ghcr.io -u <github-username> --password-stdin

Running locally
- Build and run using Maven wrapper:

  ./mvnw spring-boot:run

- Or build jar and run:

  ./mvnw -DskipTests package
  java -jar target/*.jar

- Start the stack (compose) if you want metrics and alerting:

  docker compose up -d

- Services:
  - App (Spring Boot): http://localhost:8080 (Actuator: /actuator/health, /actuator/prometheus)
  - Assistant endpoint: POST http://localhost:8080/api/assistant
  - Alias endpoint: POST http://localhost:8080/api/ai/chat
  - Prometheus: http://localhost:9090
  - Alertmanager: http://localhost:9093

Example POST to assistant (Hugging Face Grok 4-3 is default):

curl -s -X POST http://localhost:8080/api/assistant \
  -H "Content-Type: application/json" \
  -d '{"prompt":"Say hello","provider":"hf"}'

Or to the alias path expected by your tests:

curl -s -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"prompt":"Say hello","provider":"hf"}'

Repository secrets (add these in GitHub: Settings → Secrets and variables → Actions)
- HF_TOKEN — Hugging Face token
- OPENAI_API_KEY — OpenAI API key (optional)
- SMTP_USER — SMTP username (e.g. your Gmail address)
- SMTP_PASSWORD — Gmail App Password or other SMTP password
- SMTP_HOST — smtp.gmail.com
- SMTP_PORT — 587
- EMAIL_FROM — no-reply@yourdomain

Notes
- The CI workflow references these secrets but does NOT store values. Add them in the repo before merging.
- The Docker build in CI uses the repository GITHUB_TOKEN to authenticate to ghcr.io. Ensure the repository allows packages: write for workflows if your organization blocks default tokens.
