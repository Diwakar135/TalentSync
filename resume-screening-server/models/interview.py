from . import db

class Interview(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    candidate_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)
    job_id = db.Column(db.Integer, db.ForeignKey('job.id'), nullable=False)
    scheduled_time = db.Column(db.String(100), nullable=False)
    status = db.Column(db.String(50), default='pending')  # pending/accepted/rejected/completed
