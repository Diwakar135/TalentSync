from sklearn.feature_extraction.text import TfidfVectorizer
from utils.text_processor import clean_text
from utils.pdf_extractor import extract_text_from_pdf
from utils.skill_matcher import match_skills

vectorizer = TfidfVectorizer()

def vectorize_texts(resumes, job_description):
    texts = resumes + [job_description]
    vectors = vectorizer.fit_transform(texts)
    return vectors
