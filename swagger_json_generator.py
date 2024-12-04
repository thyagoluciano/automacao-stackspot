from typing import Any, List

import yaml


class SwaggerJsonGenerator:
    def __init__(self, swagger_file_path: str):
        with open(swagger_file_path) as file:
            self.swagger_spec = yaml.safe_load(file)
        self.components = self.swagger_spec.get('components', {})
        self.schemas = self.components.get('schemas', {})
        self.parameters = self.components.get('parameters', {})
        self.responses = self.components.get('responses', {})
        self.paths = self.swagger_spec.get('paths', {})

    def resolve_refs(self, obj):
        if isinstance(obj, dict):
            # Se o objeto for um dicionário, iteramos sobre suas chaves e valores
            for key, value in obj.items():
                if key == "allOf":
                    if '$ref' in value:
                        continue
                if key == "$ref":
                    # Se encontramos a chave $ref, chamamos o método process_ref
                    resolved_value = self.resolve_schema_reference(value)
                    return self.resolve_refs(resolved_value)  # Recursivamente resolve o valor retornado
                else:
                    # Se o valor for um dicionário ou lista, chamamos a função recursivamente
                    obj[key] = self.resolve_refs(value)
        elif isinstance(obj, list):
            # Se o objeto for uma lista, iteramos sobre os itens
            for i in range(len(obj)):
                obj[i] = self.resolve_refs(obj[i])
        return obj  # Retorna o objeto com as referências resolvidas

    def get_resource_properties(self, path:str, method:str) -> dict:
        resource_obj = self.paths.get(path)
        if not resource_obj:
            raise ValueError(f"Caminho não encontrado: {path}")

        method_obj = resource_obj.get(method.lower())
        if not method_obj:
            raise ValueError(f"Método {method} não encontrado para o caminho {path}")

        return {
            "parameters": method_obj.get('parameters', {}),
            "requestBody": method_obj.get('requestBody', {}),
            "responses": method_obj.get('responses', {}),
        }

    def resolve_parameters_reference(self, ref: str) -> dict[Any, Any]:
        if not ref.startswith('#/'):
            raise ValueError(f"Referência inválida: {ref}")

        parts = ref.split('/')
        if len(parts) != 4 or parts[1] != 'components' or parts[2] != 'parameters':
            raise ValueError(f"Formato de referência inválido: {ref}")

        parameter_name = parts[3]
        if parameter_name not in self.parameters:
            raise ValueError(f"Parameter não encontrado: {parameter_name}")

        return self.parameters[parameter_name]

    def resolve_responses_reference(self, ref: str) -> dict[Any, Any]:
        if not ref.startswith('#/'):
            raise ValueError(f"Referência inválida: {ref}")

        parts = ref.split('/')
        if len(parts) != 4 or parts[1] != 'components' or parts[2] != 'responses':
            raise ValueError(f"Formato de referência inválido: {ref}")

        parameter_name = parts[3]
        if parameter_name not in self.responses:
            raise ValueError(f"Responses não encontrado: {parameter_name}")

        return self.responses[parameter_name]

    def resolve_schema_reference(self, ref: str) -> dict[Any, Any]:
        if not ref.startswith('#/'):
            raise ValueError(f"Referência inválida: {ref}")

        parts = ref.split('/')

        if len(parts) != 4 or parts[1] != 'components' or parts[2] != 'schemas':
            raise ValueError(f"Formato de referência inválido: {ref}")

        schema = parts[3]
        if schema not in self.schemas:
            raise ValueError(f"Schema não encontrado: {schema}")

        return self.schemas[schema]

    def build_parameters(self, parameters: List[dict]):
        list_parameters = []
        for parameter in parameters:
            if '$ref' in parameter:
                list_parameters.append(
                    self.resolve_parameters_reference(parameter['$ref'])
                )

        return list_parameters

    def build_request(self, param: dict):
        schema = param.get("content").get("application/json; charset=utf-8").get("schema")
        if not schema:
            raise ValueError("Schema JSON não encontrado na resposta")

        return self.resolve_schema_reference(schema['$ref'])

    def build_data_type(self, request: dict):
        properties = {}
        if request.get('type') == 'object':
            for key, value in request.get('properties', {}).items():
                if '$ref' in value:
                    ref = self.resolve_schema_reference(value['$ref'])
                    ref['required'] = False
                    if key in request.get('required'):
                        ref['required'] = True
                    properties[key] = ref
                else:
                    properties[key] = value
        return properties

    def build_response(self, param) -> dict:
        result = {}
        for key, value in param.items():
            if '$ref' in value:
                response = self.resolve_responses_reference(value['$ref'])
                content = response.get('content')
                if content == {}:
                    result[key] = content
                else:
                    schema = response.get("content").get("application/json; charset=utf-8").get("schema")
                    if not schema:
                        raise ValueError("Schema JSON não encontrado na resposta")

                    result[key] = self.resolve_refs(schema)
            else:
                schema = value.get("content").get("application/json; charset=utf-8").get("schema")
                if not schema:
                    raise ValueError("Schema JSON não encontrado na resposta")

                obj_schema = self.resolve_refs(schema)

                if 'discriminator' in obj_schema:
                    for k, v in obj_schema.get('discriminator').get('mapping').items():
                        result[f"{key}_{v.split('/')[-1]}"] = self.resolve_refs({'$ref': v})['allOf'][1]
                else:
                    result[key] = self.resolve_refs(obj_schema)

        return result

    def generate_type_value(self, schema):
        if schema['type'] == 'string':
            return "string"
        elif schema['type'] == 'integer':
            return 'integer'
        elif schema['type'] == 'number':
            return 'number'
        elif schema['type'] == 'boolean':
            return 'boolean'
        elif schema['type'] == 'array':
            return [self.generate_type_value(schema['items'])]
        elif schema['type'] == 'object':
            return self.generate_request(schema)
        return None

    def generate_request(self, schema):
        request = {}
        for key, value in schema.get('properties', {}).items():
            request[key] = self.generate_type_value(value)
        return request