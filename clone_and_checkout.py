import git
import os


def clone_and_checkout(repo_url, tag, file_path, output_dir):
    """
    Clona um repositório Git, faz checkout em uma tag específica e recupera um arquivo.

    :param repo_url: URL do repositório Git
    :param tag: Tag específica para checkout
    :param file_path: Caminho do arquivo no repositório
    :param output_dir: Diretório onde o arquivo será salvo
    """
    repo_dir = os.path.join(output_dir, "temp_repo")

    try:

        print(f"Clonando o repositório {repo_url}...")
        repo = git.Repo.clone_from(repo_url, repo_dir)

        print(f"Fazendo checkout na tag {tag}...")
        repo.git.checkout(tag)

        file_full_path = os.path.join(repo_dir, file_path)

        if not os.path.exists(file_full_path):
            raise FileNotFoundError(f"O arquivo {file_path} não foi encontrado na tag {tag}.")

        output_file_path = os.path.join(output_dir, os.path.basename(file_path))
        with open(file_full_path, "r") as src_file:
            with open(output_file_path, "w") as dest_file:
                dest_file.write(src_file.read())

        print(f"Arquivo {file_path} recuperado com sucesso e salvo em {output_file_path}.")

    finally:
        if os.path.exists(repo_dir):
            import shutil
            shutil.rmtree(repo_dir)