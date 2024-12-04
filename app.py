import json
import os

import streamlit as st
import yaml

from quick_command_executor import QuickCommandExecutor
from swagger_json_generator import SwaggerJsonGenerator


# Configuração do menu
def main():
    # Configuração da página para layout mais largo
    st.set_page_config(
        page_title="Formulário Swagger",
        layout="wide",  # Define o layout como "wide" para ocupar mais espaço
        initial_sidebar_state="expanded"  # Expande a barra lateral por padrão (opcional)
    )

    if "page" not in st.session_state:
        st.session_state["page"] = "upload"

    def go_to_form():
        st.session_state["page"] = "form"

    st.sidebar.title("Menu")
    menu = st.sidebar.selectbox("Selecione uma opção", ["Upload Swagger", "Gerar Código"])

    if st.session_state["page"] == "upload":
        st.title("Upload do Arquivo Swagger (YAML)")
        swagger_file = st.file_uploader("Faça o upload do arquivo Swagger no formato YAML", type=["yaml", "yml"])

        if swagger_file:
            st.success("Arquivo carregado com sucesso!")
            st.session_state["swagger_file"] = swagger_file
            st.button("Ir para o formulário", on_click=go_to_form)  # Botão para mudar de página

    elif st.session_state["page"] == "form":

        st.title("Gerar Código com Base no Swagger")
        if "swagger_file" not in st.session_state:
            st.warning("Por favor, faça o upload do arquivo Swagger no menu 'Upload Swagger' antes de continuar.")
        else:
            with st.form("code_generation_form"):
                dup = st.text_input("Código da DUP", "520")
                method = st.selectbox("Method", "POST")
                resource = st.text_input("Resource (Path da API)", "/registradora/solicitacao-informe-agente-financiador")
                controller_name = st.text_input("Controller Name", "FinanciadorController")
                request_name = st.text_input("Request Name", "FinanciadorRequest")
                response_name = st.text_input("Response Name", "FinanciadorResponse")
                tags = st.text_input("Tags", "Financiador")
                operation_id = st.text_input("Operation ID", "FinanciadorID")
                summary = st.text_input("Summary", "Financiador Summary")
                description = st.text_area("Description", "Financiador Description")
                submitted = st.form_submit_button("Gerar Código")

                if submitted:

                    swagger_json_generator = SwaggerJsonGenerator()
                    swagger_json_generator.load_swagger(st.session_state["swagger_file"])

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
                    # save_file(f'{output_dir}/code/header.json', parameters)
                    # Objeto com todas as propriedades, quais são obrigatorias, e quais as regras
                    print(json.dumps(request, indent=2))
                    # save_file(f'{output_dir}/code/request.json', request)
                    # Imprime um exemplo de objeto de request
                    print(json.dumps(swagger_json_generator.generate_request(request), indent=4))
                    # Imprime o objeto de response
                    print(json.dumps(responses_object, indent=2))
                    # save_file(f'{output_dir}/code/response.json', responses_object)

                    executor = QuickCommandExecutor()
                    executor.add_quick_command("spring_validator", os.getenv('SS_SPRING_VALIDATOR_AGENT'))
                    # TODO: Gerar Interface de documentação do Swagger baseado no Header, Request, Response
                    # TODO: Gerar Classe de Validação baseado no objeto de Request usando o payload de definição de obrigatoriedade
                    # TODO: Gerar Migrations Baseado nos scripts de exemplos e nomes da DUP

                    obj_request = {
                        "migration": {"nome": dup},
                        "header": parameters,
                        "parametros": {
                            "controller": controller_name,
                            "request_body": request_name,
                            "response_body": response_name,
                            "http_verb": "POST",
                            "request_mapping": resource,
                            "tags": tags,
                            "operationId": operation_id,
                            "summary": summary,
                            "description": description
                        },
                        "request": request,
                        "response": responses_object
                    }

                    payload = executor.execute_quick_command("spring_validator", str(obj_request))
                    swagger = executor.extract_code(payload, 0, 'java')
                    validator = executor.extract_code(payload, 1, 'java')
                    migration = executor.extract_code(payload, 2, 'sql')

                    # print(swagger)
                    # save_code_file(f'{output_dir}/code/interface.java', swagger)
                    st.title("Interface de Documentação")
                    st.code("\n".join(swagger), language="java")
                    # print(validator)
                    # save_code_file(f'{output_dir}/code/validator.java', validator)
                    st.title("Classe de Validação")
                    st.code("\n".join(validator), language="java")
                    # print(migration)
                    # save_code_file(f'{output_dir}/code/migrations.sql', migration)
                    st.title("Script SQL de Migration")
                    st.code("\n".join(migration), language="java")

                    st.subheader("Resultado do Processamento do Swagger")
                    # if isinstance(resource_data, dict):
                    #     st.json(resource_data)
                    # else:
                    #     st.error(resource_data)

                    st.subheader("Inputs Fornecidos")
                    st.write(f"**Method:** {method}")
                    st.write(f"**Resource:** {resource}")
                    st.write(f"**Controller Name:** {controller_name}")
                    st.write(f"**Request Name:** {request_name}")
                    st.write(f"**Response Name:** {response_name}")
                    st.write(f"**Tags:** {tags}")
                    st.write(f"**Operation ID:** {operation_id}")
                    st.write(f"**Summary:** {summary}")
                    st.write(f"**Description:** {description}")

                    st.success("Código gerado com sucesso (simulado).")


if __name__ == "__main__":
    main()
