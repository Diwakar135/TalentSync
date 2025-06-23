from utils.text_processor import clean_text
from utils.pdf_extractor import extract_text_from_pdf
from utils.skill_matcher import match_skills
from sklearn.metrics.pairwise import cosine_similarity
def rank_candidates(resume_list, similarities, top_n=5):
    """
    Rank candidates based on similarity scores.

    Parameters:
    - resume_list (list): A list of dictionaries or resume data.
    - similarities (list): A list of float similarity scores.
    - top_n (int): Number of top results to return.

    Returns:
    - List of top_n resumes with their similarity scores, sorted descending.
    """

    if not resume_list or not similarities or len(resume_list) != len(similarities):
        return []

    # Combine resumes with their similarity scores
    scored_candidates = []
    for i in range(len(resume_list)):
        candidate = resume_list[i]
        score = similarities[i]
        scored_candidates.append({
            'candidate': candidate,
            'similarity_score': round(float(score), 4)
        })

    # Sort by similarity score in descending order
    ranked = sorted(scored_candidates, key=lambda x: x['similarity_score'], reverse=True)

    # Return top N ranked candidates
    return ranked[:top_n]
