import os
import re

def generate_feature(dup_number, feature_name, h1_resource, h2_resource, output_base_path):
    feature_name_lower = feature_name.lower()
    feature_name_pascal = ''.join(word.capitalize() for word in feature_name.split('-'))
    feature_name_kebab = feature_name_lower.replace("_", "-")
    feature_name_upper = feature_name.upper().replace("-", "_")

    placeholders = {
        "#{dup}": dup_number,
        "#{feature_name_lower}": feature_name_lower,
        "#{feature_name_pascal}": feature_name_pascal,
        "#{feature_name_kebab}": feature_name_kebab,
        "#{feature_name_upper}": feature_name_upper,
        "#{table_name}": dup_number,
        "#{create_resource_feature_name_kebab}": h1_resource,
        "#{query_resource_feature_name_kebab}": h2_resource,
    }

    templates_base_path = os.path.join(os.path.dirname(__file__), "templates") # Garante que o path do template seja encontrado
   # Lista os arquivos na pasta de templates
    for root, _, files in os.walk(templates_base_path):
        for file_name in files:
            if file_name.endswith(".java") or file_name.endswith(".md") or file_name.endswith(".sql"):
                template_file_path = os.path.join(root, file_name)
                output_file_path = os.path.join(
                    output_base_path,
                    os.path.relpath(template_file_path, templates_base_path)
                )

                output_file_path = replace_placeholders_in_path(output_file_path, placeholders)

                # Le o template
                with open(template_file_path) as template_file:
                    template_content = template_file.read()

                # Substitui os placeholders
                new_file_content = replace_placeholders_in_content(template_content, placeholders)

                # Cria os diretórios do output
                os.makedirs(os.path.dirname(output_file_path), exist_ok=True)

                # Salva o arquivo
                with open(output_file_path, 'w') as output_file:
                    output_file.write(new_file_content)

                print(f"Arquivo gerado: {output_file_path}")


def replace_placeholders_in_content(text, placeholders):
    """Substitui placeholders no conteúdo do template."""
    for placeholder, value in placeholders.items():
        text = text.replace(placeholder, value)
    return text


def replace_placeholders_in_path(path, placeholders):
    """Substitui placeholders no caminho do arquivo."""
    for placeholder, value in placeholders.items():
        path = path.replace(placeholder, value)
    return path


def display_output_as_markdown(output_path):
    """Lê os arquivos gerados e retorna o conteúdo em formato Markdown."""
    markdown_text = "## Arquivos Gerados:\n\n"
    try:
        for root, _, files in os.walk(output_path):
            for file_name in files:
                if file_name.endswith(".java") or file_name.endswith(".md") or file_name.endswith(".sql"):
                    file_path = os.path.join(root, file_name)
                    try:
                        with open(file_path) as file:
                            content = file.read()
                            relative_path = os.path.relpath(file_path, output_path)
                            markdown_text += f"### {relative_path}\n\n"
                            if file_name.endswith(".java"):
                                markdown_text += f"```java\n{content}\n```\n\n"
                            elif file_name.endswith(".sql"):
                                markdown_text += f"```sql\n{content}\n```\n\n"
                            else:
                                markdown_text += f"{content}\n\n"
                    except Exception as e:
                        markdown_text += f"Erro ao ler o arquivo {file_path}: {e}\n\n"
    except FileNotFoundError:
        markdown_text += f"Erro: A pasta '{output_path}' não foi encontrada.\n\n"
    return markdown_text

def converter_and_save(code, output_dir):
    try:
        match = re.search(r'public class (\w+)', code)
        if not match:
            print("Não foi possível encontrar o nome da classe no código Java. Ignorando...")
            return

        new_class_name = "Create#{feature_name_pascal}Request"
        new_file_name = "Create#{feature_name_pascal}Request.java"

        new_code = re.sub(r'public class \w+', f'public class {new_class_name}', code)

        os.makedirs(output_dir, exist_ok=True)

        output_file_path = os.path.join(output_dir, new_file_name)

        with open(output_file_path, 'w', encoding='utf-8') as output_file:
            output_file.write(new_code)

        print(f"Código Java atualizado e salvo em: {output_file_path}")

    except Exception as e:
        print(f"Erro ao processar o código Java: {e}")