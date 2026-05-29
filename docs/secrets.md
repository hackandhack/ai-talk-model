# Repository Secrets

Dette dokumentet beskriver alle nødvendige secrets som må legges inn i GitHub Actions for at prosjektet skal fungere i produksjon.

## Obligatoriske Secrets

| Secret Name     | Beskrivelse                                      | Brukes av                          | Kommentar                              |
|-----------------|--------------------------------------------------|------------------------------------|----------------------------------------|
| `HF_TOKEN`      | Hugging Face API-token med read-tilgang          | `HuggingFaceService`, Dockerfile   | Må ha tilgang til modell-repositoriet  |
| `JWT_SECRET`    | Hemmelig nøkkel for JWT-signering                | `JwtTokenProvider`                 | Minimum 64 tegn. Generer med `openssl rand -base64 64` |
| `DB_PASSWORD`   | Passord for PostgreSQL                           | `docker-compose.yml`               | Brukes i produksjon                    |
| `REDIS_PASSWORD`| Passord for Redis                                | `docker-compose.yml`               | Brukes for rate limiting og cache      |

## Valgfrie Secrets

| Secret Name       | Beskrivelse                                   | Når det trengs                  | Kommentar                     |
|-------------------|-----------------------------------------------|---------------------------------|-------------------------------|
| `GRAFANA_PASSWORD`| Admin-passord for Grafana                     | docker-compose                  | Kan endres senere             |
| `SMTP_HOST`       | SMTP-server (f.eks. smtp.gmail.com)           | E-postvarsler / Password reset  | -                             |
| `SMTP_PORT`       | SMTP-port (vanligvis 587)                     | E-postvarsler / Password reset  | -                             |
| `SMTP_USER`       | SMTP-brukernavn                               | E-postvarsler / Password reset  | -                             |
| `SMTP_PASSWORD`   | SMTP-passord (bruk App Password for Gmail)    | E-postvarsler / Password reset  | -                             |
| `EMAIL_FROM`      | Avsenderadresse for system-eposter            | Password reset                  | f.eks. no-reply@aitalk.no     |

## Viktige Notater

- `GITHUB_TOKEN` genereres automatisk av GitHub Actions – ikke legg den inn manuelt.
- Bruk **GitHub Environments** (f.eks. `production`) for å beskytte de mest sensitive secretsene.
- `JWT_SECRET` bør være sterk og aldri committes til repoet.