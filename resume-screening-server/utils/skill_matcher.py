import re

# Define a basic skill set
KNOWN_SKILLS = [
    "java", "python", "flutter", "sql", "html", "css", "javascript",
    "react", "node", "firebase", "machine learning", "android"
]

def match_skills(resume_text):
    found_skills = set()
    for skill in KNOWN_SKILLS:
        if re.search(r'\b' + re.escape(skill) + r'\b', resume_text.lower()):
            found_skills.add(skill)
    return list(found_skills)
