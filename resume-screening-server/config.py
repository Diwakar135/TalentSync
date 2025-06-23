import os

class Config:
    SECRET_KEY = os.environ.get('SECRET_KEY', 'default_key_for_development')  # Use an environment variable for production
    SQLALCHEMY_DATABASE_URI = os.environ.get('DATABASE_URL', 'sqlite:///users.db')  # Use an environment variable for production
    MAX_CONTENT_LENGTH = 16 * 1024 * 1024  # 16 MB max file size

    # Upload folder paths
    UPLOAD_FOLDER = os.path.join('static', 'uploads')
    RESUME_UPLOAD_FOLDER = os.path.join(UPLOAD_FOLDER, 'resumes')
    INTERVIEW_UPLOAD_FOLDER = os.path.join(UPLOAD_FOLDER, 'interviews')
    PROFILE_UPLOAD_FOLDER = os.path.join(UPLOAD_FOLDER, 'profiles')

    # Allowable file extensions
    ALLOWED_RESUME_EXTENSIONS = {'pdf', 'docx'}
    ALLOWED_IMAGE_EXTENSIONS = {'jpg', 'jpeg', 'png'}
    ALLOWED_VIDEO_EXTENSIONS = {'mp4', 'webm', 'ogg'}
