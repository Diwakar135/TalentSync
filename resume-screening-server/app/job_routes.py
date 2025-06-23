from flask import Blueprint, request, jsonify
from models.job import Job
from models import db

job_routes = Blueprint('job_routes', __name__)

@job_routes.route('/jobs', methods=['POST'])
def create_job():
    data = request.get_json()

    recruiter_id = data.get("recruiter_id")
    title = data.get("title")
    description = data.get("description")
    location = data.get("location")
    skills_required = data.get("skills_required")

    if not recruiter_id or not title or not description:
        return jsonify({"success": False, "message": "Missing required fields"}), 400

    job = Job(
        recruiter_id=recruiter_id,
        title=title,
        description=description,
        location=location,
        skills_required=skills_required
    )

    db.session.add(job)
    db.session.commit()

    return jsonify({"success": True, "message": "Job created", "job_id": job.id})


@job_routes.route('/jobs', methods=['GET'])
def get_all_jobs():
    jobs = Job.query.all()
    job_list = [{
        "id": job.id,
        "title": job.title,
        "description": job.description,
        "location": job.location,
        "skills_required": job.skills_required
    } for job in jobs]

    return jsonify({"success": True, "jobs": job_list})


@job_routes.route('/jobs/<int:job_id>', methods=['GET'])
def get_job_by_id(job_id):
    job = Job.query.get(job_id)
    if not job:
        return jsonify({"success": False, "message": "Job not found"}), 404

    return jsonify({
        "success": True,
        "job": {
            "id": job.id,
            "title": job.title,
            "description": job.description,
            "location": job.location,
            "skills_required": job.skills_required
        }
    })
