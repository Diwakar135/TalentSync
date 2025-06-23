from flask import Blueprint, request, jsonify
from datetime import datetime
import app

interview_routes = Blueprint('interview_routes', __name__)
UPLOAD_FOLDER = 'static/uploads/resumes'

@interview_routes.route('/api/sync_interview', methods=['POST'])
def sync_interview():
    data = request.get_json()

    candidate_name = data.get("candidate_name")
    job_title = data.get("job_title")
    scheduled_time = data.get("scheduled_time")

    if not candidate_name or not job_title or not scheduled_time:
        return jsonify({"success": False, "message": "Missing fields"}), 400
        
    # In real project: Save to DB
    print(f"[SYNC] Interview Scheduled: {candidate_name} - {job_title} @ {scheduled_time}")

    return jsonify({"success": True, "message": "Interview synced"})
    app.register_blueprint(interview_routes)
