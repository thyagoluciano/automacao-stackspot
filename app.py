import json
import os

import streamlit as st

from code_2_code import generate_feature, display_output_as_markdown
from quick_command_executor import QuickCommandExecutor
from swagger_json_generator import SwaggerJsonGenerator

from dotenv import load_dotenv

load_dotenv()


def main():
    st.set_page_config(
        page_title="Formulário Swagger",
        layout="wide",
        initial_sidebar_state="expanded"
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
            st.button("Ir para o formulário", on_click=go_to_form)

    elif st.session_state["page"] == "form":

        st.title("Gerar Código com Base no Swagger")
        if "swagger_file" not in st.session_state:
            st.warning("Por favor, faça o upload do arquivo Swagger no menu 'Upload Swagger' antes de continuar.")
        else:
            with st.form("code_generation_form"):
                dup = st.selectbox("Dup", "006")
                method = st.selectbox("Method", "POST")
                feature_name = st.text_input("Feature Name", "BloqueioDesbloqueioDuplicata")
                resource = st.text_input("Resource (Path da API)", "/escrituradora/duplicata/bloquear-desbloquear")
                submitted = st.form_submit_button("Gerar Código")

                if submitted:

                    swagger_json_generator = SwaggerJsonGenerator()
                    swagger_json_generator.load_swagger(st.session_state["swagger_file"])

                    method_obj = swagger_json_generator.get_resource_properties(resource, method)

                    parameters = swagger_json_generator.build_parameters(method_obj['parameters'])
                    request_body = swagger_json_generator.build_request(method_obj['requestBody'])
                    request = swagger_json_generator.resolve_refs(request_body)

                    response_body = swagger_json_generator.build_response(method_obj['responses'])

                    responses_object = {}
                    for response in response_body:
                        responses_object[response] = swagger_json_generator.generate_request(response_body[response])

                    print(json.dumps(parameters, indent=4))
                    # save_file(f'{output_dir}/code/header.json', parameters)

                    print(json.dumps(request, indent=2))
                    # save_file(f'{output_dir}/code/request.json', request)

                    print(json.dumps(swagger_json_generator.generate_request(request), indent=4))

                    print(json.dumps(responses_object, indent=2))
                    # save_file(f'{output_dir}/code/response.json', responses_object)

                    # {payload_fields}
                    generate_feature(dup, feature_name, resource, f"{resource}/retorno")

                    executor = QuickCommandExecutor()
                    executor.add_quick_command("spring_validator", os.getenv('SS_SPRING_VALIDATOR_AGENT'))

                    obj_request = {
                        "migration": {"nome": dup},
                        "header": parameters,
                        "parametros": {
                            "http_verb": "POST",
                            "request_mapping": resource,
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
                    # st.title("Interface de Documentação")
                    # st.code("\n".join(swagger), language="java")
                    # print(validator)
                    # save_code_file(f'{output_dir}/code/validator.java', validator)
                    st.title("Classe de Validação")
                    st.code("\n".join(validator), language="java")
                    # print(migration)
                    # save_code_file(f'{output_dir}/code/migrations.sql', migration)
                    # st.title("Script SQL de Migration")
                    # st.code("\n".join(migration), language="java")
                    code = display_output_as_markdown("./output")
                    st.title("Funcionalidade")
                    st.write(code)

                    st.subheader("Resultado do Processamento do Swagger")

                    st.subheader("Inputs Fornecidos")
                    st.write(f"**Method:** {method}")
                    st.write(f"**Resource:** {resource}")

                    st.success("Código gerado com sucesso (simulado).")


if __name__ == "__main__":
    main()
