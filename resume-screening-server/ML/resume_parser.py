# ml/resume_parser.py
from utils.text_processor import clean_text
from utils.pdf_extractor import extract_text_from_pdf
from utils.skill_matcher import match_skills

import fitz  # PyMuPDF

def extract_text_from_pdf(file_path):
    text = ""
    try:
        with fitz.open(file_path) as doc:
            for page in doc:
                text += page.get_text()
    except Exception as e:
        print("Error reading PDF:", e)
    return text
def extract_text(resume_file):
    # Example placeholder logic
    return "This is dummy resume text"
