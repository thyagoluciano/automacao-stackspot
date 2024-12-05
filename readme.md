# Projeto StackSpot Remote Quick Command + Streamlit com Docker

## 📋 Visão Geral

Este projeto utiliza Python e o StackSpot AI para automatizar a geração de código com base em especificações Swagger/OpenAPI. Ele foi desenvolvido com uma interface interativa em Streamlit para facilitar a usabilidade, permitindo que desenvolvedores importem arquivos Swagger e obtenham código funcional rapidamente.

## 🛠️ Pré-requisitos

- Python 3.11+
- Docker
- Docker Compose
- Git

## 📦 Configuração do Projeto

### Clonar o Repositório

```bash
git clone https://github.seu-usuario/seu-projeto.git
cd seu-projeto
```

### Variáveis de Ambiente

O projeto utiliza variáveis de ambiente para configuração. Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:

```env
# Url Base do StackSpot
SS_BASE_URL = "https://genai-code-buddy-api.stackspot.com/v1/quick-commands"

# Credenciais para utilização do StackSpot
SS_CLIENT_ID = ""
SS_CLIENT_SECRET = ""
SS_SPRING_VALIDATOR_AGENT = "spring-validator-agent"
SS_REALM = "nuclea-ia"

# Opcional Endereço do Repo de Código onde contem o swagger
REPO_URL = ""
```

**Importante:** Não commite o arquivo `.env` no controle de versão. Utilize o `.env.example` como template.

## 🐳 Executando com Docker

### Construir e Iniciar Containers

```bash
# Construir as imagens
docker build -t streamlit-app .

# Iniciar os serviços
docker run -p 8501:8501 streamlit-app

# Iniciar com docker compose + build
docker-compose up --build

# Iniciar com docker compose
docker-compose up -d
```

### Parar os Containers

```bash
docker-compose down
```

## 🖥️ Desenvolvimento Local

### Instalação de Dependências

```bash
# Criar ambiente virtual
python -m venv venv
source venv/bin/activate  # No Windows: venv\Scripts\activate

# Instalar dependências
pip install -r requirements.txt
```

### Executar Aplicação Localmente

```bash
streamlit run app.py
```

## 📝 Estrutura do Projeto

```
automacao-stackspot/
│── __init__.py
│── main.py
├── Dockerfile
├── docker-compose.yml
├── requirements.txt
├── .env.example
└── README.md
```

## 🔒 Boas Práticas de Segurança

- Nunca compartilhe credenciais sensíveis
- Utilize senhas fortes e únicas
- Rode a aplicação com usuário não-root no Docker
- Mantenha as dependências atualizadas


## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie sua feature branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request
```