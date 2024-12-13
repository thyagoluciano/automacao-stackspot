import sys

from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler
import subprocess
import time


class StreamlitReloader(FileSystemEventHandler):
    def __init__(self, process):
        self.process = process

    def on_modified(self, event):
        if event.src_path.endswith('.py'):
            print(f"Reloading due to changes in {event.src_path}")
            self.process.terminate()
            self.process.wait()
            self.process = subprocess.Popen([
                sys.executable, "-m", "streamlit", "run",
                "app.py"
            ])


def start_streamlit():
    process = subprocess.Popen([
        sys.executable, "-m", "streamlit", "run",
        "app.py"
    ])

    observer = Observer()
    event_handler = StreamlitReloader(process)

    observer.schedule(event_handler, path='app', recursive=True)
    observer.start()

    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        observer.stop()
    finally:
        observer.join()
        process.terminate()


if __name__ == "__main__":
    start_streamlit()
