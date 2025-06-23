from flask import Blueprint, request, jsonify
from werkzeug.utils import secure_filename
from ML.resume_parser import extract_text
import os

resume_routes = Blueprint('resume_routes', __name__)

UPLOAD_FOLDER = os.path.join(os.getcwd(), 'static', 'upload')
os.makedirs(UPLOAD_FOLDER, exist_ok=True)

@resume_routes.route('/upload_resume', methods=['POST'])
def upload_resume():
    if 'file' not in request.files:
        return jsonify({'error': 'No file provided'}), 400

    file = request.files['file']
    if file.filename == '':
        return jsonify({'error': 'Empty filename'}), 400

    filename = secure_filename(file.filename)
    filepath = os.path.join(UPLOAD_FOLDER, filename)
    file.save(filepath)

    # Use your ML parser
    text = extract_text(filepath)
    return jsonify({'text': text})
