import os
import pathlib


def directory_to_markdown(parent_directory, target_folder):
    """
    Converte todos os arquivos de um diretório e seus subdiretórios para um arquivo Markdown, filtrando apenas a pasta alvo.

    Args:
        parent_directory (str): Caminho do diretório pai a ser processado
        target_folder (str): Nome da pasta alvo a ser filtrada
    """
    # Verifica se o diretório pai existe
    if not os.path.exists(parent_directory):
        print(f"Diretório {parent_directory} não encontrado.")
        return

    # Cria o arquivo Markdown de saída
    output_file = 'catalogo_arquivos-template.md'

    with open(output_file, 'w', encoding='utf-8') as markdown_file:
        # Percorre todos os arquivos e diretórios no diretório pai
        for root, dirs, files in os.walk(parent_directory):
            # Verifica se a pasta alvo existe no caminho atual
            if target_folder in root:
                for file in files:
                    file_path = os.path.join(root, file)

                    # Obtém o caminho relativo
                    relative_path = os.path.relpath(file_path, parent_directory)

                    # Determina o tipo de linguagem para syntax highlighting
                    file_extension = pathlib.Path(file).suffix[1:]
                    language = file_extension if file_extension else ''

                    # Adiciona cabeçalho com path do arquivo
                    markdown_file.write(f"## {relative_path}\n\n")
                    markdown_file.write(f"**Caminho completo**: `{file_path}`\n\n")

                    try:
                        # Tenta ler o conteúdo do arquivo
                        with open(file_path, 'r', encoding='utf-8') as current_file:
                            file_content = current_file.read()
                            markdown_file.write(f"```{language}\n{file_content}\n```\n\n")
                    except UnicodeDecodeError:
                        # Para arquivos binários
                        markdown_file.write("**[Arquivo binário - não foi possível exibir conteúdo]**\n\n")
                    except Exception as e:
                        markdown_file.write(f"**Erro ao ler arquivo: {str(e)}**\n\n")

    print(f"Catálogo de arquivos gerado: {output_file}")


# Exemplo de uso
if __name__ == "__main__":
    caminho_diretorio_pai = "/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates"
    pasta_alvo = "#{feature_name_lower}"
    directory_to_markdown(caminho_diretorio_pai, pasta_alvo)