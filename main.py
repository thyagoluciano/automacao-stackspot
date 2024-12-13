import json
import os

import swagger_json_generator
from clone_and_checkout import clone_and_checkout
from quick_command_executor import QuickCommandExecutor

import os

from dotenv import load_dotenv

load_dotenv()


def save_code_file(path, content):
    try:
        directory = os.path.dirname(path)
        if not os.path.exists(directory):
            os.makedirs(directory)

        if isinstance(content, list):
            content = "\n".join(content)

        with open(path, 'w', encoding='utf-8') as file:
            file.write(content)

        print(f"Arquivo Java salvo com sucesso em: {path}")
        return True
    except Exception as e:
        print(f"Erro ao salvar o arquivo Java: {e}")
        return False


def save_file(path, content: any):
    try:
        directory = os.path.dirname(path)

        if not os.path.exists(directory):
            os.makedirs(directory)

        if isinstance(content, dict):
            content = json.dumps(content, indent=4)
        elif isinstance(content, list):
            content = json.dumps(content, indent=4)
        elif not isinstance(content, str):
            content = str(content)

        with open(path, 'w', encoding='utf-8') as file:
            file.write(content)

        print(f"Arquivo salvo com sucesso em: {path}")
        return True
    except Exception as e:
        print(f"Erro ao salvar o arquivo: {e}")
        return False


if __name__ == "__main__":
    repo_url = os.getenv('REPO_URL')
    tag = "v1.12.0"
    output_dir = "/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/tmp"
    swagger_file = 'dup-swagger.yaml'
    file_path = f"{output_dir}/temp_repo/{swagger_file}"

#    clone_and_checkout(repo_url, tag, file_path, output_dir)

    swagger_path = f'{output_dir}/{swagger_file}'
    method = 'POST'
    resource = "/escrituradora/duplicata/bloquear-desbloquear"

    swagger_json_generator = swagger_json_generator.SwaggerJsonGenerator()
    swagger_json_generator.load_swagger_file(swagger_path)

    method_obj = swagger_json_generator.get_resource_properties(resource, method)

    # Recupera o objeto header do recurso com as propriedades e se são obrigatorios
    parameters = swagger_json_generator.build_parameters(method_obj['parameters'])

    # Recuperar o objeto de request com um payload de exemplo e um payload de definição de obrigatoriedade
    request_body = swagger_json_generator.build_request(method_obj['requestBody'])
    request = swagger_json_generator.resolve_refs(request_body)

    response_body = swagger_json_generator.build_response(method_obj['responses'])

    responses_object = {}
    for response in response_body:
        responses_object[response] = swagger_json_generator.generate_request(response_body[response])

    # Regras e propriedades do Header
    print(json.dumps(parameters, indent=4))
    save_file(f'{output_dir}/code/header.json', parameters)
    # Objeto com todas as propriedades, quais são obrigatorias, e quais as regras
    print(json.dumps(request, indent=2))
    save_file(f'{output_dir}/code/request.json', request)
    # Imprime um exemplo de objeto de request
    print(json.dumps(swagger_json_generator.generate_request(request), indent=4))
    # Imprime o objeto de response
    print(json.dumps(responses_object, indent=2))
    save_file(f'{output_dir}/code/response.json', responses_object)

    executor = QuickCommandExecutor()
    executor.add_quick_command("spring_validator", os.getenv('SS_SPRING_VALIDATOR_AGENT'))
    # TODO: Gerar Interface de documentação do Swagger baseado no Header, Request, Response
    # TODO: Gerar Classe de Validação baseado no objeto de Request usando o payload de definição de obrigatoriedade
    # TODO: Gerar Migrations Baseado nos scripts de exemplos e nomes da DUP

    obj_request = {
      "migration": {"nome": "006"},
      "header": parameters,
      "parametros": {
        "controller": "CreateInformeAgenteFinanciadorController",
        "request_body": "CreateInformeAgenteFinanciadorRequest",
        "response_body": "CreateInformeAgenteFinanciadorResponse",
        "http_verb": "POST",
        "request_mapping": resource,
        "tags": "solicitacao-informe-agente-financiador",
        "operationId": "solicitacao-informe-agente-financiador",
        "summary": "DUP515, Solicitacao de Informe de Agente Financiador.",
        "description": "DUP0520, Assíncrono, resultado por polling /registradora/solicitacao-informe-agente-financiador"
      },
      "request": request,
      "response": responses_object
    }

    print(json.dumps(obj_request, indent=2))

    payload = executor.execute_quick_command("spring_validator", str(obj_request))
    swagger = executor.extract_code(payload, 0, 'java')
    validator = executor.extract_code(payload, 1, 'java')
    migration = executor.extract_code(payload, 2, 'sql')

    print(swagger)
    save_code_file(f'{output_dir}/code/interface.java', swagger)
    print(validator)
    save_code_file(f'{output_dir}/code/validator.java', validator)
    print(migration)
    save_code_file(f'{output_dir}/code/migrations.sql', migration)

    # TODO: Gerar Documentação de exemplo para publicar no Confluence

