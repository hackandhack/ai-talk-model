# ai-talk-model
A Java-based conversational AI talk model for iterative development and experimentation.

## CI/CD krav

- **Dockerfile** plasseres i `./docker/Dockerfile` eller sett miljøvariabelen `DOCKERFILE_PATH` i workflow om du bruker annen plassering.
- **Secrets**:  
  - Obligatorisk:  
    - `GITHUB_TOKEN` (automatisk tilgjengelig i GitHub Actions)
  - Valgfritt for flere registries:  
    - `DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN` (for Docker Hub)
    - `AWS_ACCOUNT_ID`, `AWS_REGION` og en IAM-rolle for OIDC (`arn:aws:iam::<AWS_ACCOUNT_ID>:role/GitHubActionsOIDCRole` for ECR)
- **Anbefalt**:  
  - Opprett/finn GitHub Packages/Container permissions policy slik at `packages: write` er tillatt for workflow (Settings → Actions → General → Workflow permissions → "Read and write permissions").
- For ECR med OIDC: Opprett IAM-rolle med permissions til å pushe til ECR og tillat GitHub OIDC som identity provider i AWS.

## Hvordan bruke

- **Push en tag** (f.eks. `v1.2.3`) for å trigge release og publish av container image.
- **Push til main** trigger vanlig CI, build, og publish med tag `ci-<short-sha>`.

---

Se også `LOCAL_DEVELOPMENT.md` for instruksjoner om lokal oppstart, secrets og API-bruk.
