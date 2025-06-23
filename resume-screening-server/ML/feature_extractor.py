import re
from utils.text_processor import clean_text
from utils.pdf_extractor import extract_text_from_pdf
from utils.skill_matcher import match_skills

def extract_features(text):
    text = text.lower()

    skills = re.findall(r'\b(java|python|flutter|sql|machine learning|android|react|node)\b', text)

    return {
        "skills": list(set(skills)),
        "word_count": len(text.split())
    }
