import os
import pathlib


def directory_to_markdown(directory_path):
    """
    Converte todos os arquivos de um diretório para um arquivo Markdown.

    Args:
        directory_path (str): Caminho do diretório a ser processado
    """
    # Verifica se o diretório existe
    if not os.path.exists(directory_path):
        print(f"Diretório {directory_path} não encontrado.")
        return

    # Cria o arquivo Markdown de saída
    output_file = 'catalogo_arquivos.md'

    with open(output_file, 'w', encoding='utf-8') as markdown_file:
        # Percorre todos os arquivos no diretório
        for root, dirs, files in os.walk(directory_path):
            for file in files:
                file_path = os.path.join(root, file)

                # Obtém o caminho relativo
                relative_path = os.path.relpath(file_path, directory_path)

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
    directory_to_markdown("/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates")