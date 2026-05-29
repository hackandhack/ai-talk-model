FROM openjdk:17-slim

RUN apt-get update && apt-get install -y python3 python3-pip git && rm -rf /var/lib/apt/lists/*
RUN pip3 install --no-cache-dir huggingface_hub

WORKDIR /app

COPY . .

# Last ned modell ved oppstart (tilpass repo_id)
CMD ["sh", "-c", "python3 -c 'from huggingface_hub import snapshot_download; snapshot_download(\"hackandhack/ai-talk-model\", cache_dir=\"/app/models\")' && java -jar target/*.jar"]