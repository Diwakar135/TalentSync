# ml/matcher.py
from utils.text_processor import clean_text
from utils.pdf_extractor import extract_text_from_pdf
from utils.skill_matcher import match_skills

from sklearn.metrics.pairwise import cosine_similarity
import re

def match_resumes(resume_vectors, job_vector):
    similarities = cosine_similarity(resume_vectors, job_vector)
    return similarities.flatten()
def match_jobs(resume_text):
    # Sample job roles and required keywords
    job_database = {
        "Software Engineer": ["python", "java", "data structures", "algorithms"],
        "Frontend Developer": ["html", "css", "javascript", "react"],
        "Data Scientist": ["machine learning", "statistics", "pandas", "numpy"]
    }

    resume_text = resume_text.lower()
    matched = []

    for job_title, keywords in job_database.items():
        for kw in keywords:
            if re.search(r'\b' + re.escape(kw) + r'\b', resume_text):
                matched.append(job_title)
                break  # Only match once per job

    return matched
