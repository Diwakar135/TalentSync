from flask import Blueprint, request, jsonify
import os
from werkzeug.utils import secure_filename
import sys
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))
from ML.resume_parser import extract_text_from_pdf # type: ignore
from ML.matcher import match_jobs # type: ignore

UPLOAD_FOLDER = 'static/uploads/resumes'

api = Blueprint('api', __name__)

@api.route('/upload_resume', methods=['POST'])
def upload_resume():
    if 'resume' not in request.files:
        return jsonify({'error': 'No file part'}), 400

    file = request.files['resume']
    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400

    filename = secure_filename(file.filename)
    filepath = os.path.join(UPLOAD_FOLDER, filename)
    file.save(filepath)

    # Parse resume and match jobs
    resume_text = extract_text_from_pdf(filepath)
    matches = match_jobs(resume_text)

    return jsonify({
        'message': 'Resume uploaded and parsed',
        'filename': filename,
        'matches': matches
    })
