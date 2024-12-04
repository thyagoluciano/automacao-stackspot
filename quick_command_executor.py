import os
import re

from dotenv import load_dotenv

from reemote_quick_command_client import RemoteQuickCommandClient


class QuickCommandExecutor:
    def __init__(self):
        load_dotenv()
        self.client_id = os.getenv('SS_CLIENT_ID')
        self.client_secret = os.getenv('SS_CLIENT_SECRET')
        self.realm = os.getenv('SS_REALM')
        self.remote_clients = {}

    def add_quick_command(self, command_name: str, quick_command: str):
        self.remote_clients[command_name] = RemoteQuickCommandClient(
            self.client_id, self.client_secret, self.realm, quick_command
        )

    def execute_quick_command(self, command_name: str, request: str):
        if command_name not in self.remote_clients:
            raise ValueError(f"Quick Command '{command_name}' não foi registrado.")

        remote = self.remote_clients[command_name]
        payload = remote.execute_quick_command(request)
        return payload

    def extract_code(self, payload, step, tag):
        answer_text = payload['steps'][step]['step_result']['answer']
        codes = re.findall(rf'```{tag}(.*?)```', answer_text, re.DOTALL)
        if codes:
            return [code.strip() for code in codes]
        else:
            return "Nenhum código encontrado para a tag especificada."
