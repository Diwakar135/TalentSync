from . import db

class Resume(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    file_path = db.Column(db.String(255), nullable=False)
    parsed_text = db.Column(db.Text)
    uploaded_at = db.Column(db.DateTime, server_default=db.func.now())
