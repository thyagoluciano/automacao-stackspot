# Use uma imagem base com Python
FROM python:3.9-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

RUN mkdir /output

# Copia os arquivos da aplicação para o container
COPY . /app

# Instala as dependências da aplicação
RUN pip install --no-cache-dir -r requirements.txt

# Expõe a porta padrão do Streamlit
EXPOSE 8502

# Comando para iniciar a aplicação Streamlit
CMD ["streamlit", "run", "app.py", "--server.port=8502", "--server.address=0.0.0.0"]