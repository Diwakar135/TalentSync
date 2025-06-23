from flask import Flask
from flask_cors import CORS
from flask_sqlalchemy import SQLAlchemy
from .candidates_routes import candidate_routes
from config import Config 
db = SQLAlchemy()
def create_app():
    app = Flask(__name__, static_folder='static')
    app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///users.db'
    app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
    app.config.from_object(Config)
    CORS(app)
    db.init_app(app)

    with app.app_context():
        db.create_all()

    from .auth_routes import auth_routes
    from .candidates_routes import candidate_routes
    from .job_routes import job_routes
    from .interview_routes import interview_routes
    from .resume_routes import resume_routes

    app.register_blueprint(auth_routes)
    app.register_blueprint(candidate_routes)
    app.register_blueprint(job_routes)
    app.register_blueprint(interview_routes)
    app.register_blueprint(resume_routes)

    return app
