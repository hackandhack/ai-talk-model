LOCAL DEVELOPMENT

This file describes how to run the application and the monitoring stack locally using docker compose.

Preparations
- Create a file named `.env` in the repo root (do NOT commit it). Add:

  HF_TOKEN=your_huggingface_token

- If the image is private on GHCR, login locally:

  echo <PERSONAL_ACCESS_TOKEN> | docker login ghcr.io -u <github-username> --password-stdin

Running locally
- Start the stack:

  docker compose up -d

- Services:
  - App (Spring Boot): http://localhost:8080 (Actuator: /actuator/health, /actuator/prometheus)
  - Prometheus: http://localhost:9090
  - Alertmanager: http://localhost:9093

Repository secrets (add these in GitHub: Settings → Secrets and variables → Actions)
- HF_TOKEN — Hugging Face token
- SMTP_USER — SMTP username (e.g. your Gmail address)
- SMTP_PASSWORD — Gmail App Password or other SMTP password
- SMTP_HOST — smtp.gmail.com
- SMTP_PORT — 587
- EMAIL_FROM — no-reply@yourdomain

Notes
- The CI workflow references these secrets but does NOT store values. Add them in the repo before merging.
- The Docker build in CI uses the repository GITHUB_TOKEN to authenticate to ghcr.io. Ensure the repository allows packages: write for workflows if your organization blocks default tokens.
