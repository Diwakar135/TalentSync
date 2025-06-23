import re

def clean_text(text):
    """
    Cleans raw text by removing special characters, multiple spaces, and newlines.
    """
    text = re.sub(r'\n+', ' ', text)
    text = re.sub(r'\s+', ' ', text)
    text = re.sub(r'[^\w\s]', '', text)
    return text.strip().lower()
