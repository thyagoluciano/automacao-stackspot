import json
import os
import time
import requests


class RemoteQuickCommandClient:

    def __init__(self, client_id, client_secret, realm, quick_command) -> None:
        self._BASE_URL = os.getenv('SS_BASE_URL')
        self._EXECUTION_URI = "/create-execution/"
        self._CALLBACK_URI = "/callback/"
        self._quick_command = quick_command
        self._client_id = client_id
        self._client_secret = client_secret
        self._realm = realm
        self._token = None

    def __do_post_data(self, url, request, headers=None):
        return requests.post(url, data=request, headers=headers, verify=False, timeout=60)

    def __do_post_json(self, url, request, headers=None):
        return requests.post(url, json=request, headers=headers, verify=False, timeout=60)

    def __do_get(self, url, headers=None):
        return requests.get(url, headers=headers, verify=False, timeout=60)

    def __get_token(self):
        if (self._token is None):
            data = {
                "grant_type": "client_credentials",
                "client_id": self._client_id,
                "client_secret": self._client_secret
            }
            url = f"https://idm.stackspot.com/{self._realm}/oidc/oauth/token"
            self._token = self.__do_post_data(url=url, request=data).json()["access_token"]
        return self._token

    def __get_headers(self):
        token = self.__get_token()
        headers = {
            "Authorization": f"Bearer {token}",
            "Content-Type": "application/json"
        }
        return headers

    def create_execution(self, input_data):
        execution_request = f"{self._BASE_URL}{self._EXECUTION_URI}{self._quick_command}"
        request = {
            "input_data": input_data
        }
        execution_id = self.__do_post_json(url=execution_request, request=request, headers=self.__get_headers()).json()

        return execution_id

    def __not_completed(self, callback_result):
        return callback_result is None or callback_result["progress"]["status"] != "COMPLETED"

    def get_execution_result(self, execution_id):
        count = 1
        tries = 0
        callback_result = None
        print(f"Getting {execution_id} response")
        while self.__not_completed(callback_result):
            try:
                query = f"{self._BASE_URL}{self._CALLBACK_URI}{execution_id}"
                result = self.__do_get(url=query, headers=self.__get_headers())
                callback_result = result.json()
                if (self.__not_completed(callback_result)):
                    print("Aguardar execução do Agente ....")
                    time.sleep(10)
            except requests.exceptions.ProxyError as e:
                print(e)
                tries += 1
                if tries >= 10:
                    print("Limite de tentativas excedido. Verifique sua conexão")
                    raise e
            except requests.exceptions.JSONDecodeError as e:
                print(e)
                tries += 1
                if tries >= 10:
                    print("Limite de tentativas excedido. Verifique sua conexão")
                    raise e
            count += 1

        return callback_result

    def execute_quick_command(self, data):
        query = "https://genai-code-buddy-api.stackspot.com/v1/quick-commands/create-execution/spring-validator-agent"

        request = {
            "input_data": {
                "json": data
            }
        }

        result_id = self.__do_post_data(url=query, request=json.dumps(request), headers=self.__get_headers())
        bytes_value = result_id.content
        string_value = bytes_value.decode('utf-8').strip('"')

        result = self.get_execution_result(string_value)

        return result

    def get_knowledge_source(self):
        output_file = "output.txt"
        query = "https://genai-code-buddy-api.stackspot.com/v1/knowledge-sources/teams-transcript/objects"
        result = self.__do_get(url=query, headers=self.__get_headers())
        payload = result.json()

        with open(output_file, 'w') as file:
            # Iterar sobre cada item no payload
            for item in payload:
                # Extrair o conteúdo da página
                page_content = item.get("page_content", "")
                # Escrever o conteúdo no arquivo
                file.write(page_content + "\n")

        print(f"Dados salvos em {output_file}")