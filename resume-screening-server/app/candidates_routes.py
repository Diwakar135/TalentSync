from flask import Blueprint, request, jsonify
from models.user import db, User

candidate_routes = Blueprint('candidate_routes', __name__)

@candidate_routes.route('/candidates', methods=['GET'])
def get_all_candidates():
    candidates = User.query.filter_by(role='candidate').all()
    result = [{
        'id': c.id,
        'name': c.name,
        'email': c.email
    } for c in candidates]

    return jsonify({'success': True, 'candidates': result})

@candidate_routes.route('/candidates/<int:id>', methods=['GET'])
def get_candidate_detail(id):
    candidate = User.query.get(id)
    if not candidate or candidate.role != 'candidate':
        return jsonify({'success': False, 'error': 'Candidate not found'}), 404

    result = {
        'id': candidate.id,
        'name': candidate.name,
        'email': candidate.email
        # Add other fields if needed
    }
    return jsonify({'success': True, 'candidate': result})
